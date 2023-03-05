package com.xs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xs.common.Result;
import com.xs.domain.Admin;
import com.xs.vo.PasswordVo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author xs
 * description 针对表【admin(管理员)】的数据库操作Service
 * createDate 2022-10-03 16:22:14
*/
public interface AdminService extends IService<Admin>, UserDetailsService {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    // 用户注册
    Result register(Admin admin);

    // 查询用户信息
    Result getAdminDetail(Admin admin);

    // 更新用户信息
    Result updateAdminDetail(Admin admin);

    // 修改密码
    Result changePassword(PasswordVo passwordVo);

    // 获取登录状态
    Result checkLoginState(String username);
}
