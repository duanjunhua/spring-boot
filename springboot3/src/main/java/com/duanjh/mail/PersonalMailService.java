package com.duanjh.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-10 周二 09:38
 * @Version: v1.0
 * @Description:
 */
@Slf4j
@Component
public class PersonalMailService implements MailService{

    @Autowired
    JavaMailSender mailSender;

    @Override
    public void sendSimpleMail(String from, String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
        log.info("邮件已由{}发送至{}", from, to);
    }

    /**
     * content格式为html格式，如：<html><body><h1>这是一封HTML格式的邮件</h1></body></html>
     */
    @Override
    public void sendHtmlMail(String from, String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            // true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
            log.info("Html格式邮件已由{}发送至{}", from, to);
        }catch (MessagingException e){
            log.error("邮件发送失败");
        }

    }

    @Override
    public void sendAttachmentsMail(String from, String to, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            // true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            // 添加多个附件可以使用多条 helper.addAttachment(fileName, file)
            FileSystemResource file = new FileSystemResource(new File(filePath));
            helper.addAttachment(file.getFilename(), file);
            mailSender.send(message);
            log.info("带附件的邮件已由{}发送至{}", from, to);
        }catch (MessagingException e){
            log.error("带附件的邮件发送失败");
        }
    }

    /**
     * 发送带图片的HTML邮件，content格式为
     *      如：<html><body><img src="id=' + resId + '"></></body></html>
     */
    @Override
    public void sendInlineImageMail(String from, String to, String subject, String content, String resPath, String resId) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            // true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            // 嵌入静态资源
            FileSystemResource file = new FileSystemResource(new File(resPath));
            helper.addInline(resId, file);

            mailSender.send(message);
            log.info("带图片的Html格式邮件已由{}发送至{}", from, to);
        }catch (MessagingException e){
            log.error("带图片的Html格式邮件发送失败");
        }
    }
}
