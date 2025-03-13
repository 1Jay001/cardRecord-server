package com.thaddeus.server.controller;


import com.thaddeus.common.constant.MessageConstant;
import com.thaddeus.common.result.Result;
import com.thaddeus.common.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
}
