package org.yang.zhang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 08 31 14:09
 */
@Component
@Configuration
public class JavaMailSenderConfig {

    @Autowired
    private MailProperties mailProperties;

    @Bean("javaMailSender")
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSender=new JavaMailSenderImpl();
        javaMailSender.setDefaultEncoding(mailProperties.getDefaultEncoding());
        javaMailSender.setHost(mailProperties.getHost());
        javaMailSender.setPort(mailProperties.getPort());
        javaMailSender.setProtocol(mailProperties.getProtocol());
        javaMailSender.setUsername(mailProperties.getUsername());
        javaMailSender.setPassword(mailProperties.getPassword());
        return javaMailSender;
    }
}
