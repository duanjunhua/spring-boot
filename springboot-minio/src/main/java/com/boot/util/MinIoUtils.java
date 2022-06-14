package com.boot.util;

import cn.hutool.json.JSONObject;
import com.boot.config.MinioProperties;
import com.boot.vo.ObjectItem;
import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-06-09
 * @Version: V1.0
 * @Description: MINIO工具类
 */
@Slf4j
@Data
@Component
public class MinIoUtils {

    public static MinioClient minioClient;

    @Autowired
    MinioProperties minioProperties;

    public static MinIoUtils minIoUtils;

    private static final String SEPARATOR = File.separator;

    @PostConstruct
    public void init(){
        minIoUtils = this;
        minIoUtils.minioProperties = this.minioProperties;
        try{
            minioClient = MinioClient.builder().endpoint(minioProperties.getEndpoint()).credentials(minioProperties.getAccessKey(),minioProperties.getSecretKey()).build();
            if(!bucketExists()){
                createBucket();
            }
            log.info("Minio 初始化成功！");
        }catch (Exception e){
            log.error("初始化Minio异常!", e);
        }
    }

    /**
     * 创建MinIO客户端
     */
    public void createMinIoClient(){
        try{
            if(null != minioClient) return;
            log.info("Minio Client start create");
            minioClient = MinioClient.builder().endpoint(minioProperties.getEndpoint()).credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey()).build();
            createBucket();
            log.info("Minio Client successful created");
        }catch (Exception e){
            log.error("连接MinIo服务器异常：", e);
        }
    }

    /**
     * 获取上传文件的基础路径
     */
    public String getBasicUrl(){
        return minioProperties.getEndpoint() + SEPARATOR + minioProperties.getBucketName() + SEPARATOR;
    }

    /**
     * 初始化Bucket
     */
    private void createBucket() throws Exception{
        if(bucketExists()) return;
        //不存在则创建Bucket
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
    }

    /**
     * 验证bucketName是否存在
     */
    public boolean bucketExists() throws Exception{
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
    }

    /**
     * 获取存储桶策略
     */
    private JSONObject getBucketPolicy(String bucketName) throws Exception{
        String bucketPolicy = minioClient.getBucketPolicy(GetBucketPolicyArgs.builder().bucket(bucketName).build());
        return new JSONObject(bucketPolicy);
    }

    /**
     * 删除存储Bucket
     */
    public Boolean removeBucket(String bucketName) throws Exception{
        if(bucketExists()){
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        }
        return true;
    }

    /**
     * 上传文件
     */
    public List<String> upload(MultipartFile[] files) throws Exception{
        List<String> names = new ArrayList<>(files.length);

        for (MultipartFile file : files){
            String fileName = file.getOriginalFilename();
            String[] split = fileName.split("\\.");
            if(split.length > 1){
                fileName = split[0] + "_" + System.currentTimeMillis() + "." + split[1];
            }else{
                fileName = fileName + System.currentTimeMillis();
            }

            InputStream ins = null;
            try {
                ins = file.getInputStream();
                minioClient.putObject(PutObjectArgs.builder()
                        .bucket(minioProperties.getBucketName())
                        .object(fileName)
                        .stream(ins, ins.available(), -1)
                        .contentType(file.getContentType())
                        .build());
            }catch (Exception e){
                log.error("上传失败！", e);
            }finally {
                if(null != ins){
                    try {
                        ins.close();
                    }catch (Exception e2){
                        log.error("关闭上传流失败！", e2);
                    }
                }
            }
            names.add(fileName);
        }
        return names;
    }

    /**
     * 下载文件
     */
    public ResponseEntity<byte[]> download(String fileName){
        ResponseEntity<byte[]> responseEntity = null;
        InputStream ins = null;
        ByteArrayOutputStream baos = null;
        try{
            ins = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .object(fileName)
                    .build());
            baos = new ByteArrayOutputStream();
            IOUtils.copy(ins, baos);
            //封装返回值
            byte[] bytes = baos.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            headers.setContentLength(bytes.length);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setAccessControlExposeHeaders(Arrays.asList("*"));
            responseEntity = new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
        }catch (Exception e){
            log.error("下载文件失败！", e);
        }finally {
            if(null != ins){
                try {
                    ins.close();
                }catch (Exception e2){
                    log.error("关闭下载入流失败！", e2);
                }
            }
            if(null != baos){
                try {
                    baos.close();
                } catch (IOException e) {
                    log.error("关闭下载出流失败！", e);
                }
            }
        }
        return responseEntity;
    }

    /**
     * 查看文件
     */
    /**
     * 查看文件对象
     * @param bucketName 存储bucket名称
     * @return 存储bucket内文件对象信息
     */
    public List<ObjectItem> listObjects(String bucketName) {
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
        List<ObjectItem> objectItems = new ArrayList<>();
        try {
            for (Result<Item> result : results) {
                Item item = result.get();
                ObjectItem objectItem = new ObjectItem();
                objectItem.setObjectName(item.objectName());
                objectItem.setSize(item.size());
                objectItems.add(objectItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return objectItems;
    }

    /**
     * 批量删除文件对象
     * @param bucketName 存储bucket名称
     * @param objects 对象名称集合
     */
    public Iterable<Result<DeleteError>> removeObjects(String bucketName, List<String> objects) {
        List<DeleteObject> dos = objects.stream().map(e -> new DeleteObject(e)).collect(Collectors.toList());
        Iterable<Result<DeleteError>> results = minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucketName).objects(dos).build());
        return results;
    }

}
