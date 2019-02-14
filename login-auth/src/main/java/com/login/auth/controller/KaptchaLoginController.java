package com.login.auth.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.impl.DefaultKaptcha;

@RestController
@RequestMapping("/kaptcha-login")
public class KaptchaLoginController {

	@Autowired
	@Qualifier("producer")
	private DefaultKaptcha producer;
	
	/**
	 *	 验证码获取接口
	 */
	@GetMapping(value = "/captcha.jpg", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> captcha(HttpServletResponse response) throws IOException {
		
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        // 生成文字验证码
        String text = producer.createText();
        // 生成图片验证码
        BufferedImage image = producer.createImage(text);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", out);
        // 文字验证码保存到 shiro session，登录时需要校验
        //ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        /*
	        ServletOutputStream out = response.getOutputStream();
	        IOUtils.closeQuietly(out);
        */
        
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("no-store, no-cache");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(out.toByteArray());
    }
}
