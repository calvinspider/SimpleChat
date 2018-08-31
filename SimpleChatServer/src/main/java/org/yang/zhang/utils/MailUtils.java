package org.yang.zhang.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 08 31 13:34
 */
public class MailUtils {

    public static void send(String to, String subject,String content){
        SimpleMailMessage mainMessage = new SimpleMailMessage();
        mainMessage.setFrom("yang_zhang987@163.com");
        mainMessage.setTo(to);
        mainMessage.setSubject(subject);
        mainMessage.setText(content);
        JavaMailSender jms=SpringContextUtils.getBean("javaMailSender");
        jms.send(mainMessage);
    }


}
