package com.thaddeus.common.utils;

import com.alibaba.fastjson.JSON;
import com.thaddeus.server.ws.pojo.Message;
import com.thaddeus.server.ws.pojo.ResultMessage;
import com.thaddeus.server.ws.pojo.ScoreMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;


/**
 * @Author: copper
 * @CreateTime: 2025-03-10
 * @Description: 消息通知工具
 * @Version: 1.0
 */
@Slf4j
public class MessageUtils {

    public static String getSysMessage(boolean isSystemMessage, Object userId) {
        ResultMessage resultMessage = new ResultMessage();

        resultMessage.setSystem(isSystemMessage);
        resultMessage.setOnlineUserList(userId);

        return JSON.toJSONString(resultMessage);
    }


    // 创建分数更新消息
    public static String createScoreMessage(String fromUserId, String toUserId, Double score) {
        ScoreMessage scoreMessage = new ScoreMessage();
        scoreMessage.setFromUserId(fromUserId);
        scoreMessage.setToUserId(toUserId);
        scoreMessage.setDelta(score);
        log.info(scoreMessage.toString());
        return JSON.toJSONString(scoreMessage);
    }

    // 创建错误消息
    public static String createErrorMessage(String error) {
        return JSON.toJSONString(new HashMap<String, Object>(){{
            put("type", "error");
            put("message", error);
        }});
    }

    public static String getCurrentScoreById(String id, Double currentScore) {
        ScoreMessage scoreMessage = new ScoreMessage();
        scoreMessage.setFromUserId(id);
        scoreMessage.setToUserId(null);
        scoreMessage.setDelta(currentScore);
        return JSON.toJSONString(scoreMessage);
    }
}
