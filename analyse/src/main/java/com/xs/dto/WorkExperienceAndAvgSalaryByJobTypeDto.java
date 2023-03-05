package com.xs.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class WorkExperienceAndAvgSalaryByJobTypeDto implements Serializable {

    private String workExperience;

    private Integer sum;

    private Integer count;

    private Integer avgSalary;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
