package com.xs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xs.common.Result;
import com.xs.domain.Job;
import com.xs.dto.SearchParameterDto;
import com.xs.vo.JobTypeAndAddressVo;
import com.xs.vo.JobTypeVo;

/**
* @author xs
* description 针对表【job】的数据库操作Service
* createDate 2023-02-06 13:21:18
*/
public interface JobService extends IService<Job> {

    // 各类岗位招聘数据统计
    Result getJobTypeCount();

    // 查询前十名岗位平均薪资
    Result getAvgSalaryByJobType();

    // 工作经验招聘比例
    Result getWorkExperienceByGroup();

    // 教育背景招聘比例
    Result getEducationalByGroup();

    // 统计招聘数据、城市、公司、公司类型总量
    Result getMainDataCount();

    // 查找城市、工作经验、学历、工作类型数据
    Result getSearchParameter();

    // 查找所有招聘数据
    Result getAllJobData(SearchParameterDto searchParameterDto);

    // 查找岗位薪资与工作经验的关系
    Result getWorkExperienceAndAvgSalaryByJobType(JobTypeVo jobTypeVo);

    // 查找岗位薪资与学历的关系
    Result getEducationAndAvgSalaryByJobType(JobTypeVo jobTypeVo);

    // 查找处理后和处理前的岗位类型
    Result getJobTypeAndHandledJobType();

    // 按岗位类别统计技能标签个数g
    Result getWorkTagByType(JobTypeVo jobTypeVo);

    // 岗位类型与学历的关系
    Result getEducationalByGroupAndJobType(JobTypeVo jobTypeVo);

    // 岗位类型与工作经验的关系
    Result getWorkExperienceByGroupAndJobType(JobTypeVo jobTypeVo);

    // 岗位类型与公司性质的关系
    Result getCompanyNatureByGroupAndJobType(JobTypeVo jobTypeVo);

    // 岗位类型与公司规模的关系
    Result getCompanyPeopleByGroupAndJobType(JobTypeVo jobTypeVo);

    // 公司性质与平均薪资的关系
    Result getCompanyNatureAndAvgSalaryByJobType(JobTypeVo jobTypeVo);

    // 公司规模与平均薪资的关系
    Result getCompanyPeopleAndAvgSalaryByJobType(JobTypeVo jobTypeVo);

    // 按工作类型查找公司福利标签
    Result getCompanyTagsByJobType(JobTypeVo jobTypeVo);

    // 岗位数量与城市的关系
    Result getAddressByGroupAndJobType(JobTypeAndAddressVo jobTypeAndAddressVo);

    // 获取所有城市信息列表
    Result getAllAddress();
}
