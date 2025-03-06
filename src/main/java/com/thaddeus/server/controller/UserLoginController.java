package com.thaddeus.server.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.alibaba.fastjson2.JSONObject;
import com.thaddeus.common.constant.JwtClaimsConstant;
import com.thaddeus.common.constant.UserInfoConstant;
import com.thaddeus.common.properties.JwtProperties;
import com.thaddeus.common.result.Result;
import com.thaddeus.common.result.ResultCodeEnum;
import com.thaddeus.common.utils.HttpClientUtil;
import com.thaddeus.common.utils.JwtUtil;
import com.thaddeus.pojo.dto.UserLoginDTO;
import com.thaddeus.pojo.dto.UserResponseDTO;
import com.thaddeus.pojo.entity.User;
import com.thaddeus.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @Author: copper
 * @CreateTime: 2025-02-22
 * @Description: 用户登录控制层
 * @Version: 1.0
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserLoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    @Value("${wechat.miniapp.jscode2session-url}")
    private String url;

    @Value("${wechat.miniapp.appid}")
    private String appId;

    @Value("${wechat.miniapp.secret}")
    private String secretKey;

    @Value("${wechat.miniapp.grant-type}")
    private String grantType;


    @PostMapping("/login")
    public Result userLogin(@RequestBody UserLoginDTO userLoginDTO) {
        log.info(userLoginDTO.getCode());
        // 1. 获取openid和session_key
        HashMap<String, String> map = new HashMap<>();
        map.put("appid", appId);
        map.put("secret", secretKey);
        map.put("js_code", userLoginDTO.getCode());
        map.put("grant_type", grantType);
        String result = HttpClientUtil.doGet(url, map); // TODO 前端传入js_code不正确导致空指针异常，需要抛出异常
        log.info("result: {}", result);
        JSONObject jsonObject = JSONObject.parseObject(result);

        String sessionKey = jsonObject.get("session_key").toString();
        String openid = jsonObject.get("openid").toString();

        // 根据openid查询用户是否存在
        Result<User> userResult = userService.selectByOpenId(openid);
        User user = userResult.getData();

        StpUtil.login(user.getUserId());
        String saToken = StpUtil.getTokenValue();
        log.info("saTokenInfo: {}", StpUtil.getTokenInfo());

        HashMap<String, String> userInfo = getUserInfo(user);
        // 3. 封装token和用户信息返回给前端
        UserResponseDTO userResponseDTO = UserResponseDTO.builder()
                .token(saToken)
                .userInfo(userInfo)
                .build();
        return Result.build(userResponseDTO, ResultCodeEnum.SUCCESS);
    }

    private static HashMap<String, String> getUserInfo(User user) {
        HashMap<String, String> userInfo = new HashMap<>();
        userInfo.put(UserInfoConstant.USER_ID, String.valueOf(user.getUserId()));
        userInfo.put(UserInfoConstant.OPEN_ID, user.getOpenId());
        userInfo.put(UserInfoConstant.NICK_NAME, user.getNickName());
        userInfo.put(UserInfoConstant.AVATAR_URL, user.getAvatarUrl());
        userInfo.put(UserInfoConstant.CREATE_TIME, String.valueOf(user.getCreateTime()));
        userInfo.put(UserInfoConstant.LAST_LOGIN_TIME, String.valueOf(user.getLastLoginTime()));
        return userInfo;
    }
}
