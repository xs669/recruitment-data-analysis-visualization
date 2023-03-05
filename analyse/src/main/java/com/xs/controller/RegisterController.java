package com.xs.controller;

import com.xs.common.Result;
import com.xs.domain.Admin;
import com.xs.enums.FilePathEnum;
import com.xs.service.AdminService;
import com.xs.strategy.context.UploadStrategyContext;
import com.xs.util.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
public class RegisterController {

    @Resource
    private AdminService adminService;

    @Resource
    private UploadStrategyContext uploadStrategyContext;

    // 用户注册
    @PostMapping("/register")
    public Result register(@RequestBody Admin admin) {
        return adminService.register(admin);
    }

    /**
     * 上传头像
     */
    @PostMapping("/upload")
    public R<String> upload(@RequestParam("pic") MultipartFile file) {
        return R.ok(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.AVATAR.getPath()));
    }

    // 获取登录状态
    @GetMapping("/checkLoginState/{username}")
    public Result checkLoginState(@PathVariable String username) {
        return adminService.checkLoginState(username);
    }
}
