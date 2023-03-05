package com.xs.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class JobTypeAndAddressVo implements Serializable {

    private String handledType;

    private String transformAddress;

    private String handledAddress;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
