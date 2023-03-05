package com.xs.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class PasswordVo implements Serializable {

    private String OldPassword;

    private String newPassword;

    private String rePassword;

    private String username;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
