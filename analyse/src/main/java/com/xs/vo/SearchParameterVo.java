package com.xs.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class SearchParameterVo implements Serializable {

    private List<String> city;

    private List<String> workExperience;

    private List<String> education;

    private List<String> jobType;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
