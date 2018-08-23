package org.yang.zhang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yang.zhang.socket.NettyClient;

@Configuration
public class ClientConfig {

    @Bean
    public NettyClient nettyClient(){
        NettyClient client=new NettyClient();
        client.run();
        return client;
    }
}
