package com.xs.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.common.Result;
import com.xs.domain.Job;
import com.xs.dto.*;
import com.xs.mapper.JobMapper;
import com.xs.service.JobService;
import com.xs.util.RedisCache;
import com.xs.vo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
* @author xs
* description 针对表【job】的数据库操作Service实现
* createDate 2023-02-06 13:21:18
*/
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService{

    @Resource
    private JobMapper jobMapper;

    @Resource
    private RedisCache redisCache;

    private static Map<String, Integer> map = new HashMap<>();

    // 各类岗位招聘数据统计
    @Override
    public Result getJobTypeCount() {
        if (redisCache.getCacheList("jobTypeCount").isEmpty()) {
            List<JobTypeCountDto> jobTypeCount = jobMapper.getJobTypeCount();
            if (jobTypeCount.isEmpty()) {
                return Result.error("数据为空");
            } else {
                redisCache.setCacheList("jobTypeCount", jobTypeCount);
                return Result.ok("查询成功", jobTypeCount);
            }
        } else {
            return Result.ok("查询成功", redisCache.getCacheList("jobTypeCount"));
        }

    }

    // 查询前十名岗位平均薪资
    @Override
    public Result getAvgSalaryByJobType() {
        if (redisCache.getCacheList("avgSalaryByJobTypeDtoList").isEmpty()) {
            List<AvgSalaryByJobTypeDto> avgSalaryByJobTypeDtoList = new ArrayList<>();
            List<AvgSalaryByJobTypeVo> avgSalaryByJobType = jobMapper.getAvgSalaryByJobType();
            for (AvgSalaryByJobTypeVo avgSalaryByJobTypeVo : avgSalaryByJobType) {
                AvgSalaryByJobTypeDto avgSalaryByJobTypeDto = new AvgSalaryByJobTypeDto();
                String type = avgSalaryByJobTypeVo.getType();
                if (type.equals("嵌入式软件开发工程师")) {
                    avgSalaryByJobTypeVo.setType("嵌入式软件开发");
                }
                if (type.equals("游戏客户端开发工程师")) {
                    avgSalaryByJobTypeVo.setType("游戏客户端开发");
                }
                avgSalaryByJobTypeDto.setType(avgSalaryByJobTypeVo.getType());
                avgSalaryByJobTypeDto.setAvgSalary(avgSalaryByJobTypeVo.getSumSalary() / avgSalaryByJobTypeVo.getJobCount());
                avgSalaryByJobTypeDtoList.add(avgSalaryByJobTypeDto);
            }
            if (avgSalaryByJobTypeDtoList.isEmpty()) {
                return Result.error("查询失败");
            } else {
                redisCache.setCacheList("avgSalaryByJobTypeDtoList", avgSalaryByJobTypeDtoList);
                return Result.ok("查询成功", avgSalaryByJobTypeDtoList);
            }
        } else {
            return Result.ok("查询成功", redisCache.getCacheList("avgSalaryByJobTypeDtoList"));
        }
    }

    /**
     * 工作经验招聘比例
     */
    @Override
    public Result getWorkExperienceByGroup() {
        if (redisCache.getCacheList("workExperienceByGroup").isEmpty()) {
            List<WorkExperienceByGroupVo> workExperienceByGroup = jobMapper.getWorkExperienceByGroup();
            Float sum = 0f;
            for (WorkExperienceByGroupVo workExperienceByGroupVo : workExperienceByGroup) {
                sum += workExperienceByGroupVo.getCount();
            }
            for (WorkExperienceByGroupVo workExperienceByGroupVo : workExperienceByGroup) {
                workExperienceByGroupVo.setPercent(workExperienceByGroupVo.getCount() / sum);
            }
            if (workExperienceByGroup.isEmpty()) {
                return Result.error("查询失败");
            } else {
                redisCache.setCacheList("workExperienceByGroup", workExperienceByGroup);
                return Result.ok("查询成功", workExperienceByGroup);
            }
        } else {
            return Result.ok("查询成功", redisCache.getCacheList("workExperienceByGroup"));
        }
    }

    /**
     * 教育背景招聘比例
     */
    @Override
    public Result getEducationalByGroup() {
        if (redisCache.getCacheList("educationalByGroup").isEmpty()) {
            Float sum = 0f;
            List<EducationalByGroupDto> educationalByGroup = jobMapper.getEducationalByGroup();
            for (EducationalByGroupDto educationalByGroupDto : educationalByGroup) {
                sum += educationalByGroupDto.getCount();
            }
            for (EducationalByGroupDto educationalByGroupDto : educationalByGroup) {
                educationalByGroupDto.setPercent(educationalByGroupDto.getCount() / sum);
            }
            if (educationalByGroup.isEmpty()) {
                return Result.error("查询失败");
            } else {
                redisCache.setCacheList("educationalByGroup", educationalByGroup);
                return Result.ok("查询成功", educationalByGroup);
            }
        } else {
            return Result.ok("查询成功", redisCache.getCacheList("educationalByGroup"));
        }
    }

    /**
     * 统计招聘数据、城市、公司、公司类型总量
     */
    @Override
    public Result getMainDataCount() {
        if (redisCache.getCacheList("mainDataCount").isEmpty()) {
            List<MainDataCountDto> mainDataCount = jobMapper.getMainDataCount();
            if (mainDataCount.isEmpty()) {
                return Result.error("查询失败");
            } else {
                redisCache.setCacheList("mainDataCount", mainDataCount);
                return Result.ok("查询成功", mainDataCount);
            }
        } else {
            return Result.ok("查询成功", redisCache.getCacheList("mainDataCount"));
        }
    }

    /**
     * 查找城市、工作经验、学历、工作类型数据
     */
    @Override
    public Result getSearchParameter() {
        if (redisCache.getCacheList("searchParameterVoList").isEmpty()) {
            List<SearchParameterVo> searchParameterVoList = new ArrayList<>();
            List<String> searchParameterCity = jobMapper.getSearchParameterCity();
            List<String> searchParameterWorkExperience = jobMapper.getSearchParameterWorkExperience();
            List<String> searchParameterEducation = jobMapper.getSearchParameterEducation();
            List<String> searchParameterJobType = jobMapper.getSearchParameterJobType();
            SearchParameterVo searchParameterVo = new SearchParameterVo();
            searchParameterVo.setCity(searchParameterCity);
            searchParameterVo.setWorkExperience(searchParameterWorkExperience);
            searchParameterVo.setEducation(searchParameterEducation);
            searchParameterVo.setJobType(searchParameterJobType);
            searchParameterVoList.add(searchParameterVo);
            redisCache.setCacheList("searchParameterVoList", searchParameterVoList);
            return Result.ok("查询成功", searchParameterVoList);
        } else {
            return Result.ok("查询成功", redisCache.getCacheList("searchParameterVoList"));
        }
    }

    /**
     * 查找所有招聘数据
     */
    @Override
    public Result getAllJobData(SearchParameterDto searchParameterDto) {
        IPage<Job> jobIPage = new Page<>(searchParameterDto.getCurrentPage(), searchParameterDto.getPageSize());
        LambdaQueryWrapper<Job> lqw = new LambdaQueryWrapper<>();
        lqw.eq(!Objects.equals(searchParameterDto.getCity(), ""), Job::getAddress, searchParameterDto.getCity())
                .eq(!Objects.equals(searchParameterDto.getWorkExperience(), ""), Job::getWorkExperience, searchParameterDto.getWorkExperience())
                .eq(!Objects.equals(searchParameterDto.getEducation(), "") ,Job::getEducational, searchParameterDto.getEducation())
                .eq(!Objects.equals(searchParameterDto.getJobType(), "") ,Job::getType, searchParameterDto.getJobType());
        IPage<Job> selectPageList = jobMapper.selectPage(jobIPage, lqw);
        return Result.ok("查询成功", selectPageList);
    }

    // 查找岗位薪资与工作经验的关系
    @Override
    public Result getWorkExperienceAndAvgSalaryByJobType(JobTypeVo jobTypeVo) {
        String handledType = jobTypeVo.getHandledType();
        if (redisCache.getCacheList(handledType + "WorkExperienceAndAvgSalary").isEmpty()) {
            List<WorkExperienceAndAvgSalaryByJobTypeDto> workExperienceAndAvgSalaryByJobType = jobMapper.getWorkExperienceAndAvgSalaryByJobType(handledType);
            for (WorkExperienceAndAvgSalaryByJobTypeDto workExperienceAndAvgSalaryByJobTypeDto : workExperienceAndAvgSalaryByJobType) {
                workExperienceAndAvgSalaryByJobTypeDto.setAvgSalary(workExperienceAndAvgSalaryByJobTypeDto.getSum() / workExperienceAndAvgSalaryByJobTypeDto.getCount());
            }
            if (workExperienceAndAvgSalaryByJobType.isEmpty()) {
                return Result.error("查询失败");
            } else {
                redisCache.setCacheList(handledType + "WorkExperienceAndAvgSalary", workExperienceAndAvgSalaryByJobType);
                return Result.ok("查询成功", workExperienceAndAvgSalaryByJobType);
            }
        } else {
            return Result.ok("查询成功", redisCache.getCacheList(handledType + "WorkExperienceAndAvgSalary"));
        }
    }

    // 查找岗位薪资与学历的关系
    @Override
    public Result getEducationAndAvgSalaryByJobType(JobTypeVo jobTypeVo) {
        String handledType = jobTypeVo.getHandledType();
        if (redisCache.getCacheList(handledType + "EducationAndAvgSalaryByJobType").isEmpty()) {
            List<EducationAndAvgSalaryByJobTypeDto> educationAndAvgSalaryByJobType = jobMapper.getEducationAndAvgSalaryByJobType(handledType);
            for (EducationAndAvgSalaryByJobTypeDto educationAndAvgSalaryByJobTypeDto : educationAndAvgSalaryByJobType) {
                educationAndAvgSalaryByJobTypeDto.setAvgSalary(educationAndAvgSalaryByJobTypeDto.getSum() / educationAndAvgSalaryByJobTypeDto.getCount());
            }
            if (educationAndAvgSalaryByJobType.isEmpty()) {
                return Result.error("查询失败");
            } else {
                redisCache.setCacheList(handledType + "EducationAndAvgSalaryByJobType", educationAndAvgSalaryByJobType);
                return Result.ok("查询成功", educationAndAvgSalaryByJobType);
            }
        } else {
            return Result.ok("查询成功", redisCache.getCacheList(handledType + "EducationAndAvgSalaryByJobType"));
        }
    }

    // 查找处理后和处理前的岗位类型
    @Override
    public Result getJobTypeAndHandledJobType() {
        if (redisCache.getCacheList("jobTypeAndHandledJobType").isEmpty()) {
            List<JobTypeVo> jobTypeAndHandledJobType = jobMapper.getJobTypeAndHandledJobType();
            if (jobTypeAndHandledJobType.isEmpty()) {
                return Result.error("查询失败");
            } else {
                redisCache.setCacheList("jobTypeAndHandledJobType", jobTypeAndHandledJobType);
                return Result.ok("查询成功", jobTypeAndHandledJobType);
            }
        } else {
            return Result.ok("查询成功", redisCache.getCacheList("jobTypeAndHandledJobType"));
        }
    }

    // 按岗位类别统计技能标签个数
    @Override
    public Result getWorkTagByType(JobTypeVo jobTypeVo) {
        String handledType = jobTypeVo.getHandledType();
        if (redisCache.getCacheList(handledType + "WorkTagByType").isEmpty()) {
            List<String> stringList = new ArrayList<>();
            List<TagsVo> tagsVoList = new ArrayList<>();
            List<Job> workTagByType = jobMapper.getWorkTagByType(handledType);
            for (Job job : workTagByType) {
                List<String> strings = (List<String>) JSON.parse(job.getWorkTag());
                for (String string : strings) {
                    if (!Objects.equals(string, "")) {
                        stringList.add(string);
                    }
                }
            }
            getCount(stringList);
            List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
            //降序排序
            list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
            for (Map.Entry<String, Integer> newEntry : list) {
                TagsVo tagsVo = new TagsVo();
                tagsVo.setName(newEntry.getKey());
                tagsVo.setValue(newEntry.getValue());
                tagsVoList.add(tagsVo);
            }
            if (tagsVoList.isEmpty()) {
                return Result.error("查询失败");
            } else {
                map = new HashMap<>();
                redisCache.setCacheList(handledType + "WorkTagByType", tagsVoList);
                return Result.ok("查询成功", tagsVoList);
            }
        } else {
            return Result.ok("查询成功", redisCache.getCacheList(handledType + "WorkTagByType"));
        }
    }

    // 按工作类型查找公司福利标签
    @Override
    public Result getCompanyTagsByJobType(JobTypeVo jobTypeVo) {
        String handledType = jobTypeVo.getHandledType();
        if (redisCache.getCacheList(handledType + "CompanyTagsByJobType").isEmpty()) {
            List<String> stringList = new ArrayList<>();
            List<TagsVo> tagsVoList = new ArrayList<>();
            List<Job> companyTagsByJobType = jobMapper.getCompanyTagsByJobType(handledType);
            for (Job job : companyTagsByJobType) {
                List<String> strings = (List<String>) JSON.parse(job.getCompanyTags());
                for (String string : strings) {
                    if (!Objects.equals(string, "")) {
                        stringList.add(string);
                    }
                }
            }
            getCount(stringList);
            List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
            //降序排序
            list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
            for (Map.Entry<String, Integer> newEntry : list) {
                TagsVo tagsVo = new TagsVo();
                tagsVo.setName(newEntry.getKey());
                tagsVo.setValue(newEntry.getValue());
                tagsVoList.add(tagsVo);
            }
            if (tagsVoList.isEmpty()) {
                return Result.error("查询失败");
            } else {
                map = new HashMap<>();
                redisCache.setCacheList(handledType + "CompanyTagsByJobType", tagsVoList);
                return Result.ok("查询成功", tagsVoList);
            }
        } else {
            return Result.ok("查询成功", redisCache.getCacheList(handledType + "CompanyTagsByJobType"));
        }
    }

    // 岗位数量与城市的关系
    @Override
    public Result getAddressByGroupAndJobType(JobTypeAndAddressVo jobTypeAndAddressVo) {
        String handledType = jobTypeAndAddressVo.getHandledType();
        String transformAddress = jobTypeAndAddressVo.getTransformAddress();
        String handledAddress = jobTypeAndAddressVo.getHandledAddress();
        if (redisCache.getCacheList(handledType + transformAddress + "AddressByGroupAndJobType").isEmpty()) {
            List<AddressByGroupAndJobTypeDto> addressByGroupAndJobType = jobMapper.getAddressByGroupAndJobType(handledType, transformAddress, handledAddress);
            if (addressByGroupAndJobType.isEmpty()) {
                return Result.error("查询失败");
            } else {
                redisCache.setCacheList(handledType + transformAddress + "AddressByGroupAndJobType", addressByGroupAndJobType);
                return Result.ok("查询成功", addressByGroupAndJobType);
            }
        } else {
            return Result.ok("查询成功", redisCache.getCacheList(handledType + transformAddress + "AddressByGroupAndJobType"));
        }
    }

    // 获取所有城市信息列表
    @Override
    public Result getAllAddress() {
        if (redisCache.getCacheList("allAddress").isEmpty()) {
            List<AddressVo> allAddress = jobMapper.getAllAddress();
            if (allAddress.isEmpty()) {
                return Result.error("查询失败");
            } else {
                redisCache.setCacheList("allAddress", allAddress);
                return Result.ok("查询成功", allAddress);
            }
        } else {
            return Result.ok("查询成功", redisCache.getCacheList("allAddress"));
        }
    }

    // 岗位类型与学历的关系
    @Override
    public Result getEducationalByGroupAndJobType(JobTypeVo jobTypeVo) {
        String handledType = jobTypeVo.getHandledType();
        if (redisCache.getCacheList(handledType + "EducationalByGroupAndJobType").isEmpty()) {
            List<EducationalByGroupAndJobTypeDto> educationalByGroupAndJobType = jobMapper.getEducationalByGroupAndJobType(handledType);
            if (educationalByGroupAndJobType.isEmpty()) {
                return Result.error("查询失败");
            } else {
                redisCache.setCacheList(handledType + "EducationalByGroupAndJobType", educationalByGroupAndJobType);
                return Result.ok("查询成功", educationalByGroupAndJobType);
            }
        } else {
            return Result.ok("查询成功", redisCache.getCacheList(handledType + "EducationalByGroupAndJobType"));
        }
    }

    // 岗位类型与工作经验的关系
    @Override
    public Result getWorkExperienceByGroupAndJobType(JobTypeVo jobTypeVo) {
        String handledType = jobTypeVo.getHandledType();
        if (redisCache.getCacheList(handledType + "WorkExperienceByGroupAndJobType").isEmpty()) {
            List<WorkExperienceByGroupAndJobTypeDto> workExperienceByGroupAndJobType = jobMapper.getWorkExperienceByGroupAndJobType(handledType);
            if (workExperienceByGroupAndJobType.isEmpty()) {
                return Result.error("查询失败");
            } else {
                redisCache.setCacheList(handledType + "WorkExperienceByGroupAndJobType", workExperienceByGroupAndJobType);
                return Result.ok("查询成功", workExperienceByGroupAndJobType);
            }
        } else {
            return Result.ok("查询成功", redisCache.getCacheList(handledType + "WorkExperienceByGroupAndJobType"));
        }
    }

    // 岗位类型与公司性质的关系
    @Override
    public Result getCompanyNatureByGroupAndJobType(JobTypeVo jobTypeVo) {
        String handledType = jobTypeVo.getHandledType();
        if (redisCache.getCacheList(handledType + "CompanyNatureByGroupAndJobType").isEmpty()) {
            List<CompanyNatureByGroupAndJobTypeDto> companyNatureByGroupAndJobType = jobMapper.getCompanyNatureByGroupAndJobType(handledType);
            if (companyNatureByGroupAndJobType.isEmpty()) {
                return Result.error("查询失败");
            } else {
                redisCache.setCacheList(handledType + "CompanyNatureByGroupAndJobType", companyNatureByGroupAndJobType);
                return Result.ok("查询成功", companyNatureByGroupAndJobType);
            }
        } else {
            return Result.ok("查询成功", redisCache.getCacheList(handledType + "CompanyNatureByGroupAndJobType"));
        }
    }

    // 岗位类型与公司规模的关系
    @Override
    public Result getCompanyPeopleByGroupAndJobType(JobTypeVo jobTypeVo) {
        String handledType = jobTypeVo.getHandledType();
        if (redisCache.getCacheList(handledType + "CompanyPeopleByGroupAndJobType").isEmpty()) {
            List<CompanyPeopleByGroupAndJobTypeVo> companyPeopleByGroupAndJobType = jobMapper.getCompanyPeopleByGroupAndJobType(handledType);
            if (companyPeopleByGroupAndJobType.isEmpty()) {
                return Result.error("查询失败");
            } else {
                redisCache.setCacheList(handledType + "CompanyPeopleByGroupAndJobType", companyPeopleByGroupAndJobType);
                return Result.ok("查询成功", companyPeopleByGroupAndJobType);
            }
        } else {
            return Result.ok("查询成功", redisCache.getCacheList(handledType + "CompanyPeopleByGroupAndJobType"));
        }
    }

    // 公司性质与平均薪资的关系
    @Override
    public Result getCompanyNatureAndAvgSalaryByJobType(JobTypeVo jobTypeVo) {
        String handledType = jobTypeVo.getHandledType();
        if (redisCache.getCacheList(handledType + "CompanyNatureAndAvgSalaryByJobType").isEmpty()) {
            List<CompanyNatureAndAvgSalaryByJobTypeDto> companyNatureAndAvgSalaryByJobType = jobMapper.getCompanyNatureAndAvgSalaryByJobType(handledType);
            for (CompanyNatureAndAvgSalaryByJobTypeDto companyNatureAndAvgSalaryByJobTypeDto : companyNatureAndAvgSalaryByJobType) {
                companyNatureAndAvgSalaryByJobTypeDto.setAvgSalary(companyNatureAndAvgSalaryByJobTypeDto.getSum() / companyNatureAndAvgSalaryByJobTypeDto.getCount());
            }
            if (companyNatureAndAvgSalaryByJobType.isEmpty()) {
                return Result.error("查询失败");
            } else {
                redisCache.setCacheList(handledType + "CompanyNatureAndAvgSalaryByJobType", companyNatureAndAvgSalaryByJobType);
                return Result.ok("查询成功", companyNatureAndAvgSalaryByJobType);
            }
        } else {
            return Result.ok("查询成功", redisCache.getCacheList(handledType + "CompanyNatureAndAvgSalaryByJobType"));
        }
    }

    // 公司规模与平均薪资的关系
    @Override
    public Result getCompanyPeopleAndAvgSalaryByJobType(JobTypeVo jobTypeVo) {
        String handledType = jobTypeVo.getHandledType();
        if (redisCache.getCacheList(handledType + "CompanyPeopleAndAvgSalaryByJobType").isEmpty()) {
            List<CompanyPeopleAndAvgSalaryByJobTypeVo> companyPeopleAndAvgSalaryByJobType = jobMapper.getCompanyPeopleAndAvgSalaryByJobType(handledType);
            for (CompanyPeopleAndAvgSalaryByJobTypeVo companyPeopleAndAvgSalaryByJobTypeVo : companyPeopleAndAvgSalaryByJobType) {
                companyPeopleAndAvgSalaryByJobTypeVo.setAvgSalary(companyPeopleAndAvgSalaryByJobTypeVo.getSum() / companyPeopleAndAvgSalaryByJobTypeVo.getCount());
            }
            if (companyPeopleAndAvgSalaryByJobType.isEmpty()) {
                return Result.error("查询失败");
            } else {
                redisCache.setCacheList(handledType + "CompanyPeopleAndAvgSalaryByJobType", companyPeopleAndAvgSalaryByJobType);
                return Result.ok("查询成功", companyPeopleAndAvgSalaryByJobType);
            }
        } else {
            return Result.ok("查询成功", redisCache.getCacheList(handledType + "CompanyPeopleAndAvgSalaryByJobType"));
        }
    }

    public static void getCount(List<String> arr){
        loop:for (int i = 0 ;i < arr.size(); i++) {
            //这段代码用于实现在比较之前输出的的语句是否有曾经输出过的元素值,
            //如果有则跳出loop循环,不执行下面的打印语句.
            if (i > 0) {
                for (int j = 0; j <i ; j++) {
                    if (Objects.equals(arr.get(i), arr.get(j))) {
                        continue loop;
                    }
                }
            }
            // 这段代码用于实现代码的判断元素值出现的次数并且打印出输出次数.
            int count = 0;
            for (String k : arr) {
                if (Objects.equals(arr.get(i), k)) {
                    count++;
                }
            }
            map.put(arr.get(i), count);
        }
    }
}




