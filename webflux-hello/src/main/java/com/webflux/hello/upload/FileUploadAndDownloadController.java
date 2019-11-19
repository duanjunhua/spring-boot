package com.webflux.hello.upload;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@RestController
@RequestMapping(value = "/file/upAndDown")
public class FileUploadAndDownloadController {     //也可以定义FileUploadRouter

    /**
     * 上传文件
     * @param filePart
     * @return
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<String> upload(@RequestPart("file")FilePart filePart) throws IOException {
        System.out.println(filePart.filename());
        Path path = Files.createTempFile("text", filePart.filename());

        //方法一：
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        DataBufferUtils.write(filePart.content(), channel, 0).doOnComplete(() -> {
                System.out.println("Finish Upload File.");
        }).subscribe();

        //方法二：
        //filePart.transferTo(path.toFile());

        return Mono.just(filePart.filename());
    }

    @GetMapping("/download")
    public Mono<Void> downloadFile(ServerHttpResponse response) throws IOException{
        ZeroCopyHttpOutputMessage outputMessage = (ZeroCopyHttpOutputMessage) response;
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=text.png");
        response.getHeaders().setContentType(MediaType.IMAGE_PNG);
        Resource resource = new ClassPathResource("text.png");
        File file = resource.getFile();
        return outputMessage.writeWith(file, 0, file.length());
    }
}
