package com.xs.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class TagsVo implements Serializable {

    private String name;

    private Integer value;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
