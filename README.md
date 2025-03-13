记牌器后端
用户->>小程序: 选择头像 + 填写昵称
小程序->>后端: 上传头像到 /uploadAvatar
后端->>阿里云OSS: 上传文件
阿里云OSS-->>后端: 返回URL
后端-->>小程序: 返回头像URL
小程序->>用户: 预览头像
用户->>小程序: 点击提交
小程序->>后端: 调用 /updateUserInfo
后端->>数据库: 更新 avatar 和 nickname
数据库-->>后端: 操作结果
后端-->>小程序: 返回成功/失败
小程序->>用户: 显示结果