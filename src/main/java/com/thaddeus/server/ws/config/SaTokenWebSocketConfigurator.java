package com.thaddeus.server.ws.config;

import cn.dev33.satoken.stp.StpUtil;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaTokenWebSocketConfigurator extends ServerEndpointConfig.Configurator {
    
    @Override
    public void modifyHandshake(ServerEndpointConfig config, 
                                HandshakeRequest request,
                                HandshakeResponse response) {
        // 修改11: 从请求参数中获取 Token（根据前端传递方式调整）
        List<String> tokenHeaders = request.getHeaders().get("satoken");
        String token = (tokenHeaders != null && !tokenHeaders.isEmpty()) ? tokenHeaders.get(0) : null;
        String loginId = StpUtil.getLoginIdByToken(token).toString();
        config.getUserProperties().put("loginId", loginId);

        // 修改12: 验证 Token 并获取用户ID
        if (token == null || !StpUtil.isLogin(loginId)) {
            throw new RuntimeException("WebSocket 连接未授权：缺少有效 Token");
        }
    }

    // 辅助方法：解析 URL 查询参数
    private Map<String, String> getQueryParams(HandshakeRequest request) {
        Map<String, String> params = new HashMap<>();
        String query = request.getRequestURI().getQuery();
        if (query != null) {
            for (String pair : query.split("&")) {
                String[] kv = pair.split("=");
                params.put(kv[0], kv.length > 1 ? kv[1] : "");
            }
        }
        return params;
    }
}