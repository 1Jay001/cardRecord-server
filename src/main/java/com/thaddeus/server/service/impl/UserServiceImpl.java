package com.thaddeus.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.thaddeus.pojo.entity.User;
import com.thaddeus.pojo.entity.WeChatEntity;
import com.thaddeus.server.mapper.UserMapper;
import com.thaddeus.server.service.UserService;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public void addUser(User user) {
//        User u = new User(user.getUserId(), );
        int row = userMapper.insert(user);

    }

    public User selectByOpenId(String openId) {
        if (openId != null) {
//            User user = userMapper.selectById(openId);
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("open_id", openId);  // 假设数据库字段为 open_id
            return userMapper.selectOne(queryWrapper);
        } else {
            // openid为空，新增用户
            User user = new User();
            // TODO: 设置创建时间 使用拦截器对createTime等字段自动补充
            userMapper.insert(user);
            return user;
        }
    }
}
