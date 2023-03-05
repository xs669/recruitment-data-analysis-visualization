package com.xs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.common.Result;
import com.xs.domain.Admin;
import com.xs.mapper.AdminMapper;
import com.xs.service.AdminService;
import com.xs.util.RedisCache;
import com.xs.vo.PasswordVo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author xs
 * description 针对表【admin(管理员)】的数据库操作Service实现
 * createDate 2022-10-03 16:22:14
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private RedisCache redisCache;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<Admin> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Admin::getUsername, username);
        Admin admin = adminMapper.selectOne(lqw);
        if (Objects.isNull(admin)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return admin;
    }

    // 用户注册
    @Override
    public Result register(Admin admin) {
        LambdaQueryWrapper<Admin> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Objects.nonNull(admin.getUsername()), Admin::getUsername, admin.getUsername());
        if (adminMapper.selectList(lqw).isEmpty()) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            admin.setRole("admin");
            admin.setCreateTime(new Date());
            adminMapper.insert(admin);
            return Result.ok("注册成功！");
        } else {
            return Result.error("用户名重复");
        }
    }

    // 查询用户信息
    @Override
    public Result getAdminDetail(Admin admin) {
        if (Objects.isNull(redisCache.getCacheObject(admin.getUsername()))) {
            LambdaQueryWrapper<Admin> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Objects.nonNull(admin.getUsername()), Admin::getUsername, admin.getUsername());
            Admin a = adminMapper.selectOne(lqw);
            if (Objects.isNull(a)) {
                return Result.error("查询失败");
            } else {
                redisCache.setCacheObject(admin.getUsername(), a);
                return Result.ok("查询成功", a);
            }
        } else {
            return Result.ok("查询成功", redisCache.getCacheObject(admin.getUsername()));
        }
    }

    // 更新用户信息
    @Override
    public Result updateAdminDetail(Admin admin) {
        Admin adminByUserName = adminMapper.getAdminByUserName(admin.getUsername());
        admin.setId(adminByUserName.getId());
        if (Objects.equals(admin, adminByUserName)) {
            return Result.error("用户信息没有发生变化");
        } else {
            adminMapper.updateById(admin);
            redisCache.deleteObject(admin.getUsername());
            redisCache.setCacheObject(admin.getUsername(), admin);
            return Result.ok("修改成功");
        }
    }

    // 修改密码
    @Override
    public Result changePassword(PasswordVo passwordVo) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Admin adminByUserName = adminMapper.getAdminByUserName(passwordVo.getUsername());
        boolean matches = passwordEncoder.matches(passwordVo.getOldPassword(), adminByUserName.getPassword());
        if (matches) {
            if (Objects.equals(passwordVo.getNewPassword(), passwordVo.getRePassword())) {
                adminByUserName.setPassword(passwordEncoder.encode(passwordVo.getRePassword()));
                adminMapper.updateById(adminByUserName);
                redisCache.deleteObject(passwordVo.getUsername());
                redisCache.setCacheObject(passwordVo.getUsername(), adminByUserName);
                return Result.ok("修改成功");
            } else {
                return Result.error("新密码与确认密码输入不一致");
            }
        } else {
            return Result.error("旧密码错误");
        }
    }

    // 获取登录状态
    @Override
    public Result checkLoginState(String username) {
        if (Objects.isNull(redisCache.getCacheObject(username))) {
            return Result.ok("token已删除", false);
        } else {
            return Result.ok("token生效中", true);
        }
    }

}




