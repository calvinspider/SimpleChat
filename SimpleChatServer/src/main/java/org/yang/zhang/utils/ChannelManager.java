package org.yang.zhang.utils;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelHandlerContext;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 20 18:42
 */

public class ChannelManager {

    private static Map<String,ChannelHandlerContext> map=new HashMap<>();

    public static void registerChannel(String id,ChannelHandlerContext ctx){
        map.put(id,ctx);
    }

    public static ChannelHandlerContext getChannel(String id){
        return map.get(id);
    }
}
