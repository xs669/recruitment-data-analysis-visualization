package com.xs.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class MainDataCountDto implements Serializable {

    private Integer jobCount;

    private Integer hotCityCount;

    private Integer companyCount;

    private Integer companyTypeCount;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
