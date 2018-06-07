package socket;

import java.net.URI;
import java.net.URISyntaxException;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import module.MessageInfo;
import utils.JsonUtils;


public class WebsocketThread implements Runnable {

	private WebSocketClient wsc;

	private String url="ws://localhost:8889";


	public WebsocketThread(Integer userId) {

	}

	void send(MessageInfo message) {
		String messagesString = JsonUtils.toJson(message);
		wsc.send(messagesString);
	}


	public void run() {
		try {
			wsc = new WebSocketClient(new URI(url),new Draft_17()) {
				@Override
				public void onOpen(ServerHandshake handshake) {
					System.out.println("已连接服务器");
				}
				@Override
				public void onMessage(String revString) {
					if (revString != null) {
						MessageInfo message = JsonUtils.fromJson(revString, MessageInfo.class);
						switch (message.getType()) {
						case MSG:
							break;
						case NOTIFICATION:
							break;
						default:
							break;
						}
					}
				}

				@Override
				public void onError(Exception ex) {
					System.out.println("连接服务器失败");
				}

				@Override
				public void onClose(int arg0, String arg1, boolean arg2) {
					System.out.println("断开连接");
				}
			};
			wsc.connect();
		} catch (URISyntaxException e) {
			System.out.println("连接异常");
		}
	}

	public void destroy() {
		wsc.close();
	}

}
