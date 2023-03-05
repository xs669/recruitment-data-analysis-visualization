package com.xs.service.impl;

import com.xs.common.Result;
import com.xs.domain.Admin;
import com.xs.service.LogoutService;
import com.xs.util.RedisCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LogoutServiceImpl implements LogoutService {

    @Resource
    private RedisCache redisCache;

    /**
     * 退出登录
     */
    @Override
    public Result logout(Admin admin) {
        redisCache.deleteObject(admin.getUsername());
        return Result.ok("退出登录成功");
    }
}
