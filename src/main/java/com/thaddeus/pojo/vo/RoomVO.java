package com.thaddeus.pojo.vo;

import com.thaddeus.pojo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: copper
 * @CreateTime: 2025-03-08
 * @Description: 展示给前端的房间信息，包括房间id、房主、状态、成员
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomVO {

    private Long roomId;

    private String roomName;

    private Integer status;

    private List<User> members;
}
