package com.thaddeus.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.thaddeus.common.exception.BaseException;
import com.thaddeus.common.result.Result;
import com.thaddeus.common.result.ResultCodeEnum;
import com.thaddeus.pojo.entity.User;
import com.thaddeus.pojo.entity.WeChatEntity;
import com.thaddeus.server.mapper.UserMapper;
import com.thaddeus.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Author: copper
 * @CreateTime: 2025-02-18
 * @Description: 用户接口实现类
 * @Version: 1.0
 */

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public void addUser(User user) {
//        User u = new User(user.getUserId(), );
        int row = userMapper.insert(user);
    }

    public Result<User> selectByOpenId(String openId) {
        if (openId != null) {
//            User user = userMapper.selectById(openId);
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("open_id", openId);  // 假设数据库字段为 open_id
            User user = userMapper.selectOne(queryWrapper);
            return Result.build(user, ResultCodeEnum.SUCCESS);
        } else {
            // openid为空，新增用户
            User user = new User();
            // TODO: 设置创建时间 使用拦截器对createTime等字段自动补充
            userMapper.insert(user);
            return Result.build(user, ResultCodeEnum.SUCCESS);
        }
    }

    public Result<User> selectByUserId(Long userId) {
        if (userId != null) {
            User user = userMapper.selectById(userId);
            return Result.build(user, ResultCodeEnum.SUCCESS);
        } else {
            return Result.build(null, ResultCodeEnum.FAIL);
        }
    }
}
