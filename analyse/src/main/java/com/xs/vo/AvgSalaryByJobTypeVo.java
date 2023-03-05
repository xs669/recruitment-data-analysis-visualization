package com.xs.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class AvgSalaryByJobTypeVo implements Serializable {

    private String type;

    private Integer sumSalary;

    private Integer jobCount;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
