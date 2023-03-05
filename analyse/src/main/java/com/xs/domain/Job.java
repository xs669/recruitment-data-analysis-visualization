package com.xs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 
 * TableName job
 */
@TableName(value ="job")
@Data
public class Job implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 岗位名称
     */
    private String title;

    /**
     * 省会
     */
    private String address;

    /**
     * 英译后省会
     */
    @TableField("transformAddress")
    private String transformAddress;

    /**
     * 处理后工作地点
     */
    @TableField("handledAddress")
    private String handledAddress;

    /**
     * 职业
     */
    private String type;

    /**
     * 处理后职业
     */
    @TableField("handledType")
    private String handledType;

    /**
     * 学历
     */
    private String educational;

    /**
     * 工作经验
     */
    @TableField("workExperience")
    private String workExperience;

    /**
     * 工作标签
     */
    @TableField("workTag")
    private String workTag;

    /**
     * 处理后工作标签
     */
    @TableField("handledWorkTag")
    private String handledWorkTag;

    /**
     * 薪资
     */
    private String salary;

    /**
     * 处理后薪资
     */
    @TableField("handledSalary")
    private String handledSalary;

    /**
     * 平均薪资
     */
    @TableField("avgSalary")
    private Integer avgSalary;

    /**
     * 年终奖
     */
    @TableField("salaryMonth")
    private String salaryMonth;

    /**
     * 公司福利
     */
    @TableField("companyTags")
    private String companyTags;

    /**
     * 处理后公司福利
     */
    @TableField("handledCompanyTags")
    private String handledCompanyTags;

    /**
     * 人事职位
     */
    @TableField("hrWork")
    private String hrWork;

    /**
     * 人事名字
     */
    @TableField("hrName")
    private String hrName;

    /**
     * 是否为实习岗位(0否 1是)
     */
    private Integer practice;

    /**
     * 公司名称
     */
    @TableField("companyTitle")
    private String companyTitle;

    /**
     * 公司头像
     */
    @TableField("companyAvatar")
    private String companyAvatar;

    /**
     * 公司性质
     */
    @TableField("companyNature")
    private String companyNature;

    /**
     * 公司状态
     */
    @TableField("companyStatus")
    private String companyStatus;

    /**
     * 公司人数
     */
    @TableField("companyPeople")
    private String companyPeople;

    /**
     * 处理后公司人数
     */
    @TableField("handledCompanyPeople")
    private String handledCompanyPeople;

    /**
     * 岗位详情地址
     */
    @TableField("detailUrl")
    private String detailUrl;

    /**
     * 公司详情地址
     */
    @TableField("companyUrl")
    private String companyUrl;

    /**
     * 行政区
     */
    private String dist;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}