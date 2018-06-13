package org.yang.zhang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yang.zhang.socket.NettyServer;

@Configuration
public class NettyServerConfig {

    @Bean
    NettyServer getNettyServer(){
        NettyServer server=new NettyServer();
        new Thread(server).start();
        return server;
    }
}
