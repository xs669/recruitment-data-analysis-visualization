package com.xs;

import com.alibaba.fastjson.JSON;
import com.xs.domain.Job;
import com.xs.dto.SalaryDto;
import com.xs.mapper.JobMapper;
import com.xs.vo.SearchParameterVo;
import com.xs.vo.TagsVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.*;

@SpringBootTest
class AnalyseApplicationTests {

    @Resource
    private JobMapper jobMapper;

    private static final Map<String, Integer> map = new HashMap<>();

    @Test
    void contextLoads() {
        List<SalaryDto> salaryList = jobMapper.getSalaryList();
        int num = 0;
        int sum = 0;
        for (SalaryDto salaryDto : salaryList) {
            Integer id = salaryDto.getId();
            Job job = jobMapper.selectById(id);
            String salary = salaryDto.getSalary();
            List<Integer> s = (List<Integer>) JSON.parse(salary);
            for (Integer integer : s) {
                sum += integer;
            }
            int avgSalary = sum / 2;
            job.setAvgSalary(avgSalary);
            jobMapper.updateById(job);
            num += 1;
            sum = 0;
        }
        System.out.println(num);
    }

    // 处理薪资
    @Test
    void handleSalary() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        List<SalaryDto> salaryList = jobMapper.getSalaryList();
        for (SalaryDto salaryDto : salaryList) {
            Integer id = salaryDto.getId();
            Job job = jobMapper.selectById(id);
            String salary = salaryDto.getSalary();
            List<Integer> s = (List<Integer>) JSON.parse(salary);
            for (Integer integer : s) {
                if (integer >= 1000) {
                    if (count == 0) {
                        sb.append(integer / 1000).append("-");
                        count += 1;
                    } else {
                        sb.append(integer / 1000).append("K/月");
                        count = 0;
                    }
                } else {
                    if (count == 0) {
                        sb.append(integer).append("-");
                        count += 1;
                    } else {
                        sb.append(integer).append("元/天");
                        count = 0;
                    }
                }
            }
            job.setHandledSalary(String.valueOf(sb));
            jobMapper.updateById(job);
            sb = new StringBuilder();
        }
    }

    // 处理工作地点
    @Test
    void handleAddress() {
        List<Job> addressAndDist = jobMapper.getAddressAndDist();
        StringBuilder sb = new StringBuilder();
        for (Job job : addressAndDist) {
            Job j = jobMapper.selectById(job.getId());
            String dist = job.getDist();
            if (Objects.nonNull(dist)) {
                sb.append(job.getAddress()).append("-").append(dist);
            } else {
                sb.append(job.getAddress());
            }
            j.setHandledAddress(String.valueOf(sb));
            jobMapper.updateById(j);
            sb = new StringBuilder();
        }
    }

    // 统计每个技能标签个数
    @Test
    void handleWorkTag() {
        String handledType = "java";
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
        System.out.println(tagsVoList);
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

    // 处理搜索参数
    @Test
    void getSearchParameter() {
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
        System.out.println(searchParameterVoList);
    }

    // 处理技能标签
    @Test
    void handleWorkTags() {
        List<Job> allWorkTag = jobMapper.getAllWorkTag();
        StringBuilder sb = new StringBuilder();
        for (Job job : allWorkTag) {
            Job j = jobMapper.selectById(job.getId());
            List<String> strings = (List<String>) JSON.parse(job.getWorkTag());
            for (String string : strings) {
                if (!Objects.equals(string, "")) {
                    sb.append(string).append(' ');
                }
            }
            j.setHandledWorkTag(String.valueOf(sb));
            jobMapper.updateById(j);
            sb = new StringBuilder();
        }
    }

    // 处理公司福利标签
    @Test
    void handleCompanyTags() {
        List<Job> allCompanyTags = jobMapper.getAllCompanyTags();
        StringBuilder sb = new StringBuilder();
        for (Job allCompanyTag : allCompanyTags) {
            Job job = jobMapper.selectById(allCompanyTag.getId());
            List<String> strings = (List<String>) JSON.parse(job.getCompanyTags());
            for (String string : strings) {
                if (!Objects.equals(string, "")) {
                    sb.append(string).append(' ');
                }
            }
            job.setHandledCompanyTags(String.valueOf(sb));
            jobMapper.updateById(job);
            sb = new StringBuilder();
        }
    }

    // 处理薪资
    @Test
    void handleSalaryList() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        List<Job> allSalary = jobMapper.getAllSalary();
        for (Job job : allSalary) {
            Integer id = job.getId();
            Job j = jobMapper.selectById(id);
            String salary = job.getSalary();
            String salaryMonth = job.getSalaryMonth();
            List<Integer> s = (List<Integer>) JSON.parse(salary);
            for (Integer integer : s) {
                if (integer >= 1000) {
                    if (count == 0) {
                        sb.append(integer / 1000).append("-");
                        count += 1;
                    } else {
                        if (!Objects.equals(salaryMonth, "0薪")) {
                            sb.append(integer / 1000).append("K/月").append("·").append(salaryMonth);
                            count = 0;
                        } else {
                            sb.append(integer / 1000).append("K/月");
                            count = 0;
                        }
                    }
                } else {
                    if (count == 0) {
                        sb.append(integer).append("-");
                        count += 1;
                    } else {
                        sb.append(integer).append("元/天");
                        count = 0;
                    }
                }
            }
            j.setHandledSalary(String.valueOf(sb));
            jobMapper.updateById(j);
            sb = new StringBuilder();
        }
    }

    // 处理公司人数
    @Test
    void handleCompanyPeople() {
        List<Job> allCompanyPeople = jobMapper.getAllCompanyPeople();
        List<Integer> temp = new ArrayList<>();
        temp.add(0);
        temp.add(10000);
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Job allCompanyPerson : allCompanyPeople) {
            Job job = jobMapper.selectById(allCompanyPerson.getId());
            List<Integer> integerList = (List<Integer>) JSON.parse(allCompanyPerson.getCompanyPeople());
            if (integerList.equals(temp)) {
                sb.append("10000人以上");
            } else {
                for (Integer integer : integerList) {
                    if (count == 0) {
                        sb.append(integer).append("-");
                        count += 1;
                    } else {
                        sb.append(integer).append("人");
                        count = 0;
                    }
                }
            }
            job.setHandledCompanyPeople(String.valueOf(sb));
            jobMapper.updateById(job);
            sb = new StringBuilder();
        }
    }

}
