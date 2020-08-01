package com.eks.email;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailTest {
    private static final Logger log = LoggerFactory.getLogger(EmailTest.class);

    //调用QQ邮箱服务器发送文本邮件
    @Test
    public void test1() throws MessagingException {
        //验证账号及授权码
        //TODO:填写您自己的账号
        String userNameString = "3115761431@qq.com";
        //授权码
        //QQ官方说明文档:https://service.mail.qq.com/cgi-bin/help?subtype=1&&id=28&&no=1001256
        //TODO:填写您自己的授权码
        String authorizationCodeString = "bfbhbuegdyswdcfa";

        //设置参数
        Properties properties = new Properties();
        //设置邮件发送协议
        properties.setProperty("mail.transport.protocol", "smtp");
        //设置SMTP邮件服务器
        properties.setProperty("mail.host", "smtp.qq.com");
        //设置是否要求身份认证
        properties.setProperty("mail.smtp.auth", "true");
        //设置SMTP邮件服务器端口
        //https://service.mail.qq.com/cgi-bin/help?subtype=1&id=20010&no=1000557
        //注意:如果不指定则默认端口为25,但端口25为非SSL协议端口号,出于安全考虑不建议使用,建议使用SSL协议端口号(465或587)
        properties.setProperty("mail.smtp.port", "587");
        Authenticator authenticator = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                //第一个参数为:用户账号,第二个参数为该账号的授权码
                return new PasswordAuthentication(userNameString, authorizationCodeString);
            }
        };
        //获取连接
        Session session = Session.getInstance(properties, authenticator);
        //创建MimeMessage对象
        MimeMessage mimeMessage = new MimeMessage(session);
        //发件人
        mimeMessage.setFrom(new InternetAddress(userNameString));

        //收件人
        //TODO:填写您想发送的人,这里以发给自己作为演示
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(userNameString));
        //抄送给的人
        //TODO:填写您想抄送的人,这里以发给自己作为演示
        mimeMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(userNameString));
        //密送给的人(即其他收件人或抄送给的人在邮件头中不会看到密送给的人)
        //TODO:填写您想谜送的人,这里以发给自己作为演示
        mimeMessage.addRecipient(Message.RecipientType.BCC, new InternetAddress(userNameString));

        //主题
        mimeMessage.setSubject("主题-HELLO_WORLD", "UTF-8");
        //正文
        mimeMessage.setText("正文-CONTENT", "UTF-8");
        //保存邮件
        mimeMessage.saveChanges();
        //发送
        Transport.send(mimeMessage);
        log.info("sent successfully!");
    }
}
