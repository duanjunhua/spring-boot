package com.boot;

import cn.hutool.core.io.IoUtil;
import com.boot.util.MinIoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

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
    MinIoUtils minIoUtils;

    /**
     * 上传
     */
    @PostMapping("/upload")
    @ResponseBody
    public Object upload(MultipartFile file) throws Exception{
        List<String> result = minIoUtils.upload(new MultipartFile[]{file});
        return result.get(0);
    }

    /**
     * 预览
     */
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable("fileName") String fileName, HttpServletResponse response) throws IOException {
        ResponseEntity<byte[]> download = minIoUtils.download(fileName);
        ServletOutputStream outputStream = response.getOutputStream();
        IoUtil.copy(new ByteArrayInputStream(download.getBody()), outputStream);
        return;
    }

    /**
     * 预览文件
     */
    @GetMapping("/preview/{fileName}")
    public void Preview(@PathVariable String fileName, HttpServletResponse response) throws Exception {
        ResponseEntity<byte[]> download = minIoUtils.download(fileName);

        ByteArrayInputStream fis = new ByteArrayInputStream(download.getBody());
        ServletOutputStream os = response.getOutputStream();

        response.setContentType(download.getHeaders().getContentType().getType());
        byte [] b = new byte[1024*8];
        while(fis.read(b)!=-1){
            os.write(b);
        }
    }
}
