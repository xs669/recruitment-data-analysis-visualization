package com.xs.controller;

import com.xs.common.Result;
import com.xs.domain.Admin;
import com.xs.dto.SearchParameterDto;
import com.xs.service.AdminService;
import com.xs.service.JobService;
import com.xs.service.LogoutService;
import com.xs.vo.JobTypeAndAddressVo;
import com.xs.vo.JobTypeVo;
import com.xs.vo.PasswordVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
public class JobController {

    @Resource
    private JobService jobService;

    @Resource
    private LogoutService logoutService;

    @Resource
    private AdminService adminService;

    // 各类岗位招聘数据统计
    @GetMapping("/getJobTypeCount")
    public Result getJobTypeCount() {
        return jobService. getJobTypeCount();
    }

    // 查询前十名岗位平均薪资
    @GetMapping("/getAvgSalaryByJobType")
    public Result getAvgSalaryByJobType() {
        return jobService.getAvgSalaryByJobType();
    }

    // 工作经验招聘比例
    @GetMapping("/getWorkExperienceByGroup")
    public Result getWorkExperienceByGroup() {
        return jobService.getWorkExperienceByGroup();
    }

    // 教育背景招聘比例
    @GetMapping("/getEducationalByGroup")
    public Result getEducationalByGroup() {
        return jobService.getEducationalByGroup();
    }

    // 统计招聘数据、城市、公司、公司类型总量
    @GetMapping("/getMainDataCount")
    public Result getMainDataCount() {
        return jobService.getMainDataCount();
    }

    // 查找城市、工作经验、学历、工作类型数据
    @GetMapping("/getSearchParameter")
    public Result getSearchParameter() {
        return jobService.getSearchParameter();
    }

    // 查找所有招聘数据
    @PostMapping("/getAllJobData")
    public Result getAllJobData(@RequestBody SearchParameterDto searchParameterDto) {
        return jobService.getAllJobData(searchParameterDto);
    }

    // 查找岗位薪资与工作经验的关系
    @PostMapping("/getWorkExperienceAndAvgSalaryByJobType")
    public Result getWorkExperienceAndAvgSalaryByJobType(@RequestBody JobTypeVo jobTypeVo) {
        return jobService.getWorkExperienceAndAvgSalaryByJobType(jobTypeVo);
    }

    // 查找岗位薪资与学历的关系
    @PostMapping("/getEducationAndAvgSalaryByJobType")
    public Result getEducationAndAvgSalaryByJobType(@RequestBody JobTypeVo jobTypeVo) {
        return jobService.getEducationAndAvgSalaryByJobType(jobTypeVo);
    }

    // 查找处理后和处理前的岗位类型
    @GetMapping("/getJobTypeAndHandledJobType")
    public Result getJobTypeAndHandledJobType() {
        return jobService.getJobTypeAndHandledJobType();
    }

    // 按岗位类别统计技能标签个数
    @PostMapping("/getWorkTagByType")
    public Result getWorkTagByType(@RequestBody JobTypeVo jobTypeVo) {
        return jobService.getWorkTagByType(jobTypeVo);
    }

    // 岗位类型与学历的关系
    @PostMapping("/getEducationalByGroupAndJobType")
    public Result getEducationalByGroupAndJobType(@RequestBody JobTypeVo jobTypeVo) {
        return jobService.getEducationalByGroupAndJobType(jobTypeVo);
    }

    // 岗位类型与工作经验的关系
    @PostMapping("/getWorkExperienceByGroupAndJobType")
    public Result getWorkExperienceByGroupAndJobType(@RequestBody JobTypeVo jobTypeVo) {
        return jobService.getWorkExperienceByGroupAndJobType(jobTypeVo);
    }

    // 岗位类型与公司性质的关系
    @PostMapping("/getCompanyNatureByGroupAndJobType")
    public Result getCompanyNatureByGroupAndJobType(@RequestBody JobTypeVo jobTypeVo) {
        return jobService.getCompanyNatureByGroupAndJobType(jobTypeVo);
    }

    // 岗位类型与公司规模的关系
    @PostMapping("/getCompanyPeopleByGroupAndJobType")
    public Result getCompanyPeopleByGroupAndJobType(@RequestBody JobTypeVo jobTypeVo) {
        return jobService.getCompanyPeopleByGroupAndJobType(jobTypeVo);
    }

    // 公司性质与平均薪资的关系
    @PostMapping("/getCompanyNatureAndAvgSalaryByJobType")
    public Result getCompanyNatureAndAvgSalaryByJobType(@RequestBody JobTypeVo jobTypeVo) {
        return jobService.getCompanyNatureAndAvgSalaryByJobType(jobTypeVo);
    }

    // 公司规模与平均薪资的关系
    @PostMapping("/getCompanyPeopleAndAvgSalaryByJobType")
    public Result getCompanyPeopleAndAvgSalaryByJobType(@RequestBody JobTypeVo jobTypeVo) {
        return jobService.getCompanyPeopleAndAvgSalaryByJobType(jobTypeVo);
    }

    // 按工作类型查找公司福利标签
    @PostMapping("/getCompanyTagsByJobType")
    public Result getCompanyTagsByJobType(@RequestBody JobTypeVo jobTypeVo) {
        return jobService.getCompanyTagsByJobType(jobTypeVo);
    }

    // 岗位数量与城市的关系
    @PostMapping("/getAddressByGroupAndJobType")
    public Result getAddressByGroupAndJobType(@RequestBody JobTypeAndAddressVo jobTypeAndAddressVo) {
        return jobService.getAddressByGroupAndJobType(jobTypeAndAddressVo);
    }

    // 获取所有城市信息列表
    @GetMapping("/getAllAddress")
    public Result getAllAddress() {
        return jobService.getAllAddress();
    }

    /**
     * 退出登录
     */
    @PostMapping("/exit")
    public Result logout(@RequestBody Admin admin) {
        return logoutService.logout(admin);
    }

    // 查询用户信息
    @PostMapping("/getAdminDetail")
    public Result getAdminDetail(@RequestBody Admin admin) {
        return adminService.getAdminDetail(admin);
    }

    // 更新用户信息
    @PostMapping("/updateAdminDetail")
    public Result updateAdminDetail(@RequestBody Admin admin) {
        return adminService.updateAdminDetail(admin);
    }

    // 修改密码
    @PostMapping("/changePassword")
    public Result changePassword(@RequestBody PasswordVo passwordVo) {
        return adminService.changePassword(passwordVo);
    }
}
