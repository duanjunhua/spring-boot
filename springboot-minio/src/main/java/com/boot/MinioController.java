package com.boot;

import cn.hutool.json.JSONObject;
import com.boot.config.MinioConfig;
import com.boot.util.FileUtils;
import com.boot.util.MinioUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.minio.ObjectWriteResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-06-14
 * @Version: V1.0
 * @Description: MINIO测试Api
 */
@Controller
@RequestMapping("/minio")
public class MinioController {

    @Autowired
    MinioConfig config;

    @GetMapping(value = {"/", "*"})
    @ResponseBody
    public String minio(){
        return MinioUtil.getBasicUrl();
    }

    /**
     * 上传
     */
    @PostMapping("/upload")
    @ResponseBody
    public void upload(MultipartFile file) throws Exception{
        ObjectWriteResponse response = MinioUtil.uploadFile(config.getBucketName(), file, file.getOriginalFilename());
        System.out.println(response);
    }

    /**
     * 下载文件
     */
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable("fileName") String fileName, HttpServletResponse response) throws Exception {
        InputStream is = MinioUtil.getObject(config.bucketName, fileName, 0, 1024);

    }

    /**
     * 预览文件
     */
    @GetMapping("/preview/{fileName}")
    public void Preview(@PathVariable String fileName, HttpServletResponse response) throws Exception {
        InputStream object = MinioUtil.getObject(config.getBucketName(), fileName);
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.copy(object, outputStream);
    }
}
