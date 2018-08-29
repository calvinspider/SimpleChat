package org.yang.zhang.config;

import java.io.File;
import java.io.FileInputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yang.zhang.constants.Constant;
import org.yang.zhang.socket.NettyClient;
import org.yang.zhang.utils.JsonUtils;

@Configuration
public class ClientConfig {

    @Bean
    public NettyClient nettyClient(){
        NettyClient client=new NettyClient();
        client.run();
        return client;
    }
}
