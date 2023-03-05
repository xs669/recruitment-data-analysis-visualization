package com.xs.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class EducationalByGroupDto implements Serializable {

    private String educational;

    private Integer count;

    private Float percent;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
