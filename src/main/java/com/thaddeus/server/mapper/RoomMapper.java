package com.thaddeus.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thaddeus.pojo.entity.Room;
import com.thaddeus.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: copper
 * @CreateTime: 2025-03-06
 * @Description:
 * @Version: 1.0
 */
@Mapper
public interface RoomMapper extends BaseMapper<Room> {
}
