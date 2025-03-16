package com.thaddeus.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: copper
 * @CreateTime: 2025-03-14
 * @Description: 用户加入房间传入的数据
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinRoomDTO {

    private Long roomId;

    private Long userId;

}
