package com.thaddeus.server.ws.config;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSON;
import com.thaddeus.common.utils.MessageUtils;
import com.thaddeus.server.ws.pojo.ScoreMessage;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: copper
 * @CreateTime: 2025-03-10
 * @Description:
 * @Version: 1.0
 */
@ServerEndpoint(value = "/sc", configurator = SaTokenWebSocketConfigurator.class) // 修改1: 使用自定义的 SaToken WebSocket 配置类
@Component
public class ScoreCountEndpoint {

    // 修改2: 使用 SaToken 用户ID（loginId）作为键，替代原 HttpSession 中的 "user" 字符串
    private static final ConcurrentHashMap<String, Session> onlineUsers = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<String, Double> userScores = new ConcurrentHashMap<>();

    private String loginId; // 修改3: 直接存储 SaToken 的用户标识，替代 SaSession 对象

    /**
     * 建立 WebSocket 连接后调用
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        // 1. 获取并验证用户身份
        this.loginId = (String) config.getUserProperties().get("loginId");
        if (!StpUtil.isLogin(this.loginId)) {
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "未授权连接"));
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 2. 处理重复连接（关闭旧会话）
        Session existingSession = onlineUsers.get(this.loginId);
        if (existingSession != null && existingSession.isOpen()) {
            try {
                existingSession.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 3. 存储新会话
        onlineUsers.put(this.loginId, session);

        // 4. 向新用户发送初始化数据（init）
        sendInitMessage(session);

        // 5. 向其他用户广播新增用户（add）
        broadcastAddMessage(this.loginId);
    }

    /**
     * 向新用户发送初始化消息（全量数据）
     */
    private void sendInitMessage(Session session) {
        try {
            String initMsg = MessageUtils.getSysMessage("init", getOnlineUsers());
            session.getBasicRemote().sendText(initMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Set<String> getOnlineUsers() {
        return onlineUsers.keySet();
    }

    /**
     * 向其他用户广播新增用户（增量数据）
     */
    private void broadcastAddMessage(String newUserId) {
        String addMsg = MessageUtils.getSysMessage("add", Collections.singleton(newUserId));
        onlineUsers.forEach((userId, session) -> {
            if (!userId.equals(newUserId) && session.isOpen()) { // 排除自己
                try {
                    session.getBasicRemote().sendText(addMsg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 处理客户端消息（私聊）
     */
    @OnMessage
    public void onMessage(String message) {
        try {
            ScoreMessage msg = JSON.parseObject(message, ScoreMessage.class);
            String toUserId = msg.getToUserId(); // 接收方用户ID

            // 修改8: 直接从在线用户 Map 获取目标 Session
            Session targetSession = onlineUsers.get(toUserId);
            if (targetSession != null && targetSession.isOpen()) {
                // 修改9: 发送方用户ID使用当前 loginId（无需从 HttpSession 获取）
//                String formattedMsg = MessageUtils.getMessage(false, this.loginId, msg.getMessage());
                Double score = userScores.get(loginId);
//                String formattedMsg = MessageUtils.createScoreMessage(this.loginId, toUserId, score);
//                targetSession.getBasicRemote().sendText(formattedMsg);

                Double delta = msg.getDelta();

                if (!onlineUsers.containsKey(toUserId)) {
                    sendError(toUserId, "目标用户不在线");
                    return;
                }

                // 3. 原子性更新分数（同步代码块）
                // 扣除发送方分数
                userScores.compute(loginId, (key, oldValue) -> (oldValue == null ? 0.0 : oldValue) - delta);

                // 增加接收方分数
                userScores.compute(toUserId, (key, oldValue) -> (oldValue == null ? 0.0 : oldValue) + delta);

                // 4. 推送分数更新给双方
//                sendScoreUpdate(loginId);
                sendScoreUpdate(toUserId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 断开连接处理
     */
    @OnClose
    public void onClose(Session session) {
        onlineUsers.remove(this.loginId);
        broadcastRemoveMessage(this.loginId);
    }

    /**
     * 广播用户离开
     */
    private void broadcastRemoveMessage(String removedUserId) {
        String removeMsg = MessageUtils.getSysMessage("remove", Collections.singleton(removedUserId));
        onlineUsers.forEach((userId, session) -> {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(removeMsg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 发送分数更新给指定用户
     */
    private void sendScoreUpdate(String toUserId) {
        Session session = onlineUsers.get(toUserId);
        if (session != null && session.isOpen()) {
            try {
                Double score = userScores.get(toUserId);
                String message = MessageUtils.createScoreMessage(loginId, toUserId, score);
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送错误信息
     */
    private void sendError(String userId, String errorMsg) {
        Session session = onlineUsers.get(userId);
        if (session != null && session.isOpen()) {
            try {
                String message = MessageUtils.createErrorMessage(errorMsg);
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
