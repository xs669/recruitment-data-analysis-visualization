package com.xs.mapper;

import com.xs.domain.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xs
* description 针对表【admin(管理员)】的数据库操作Mapper
* createDate 2023-02-06 13:30:51
* Entity com.xs.domain.Admin
*/
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    // 按用户名获取用户信息
    Admin getAdminByUserName(String username);
}




