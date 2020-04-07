package vt.vmt.status.ws;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static vt.vmt.VmtConstant.URI_PREFIX_VMT;

@ServerEndpoint("/" + URI_PREFIX_VMT + "/status/ws/{requestId}")
@Component
@Slf4j
public class VmtWebSocketServer {

    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static AtomicInteger onlineCount = new AtomicInteger();
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static ConcurrentHashMap<String, VmtWebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**请求Id,客户端生成*/
    private String requestId="";

    /**
     * period of send message
     */
    private long preSendTimestamp = -1;

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("requestId") String requestId) {
        this.session = session;
        this.requestId=requestId;
        if(webSocketMap.containsKey(requestId)){
            webSocketMap.remove(requestId);
            webSocketMap.put(requestId,this);
        }else{
            webSocketMap.put(requestId,this);
            //连接数加1
            addOnlineCount();
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(requestId)){
            webSocketMap.remove(requestId);
            //从set中删除
            subOnlineCount();
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("vmt-status-ws: " + error.getMessage(), error);
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 发送自定义消息
     * */
    public static void sendInfo(String message,@PathParam("userId") String userId) throws IOException {
    }

    public static int getOnlineCount() {
        return onlineCount.get();
    }

    public static void addOnlineCount() {
        onlineCount.getAndIncrement();
    }

    public static void subOnlineCount() {
        onlineCount.getAndDecrement();
    }

    public long getPreSendTimestamp() {
        return preSendTimestamp;
    }

    public void setPreSendTimestamp(long preSendTimestamp) {
        this.preSendTimestamp = preSendTimestamp;
    }

    public static ConcurrentHashMap<String, VmtWebSocketServer> getWebSocketMap() {
        return webSocketMap;
    }
}