package com.thaddeus.server.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.thaddeus.common.constant.MessageConstant;
import com.thaddeus.common.exception.BaseException;
import com.thaddeus.common.result.Result;
import com.thaddeus.common.result.ResultCodeEnum;
import com.thaddeus.pojo.dto.UserDTO;
import com.thaddeus.pojo.entity.User;
import com.thaddeus.server.mapper.UserMapper;
import com.thaddeus.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        String avatarUrl = user.getAvatarUrl();
        int row = userMapper.insert(user);
        log.info(String.valueOf(row));
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
            user.setCreateTime(LocalDateTime.now());
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

    public Result updateUser(UserDTO userDTO) {

        Long currentUserId = StpUtil.getLoginIdAsLong();
        User existingUser = userMapper.selectById(currentUserId);
        if (existingUser == null) {
            return Result.fail(ResultCodeEnum.NOEXIST_USER);
        }
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::getUserId, currentUserId)
                .set(User::getNickName, userDTO.getNickName())
                .set(User::getAvatarUrl, userDTO.getAvatarUrl());
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        int rows = userMapper.update(user, wrapper);
        log.info(String.valueOf(rows));
        return rows > 0 ? Result.ok() : Result.fail();
    }
}
