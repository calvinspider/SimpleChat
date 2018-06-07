package org.yang.zhang.socket2;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 07 18:16
 */

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
public class ChildChannelHandler extends ChannelInitializer<SocketChannel>{
    @Override
    protected void initChannel(SocketChannel e) throws Exception {

        e.pipeline().addLast("http-codec",new HttpServerCodec());
        e.pipeline().addLast("aggregator",new HttpObjectAggregator(65536));
        e.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
        e.pipeline().addLast("handler",new MyWebSocketServerHandler());
    }
}
