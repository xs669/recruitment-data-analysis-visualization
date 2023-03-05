package com.xs.service;

import com.xs.common.Result;
import com.xs.domain.Admin;

public interface LogoutService {

    /**
     * 退出登录
     */
    Result logout(Admin admin);
}
