package com.thaddeus.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thaddeus.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: copper
 * @CreateTime: 2025-02-18
 * @Description:
 * @Version: 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
