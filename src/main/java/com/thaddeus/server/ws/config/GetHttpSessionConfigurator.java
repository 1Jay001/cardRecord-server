package com.thaddeus.server.ws.config;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;

import java.util.List;

/**
 * @Author: copper
 * @CreateTime: 2025-03-10
 * @Description: 用户获取HttpSession对象
 * @Version: 1.0
 */
public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
//        HttpSession httpSession = (HttpSession) request.getHttpSession();
//        sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
        SaSession session = StpUtil.getSession();
        sec.getUserProperties().put("saSession", session);

    }
}
