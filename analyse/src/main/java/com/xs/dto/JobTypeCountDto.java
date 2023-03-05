package com.xs.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class JobTypeCountDto implements Serializable {

    private String jobName;

    private Integer number;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
