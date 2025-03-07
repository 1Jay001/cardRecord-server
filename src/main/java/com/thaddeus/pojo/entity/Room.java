package com.thaddeus.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author: copper
 * @CreateTime: 2025-03-06
 * @Description: 房间实体类
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @TableId(type = IdType.AUTO)
    private Long roomId;

    private String roomName;

    private Integer roomSize;

    private Integer roomStatus;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;

}
