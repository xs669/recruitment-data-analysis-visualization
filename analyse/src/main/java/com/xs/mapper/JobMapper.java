package com.xs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xs.domain.Job;
import com.xs.dto.*;
import com.xs.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author xs
* description 针对表【job】的数据库操作Mapper
* createDate 2023-02-06 13:21:18
* Entity com.xs.domain.Job
*/
@Mapper
public interface JobMapper extends BaseMapper<Job> {

    // 各类岗位招聘数据统计
    List<JobTypeCountDto> getJobTypeCount();

    // 查询薪资列表
    List<SalaryDto> getSalaryList();

    // 查询前十名岗位平均薪资
    List<AvgSalaryByJobTypeVo> getAvgSalaryByJobType();

    // 工作经验招聘比例
    List<WorkExperienceByGroupVo> getWorkExperienceByGroup();

    // 教育背景招聘比例
    List<EducationalByGroupDto> getEducationalByGroup();

    // 统计招聘数据、城市、公司、公司类型总量
    List<MainDataCountDto> getMainDataCount();

    // 查找工作地点
    List<Job> getAddressAndDist();

    // 按工作类型查找技能标签
    List<Job> getWorkTagByType(String handledType);

    // 按工作类型查找公司福利标签
    List<Job> getCompanyTagsByJobType(String handledType);

    // 查找城市、工作经验、学历、工作类型数据
    List<String> getSearchParameterCity();
    List<String> getSearchParameterWorkExperience();
    List<String> getSearchParameterEducation();
    List<String> getSearchParameterJobType();

    // 查找所有技能标签
    List<Job> getAllWorkTag();

    // 查找所有公司福利标签
    List<Job> getAllCompanyTags();

    // 查找所有薪资
    List<Job> getAllSalary();

    // 查找所有公司规模
    List<Job> getAllCompanyPeople();

    // 查找岗位薪资与工作经验的关系
    List<WorkExperienceAndAvgSalaryByJobTypeDto> getWorkExperienceAndAvgSalaryByJobType(String handledType);

    // 查找岗位薪资与学历的关系
    List<EducationAndAvgSalaryByJobTypeDto> getEducationAndAvgSalaryByJobType(String handledType);

    // 查找处理后和处理前的岗位类型
    List<JobTypeVo> getJobTypeAndHandledJobType();

    // 公司性质与平均薪资的关系
    List<CompanyNatureAndAvgSalaryByJobTypeDto> getCompanyNatureAndAvgSalaryByJobType(String handledType);

    // 公司规模与平均薪资的关系
    List<CompanyPeopleAndAvgSalaryByJobTypeVo> getCompanyPeopleAndAvgSalaryByJobType(String handledType);

    // 岗位类型与学历的关系
    List<EducationalByGroupAndJobTypeDto> getEducationalByGroupAndJobType(String handledType);

    // 岗位类型与工作经验的关系
    List<WorkExperienceByGroupAndJobTypeDto> getWorkExperienceByGroupAndJobType(String handledType);

    // 岗位类型与公司性质的关系
    List<CompanyNatureByGroupAndJobTypeDto> getCompanyNatureByGroupAndJobType(String handledType);

    // 岗位类型与公司规模的关系
    List<CompanyPeopleByGroupAndJobTypeVo> getCompanyPeopleByGroupAndJobType(String handledType);

    // 岗位数量与城市的关系
    List<AddressByGroupAndJobTypeDto> getAddressByGroupAndJobType(String handledType, String transformAddress, String handledAddress);

    // 获取所有城市信息列表
    List<AddressVo> getAllAddress();

}




