package com.xs.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class SearchParameterDto implements Serializable {

    private String city;

    private String workExperience;

    private String education;

    private String jobType;

    private Integer currentPage;

    private Integer pageSize;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
