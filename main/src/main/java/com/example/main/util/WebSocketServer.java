package com.example.main.util;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/v1/order-endpoint/{token}")
@Component
public class WebSocketServer {
    private static final ConcurrentHashMap<String, Session> SESSIONS = new ConcurrentHashMap<String, Session>();

    public void sendResult(Session toSession, String message){
        if(toSession != null){
            try{
                toSession.getBasicRemote().sendText(message);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void sendResultToUser(String token ,String message){
        System.out.println(message);
        Session toSession = SESSIONS.get(token);
        sendResult(toSession, message);
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token){
        System.out.println("connect - " + token);
        // SecurityContextHolder.getContext().getAuthentication();
        if(SESSIONS.get(token) != null) return;
        SESSIONS.put(token, session);
    }
    @OnClose
    public void onClose(@PathParam("token") String token){
        SESSIONS.remove(token);
    }

    @OnError
    public void onError(Session session, Throwable throwable){
        throwable.printStackTrace();
    }

    public void closeSession(String token) {
        Session session = SESSIONS.get(token);
        if (session != null && session.isOpen()) {
            try {
                session.close();  // 显式关闭 WebSocket 连接
                SESSIONS.remove(token);  // 从 SESSIONS 中移除
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
