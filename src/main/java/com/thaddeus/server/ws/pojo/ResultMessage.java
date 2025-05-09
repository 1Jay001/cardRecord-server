package com.thaddeus.server.ws.pojo;

import lombok.Data;


/**
 * 用来封装服务端给浏览器发送的消息数据
 */
@Data
public class ResultMessage {

    private boolean isSystem;

    private Object onlineUserList;

}
