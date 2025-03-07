package com.thaddeus.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thaddeus.common.enumeration.OperationType;
import com.thaddeus.pojo.entity.User;
import com.thaddeus.server.annotation.AutoFill;
import org.apache.ibatis.annotations.Insert;
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
