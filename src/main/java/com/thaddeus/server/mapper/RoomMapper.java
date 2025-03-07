package com.thaddeus.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thaddeus.common.enumeration.OperationType;
import com.thaddeus.pojo.entity.Room;
import com.thaddeus.pojo.entity.User;
import com.thaddeus.server.annotation.AutoFill;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: copper
 * @CreateTime: 2025-03-06
 * @Description:
 * @Version: 1.0
 */
@Mapper
public interface RoomMapper extends BaseMapper<Room> {

    @AutoFill(OperationType.INSERT)
    @Insert("insert into room(room_id, room_name, room_size, room_status, create_time, create_user, update_time, update_user)" +
            "values (#{roomId}, #{roomName}, #{roomSize}, #{roomStatus}, #{createTime}, #{createUser}, #{updateTime}, #{updateUser})")
    void createRoom(Room room);
}
