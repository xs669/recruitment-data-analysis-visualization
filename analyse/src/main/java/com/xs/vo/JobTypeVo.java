package com.xs.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class JobTypeVo implements Serializable {

    private String type;

    private String handledType;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
