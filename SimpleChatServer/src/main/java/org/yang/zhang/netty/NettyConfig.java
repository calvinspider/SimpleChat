package org.yang.zhang.netty;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 05 18 16:04
 */
@Configuration
public class NettyConfig {

    @Bean
    public SocketIOServer socketIOServer()
    {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname("localhost");
        config.setPort(8889);

        //该处可以用来进行身份验证
        config.setAuthorizationListener(new AuthorizationListener() {
            @Override
            public boolean isAuthorized(HandshakeData data) {
                //http://localhost:8081?username=test&password=test
                //例如果使用上面的链接进行connect，可以使用如下代码获取用户密码信息，本文不做身份验证
//              String username = data.getSingleUrlParam("username");
//              String password = data.getSingleUrlParam("password");
                return true;
            }
        });

        final SocketIOServer server = new SocketIOServer(config);
        return server;
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }
}
