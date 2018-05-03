package com.blog.mail.service.impl;

import com.blog.mail.service.MailSender;
import com.blog.mail.util.PropertiesUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class SpringMail implements MailSender {

    //注入一个mailSender
    private final JavaMailSenderImpl mailSender;
    private Log log = LogFactory.getLog(PropertiesUtil.class);
    @Autowired
    public SpringMail(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail() {
        //读取配置文件中的收件人
        PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("mail.properties");
        String receiver = propertiesUtil.getPropertyAsString("mail.smtp.to");// 收件人
        System.out.print(1);
        if(mailSender==null)
            System.out.print(1);
        MimeMessage mailMessage = mailSender.createMimeMessage();
        log.info("发送邮件给" + propertiesUtil.getPropertyAsString("mail.smtp.to"));
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "utf-8");
            helper.setFrom(propertiesUtil.getPropertyAsString("mail.smtp.from"));// 设置发件人
            helper.setTo(receiver);// 设置收件人

            helper.setSubject("SpringMail测试");// 设置主题
            helper.setText("这是一封来自SpringMail的测试邮件");// 邮件体
            mailSender.send(mailMessage);// 发送邮件
            log.info("邮件发送成功...");
        } catch (Exception e) {
            log.error("邮件发送发生异常");
            log.error(e.getMessage());
            log.info("进行重发");
            try {
                Thread.sleep(1000 * 1000);
                this.sendMail();
            } catch (InterruptedException e1) {
                log.info("重发邮件发生异常");
                log.error(e.getMessage());
                e1.printStackTrace();
            }
        }
    }


}
