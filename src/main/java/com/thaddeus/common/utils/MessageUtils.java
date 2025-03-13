package com.thaddeus.common.utils;

import com.alibaba.fastjson.JSON;
import com.thaddeus.server.ws.pojo.ResultMessage;

/**
 * @Author: copper
 * @CreateTime: 2025-03-10
 * @Description: 消息通知工具
 * @Version: 1.0
 */
public class MessageUtils {

    public static String getMessage(boolean isSystemMessage, String fromName, Object message) {
        ResultMessage resultMessage = new ResultMessage();

        resultMessage.setSystem(isSystemMessage);
        resultMessage.setMessage(message);
        if (fromName != null) {
            resultMessage.setFromName(fromName);
        }

        return JSON.toJSONString(resultMessage);
    }
}
