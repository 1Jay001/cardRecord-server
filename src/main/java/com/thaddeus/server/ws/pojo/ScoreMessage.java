package com.thaddeus.server.ws.pojo;

import lombok.Data;

/**
 * @Author: copper
 * @CreateTime: 2025-04-01
 * @Description:
 * @Version: 1.0
 */
@Data
public class ScoreMessage {

    private String fromUserId; // 发起用户ID

    private String toUserId;  // 目标用户ID

    private Double delta;    // 分数变化值（正数: 加分，负数: 扣分）

}
