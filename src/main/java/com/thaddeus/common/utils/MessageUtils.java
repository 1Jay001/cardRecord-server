package com.thaddeus.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.thaddeus.server.ws.pojo.ResultMessage;
import com.thaddeus.server.ws.pojo.ScoreMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


/**
 * @Author: copper
 * @CreateTime: 2025-03-10
 * @Description: 消息通知工具
 * @Version: 1.0
 */
@Slf4j
public class MessageUtils {

    public static String getSysMessage(String type, Set<String> userIds) {
        JSONObject msg = new JSONObject();
        msg.put("type", type);
        // init 返回全量列表，add/remove 返回增量列表
        if ("init".equals(type)) {
            msg.put("onlineUserList", new ArrayList<>(userIds));
        } else {
            msg.put("userIds", new ArrayList<>(userIds));
        }
        return msg.toString();
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
