package com.thaddeus.server.controller;


import com.alibaba.fastjson2.JSONObject;
import com.thaddeus.common.constant.MessageConstant;
import com.thaddeus.common.result.Result;
import com.thaddeus.common.utils.AliOssUtil;
import com.thaddeus.common.utils.HttpClientUtil;
import com.thaddeus.pojo.dto.AccessTokenDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * @Author: copper
 * @CreateTime: 2025-01-03
 * @Description: 通用接口
 * @Version: 1.0
 */

@RestController
@RequestMapping("/admin/common")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @Value("${wechat.accessToken.url}")
    private String url;

    @Value("${wechat.miniapp.appid}")
    private String appId;

    @Value("${wechat.miniapp.secret}")
    private String secret;

    @Value("${wechat.accessToken.grant-type}")
    private String grantType;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传：{}", file);
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String objectName = UUID.randomUUID().toString() + extension;
        try {
            // TODO Cannot invoke "com.sky.utils.AliOssUtil.upload(byte[], String)" because "this.aliOssUtil" is null
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            return Result.ok(filePath);
        } catch (IOException e) {
            log.info("文件上传失败：{}", e);
        }
        return Result.fail(MessageConstant.UPLOAD_FAILED);
    }

    @GetMapping("/accessToken")
    public Result getAccessToken() {
        HashMap<String, String> map = new HashMap<>();
        map.put("appid", appId);
        map.put("secret", secret);
        map.put("grant_type", grantType);
        String result = HttpClientUtil.doGet(url, map);
        log.info(result);
        JSONObject jsonObject = JSONObject.parseObject(result);

        String accessToken = jsonObject.get("access_token").toString();
        String expiresIn = jsonObject.get("expires_in").toString();
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO(accessToken, expiresIn);
        return Result.ok(accessTokenDTO);
    }
}
