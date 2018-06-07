package org.yang.zhang.socket2;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 07 18:16
 */

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
public class Global {
    public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

}
