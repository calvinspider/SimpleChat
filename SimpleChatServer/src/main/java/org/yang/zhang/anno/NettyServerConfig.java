package org.yang.zhang.anno;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yang.zhang.socket.NettyServer;

@Configuration
public class NettyServerConfig {

    @Bean
    NettyServer getNettyServer(){
        NettyServer server=new NettyServer();
        try {
            server.start();
        }catch (Exception e){
            e.printStackTrace();
        }
        return server;
    }
}
