package org.yang.zhang.netty;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 05 18 16:11
 */

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.yang.zhang.mapper.ChatMessageCustRepository;
import org.yang.zhang.mapper.ChatMessageRepository;
import org.yang.zhang.mapper.ClientInfoRepository;
import org.yang.zhang.module.ClientInfo;
import org.yang.zhang.module.MessageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;

@Component
public class MessageEventHandler
{
    private final SocketIOServer server;

    @Autowired
    private ClientInfoRepository clientInfoRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatMessageCustRepository chatMessageCustRepository;
    @Autowired
    public MessageEventHandler(SocketIOServer server)
    {
        this.server = server;
    }
    //添加connect事件，当客户端发起连接时调用，本文中将clientid与sessionid存入数据库
    //方便后面发送消息时查找到对应的目标client,
    @OnConnect
    public void onConnect(SocketIOClient client)
    {
        String userid = client.getHandshakeData().getSingleUrlParam("userid");
        ClientInfo clientInfo = clientInfoRepository.findClientByclientid(Integer.valueOf(userid));
        Date nowTime = new Date(System.currentTimeMillis());
        clientInfo.setConnected((short)1);
        clientInfo.setClientid(Integer.valueOf(userid));
        clientInfo.setMostsignbits(client.getSessionId().getMostSignificantBits());
        clientInfo.setLeastsignbits(client.getSessionId().getLeastSignificantBits());
        clientInfo.setLastconnecteddate(nowTime);
        clientInfoRepository.save(clientInfo);

        List<MessageInfo> tosendmsg=chatMessageCustRepository.findUnsendChatMessage(Integer.valueOf(userid));
        for (MessageInfo sendData:tosendmsg)
        {
            client.sendEvent("messageevent", sendData);
        }
    }

    //添加@OnDisconnect事件，客户端断开连接时调用，刷新客户端信息
    @OnDisconnect
    public void onDisconnect(SocketIOClient client)
    {
        String userid = client.getHandshakeData().getSingleUrlParam("userid");
        ClientInfo clientInfo = clientInfoRepository.findClientByclientid(Integer.valueOf(userid));
        if (clientInfo != null)
        {
            clientInfo.setConnected((short)0);
            clientInfo.setMostsignbits(null);
            clientInfo.setLeastsignbits(null);
            clientInfoRepository.save(clientInfo);
        }
    }

    //消息接收入口，当接收到消息后，查找发送目标客户端，并且向该客户端发送消息，且给自己发送消息
    @OnEvent(value = "messageevent")
    public void onEvent(SocketIOClient client, AckRequest request, MessageInfo data)
    {
        Integer targetClientId = data.getTargetclientid();
        ClientInfo clientInfo = clientInfoRepository.findClientByclientid(targetClientId);
        if (clientInfo != null)
        {
            MessageInfo sendData = new MessageInfo();
            sendData.setSourceclientid(data.getSourceclientid());
            sendData.setTargetclientid(data.getTargetclientid());
            sendData.setMsgtype("chat");
            sendData.setMsgcontent(data.getMsgcontent());
            sendData.setTime(new Date());
            if(clientInfo.getConnected()!=null&&clientInfo.getConnected() == 1)
            {
                UUID uuid = new UUID(clientInfo.getMostsignbits(), clientInfo.getLeastsignbits());
                sendData.setSendflag(1);
                chatMessageRepository.save(sendData);
                server.getClient(uuid).sendEvent("messageevent", sendData);
            }else{
                sendData.setSendflag(0);
                chatMessageRepository.save(sendData);
                client.sendEvent("messageevent", sendData);
            }
        }

    }
}
