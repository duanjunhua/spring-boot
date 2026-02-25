package com.duanjh.mail;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-10 周二 09:38
 * @Version: v1.0
 * @Description:
 */
public interface MailService {

    /**
     * 发送普通邮件
     * @param from 谁来发送邮件
     * @param to    发送给谁
     * @param subject   邮件主题
     * @param content   邮件内容
     */
    void sendSimpleMail(String from, String to, String subject, String content);

    /**
     * 发送HTML格式邮件
     * @param from  谁来发送邮件
     * @param to    发送给谁
     * @param subject   邮件主题
     * @param content   邮件内容
     */
    void sendHtmlMail(String from, String to, String subject, String content);

    /**
     * 发送带附件的邮件
     * @param from  谁来发送邮件
     * @param to    发送给谁
     * @param subject   邮件主题
     * @param content   邮件内容
     * @param filePath  附件路径
     */
    void sendAttachmentsMail(String from, String to, String subject, String content, String filePath);

    /**
     * 发送带图片静态资源的HTML邮件
     * @param from  谁来发送邮件
     * @param to    发送给谁
     * @param subject   邮件主题
     * @param content   邮件内容
     * @param resPath   附件(图片)路径
     * @param resId     附件ID
     */
    void sendInlineImageMail(String from, String to, String subject, String content, String resPath, String resId);
}
