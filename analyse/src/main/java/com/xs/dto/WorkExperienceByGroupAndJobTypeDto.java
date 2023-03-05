package com.xs.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class WorkExperienceByGroupAndJobTypeDto implements Serializable {

    private String workExperience;

    private Integer count;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
