package com.xs.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class CompanyPeopleAndAvgSalaryByJobTypeVo implements Serializable {

    private String handledCompanyPeople;

    private Integer sum;

    private Integer count;

    private Integer avgSalary;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
