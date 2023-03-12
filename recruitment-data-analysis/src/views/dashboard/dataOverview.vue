<template>
  <div>
    <div style="padding: 20px;">
      <span>城市：</span>
      <el-select v-model="value1" placeholder="请选择" style="margin-right: 20px;width: 120px;">
        <el-option
            v-for="item in options1"
            :key="item.label"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <span>工作经验：</span>
      <el-select v-model="value2" placeholder="请选择" style="margin-right: 20px;width: 150px;">
        <el-option
            v-for="item in options2"
            :key="item.label"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <span>学历要求：</span>
      <el-select v-model="value3" placeholder="请选择" style="margin-right: 20px;width: 120px;">
        <el-option
            v-for="item in options3"
            :key="item.label"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <span>职位：</span>
      <el-select v-model="value4" placeholder="请选择" style="margin-right: 20px;width: 180px;">
        <el-option
            v-for="item in options4"
            :key="item.label"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button icon="el-icon-search" circle type="primary" @click="search"></el-button>
    </div>
    <div>
      <el-table
          :data="tableData"
          border
          style="width: 100%;">
        <el-table-column
            align="center"
            prop="id"
            label="序号"
            width="120">
        </el-table-column>
        <el-table-column
            align="center"
            prop="title"
            label="招聘名称"
            width="250">
          <template v-slot="scope">
            <a :href="scope.row.detailUrl" style="color:royalblue;">{{ scope.row.title }}</a>
          </template>
        </el-table-column>
        <el-table-column
            align="center"
            prop="type"
            label="岗位类型"
            width="150">
        </el-table-column>
        <el-table-column
            align="center"
            prop="handledAddress"
            width="120"
            label="工作地点">
        </el-table-column>
        <el-table-column
            align="center"
            prop="workExperience"
            width="120"
            label="工作经验要求">
        </el-table-column>
        <el-table-column
            align="center"
            prop="educational"
            width="120"
            label="学历要求">
        </el-table-column>
        <el-table-column
            align="center"
            prop="handledSalary"
            width="120"
            label="薪资">
        </el-table-column>
        <el-table-column
            align="center"
            prop="practice"
            width="120"
            label="工作职责">
          <template v-slot="scope">
            {{ scope.row.practice ? '实习' : '全职' }}
          </template>
        </el-table-column>
        <el-table-column
            align="center"
            prop="handledWorkTag"
            width="300"
            label="技能标签">
          <template v-slot="scope">
            <p>
              {{ scope.row.handledWorkTag }}
            </p>
          </template>
        </el-table-column>
        <el-table-column
            align="center"
            prop="handledCompanyTags"
            width="300"
            label="公司福利">
        </el-table-column>
        <el-table-column
            align="center"
            prop="companyTitle"
            width="150"
            label="公司名称">
          <template v-slot="scope">
            <a :href="scope.row.companyUrl" style="color: royalblue">{{ scope.row.companyTitle }}</a>
          </template>
        </el-table-column>
        <el-table-column
            align="center"
            prop="companyAvatar"
            width="150"
            label="公司头像">
          <template v-slot="scope">
            <a :href="scope.row.companyUrl"><img :src="scope.row.companyAvatar" alt="" style="width: 120px;height: 120px;"></a>
          </template>
        </el-table-column>
        <el-table-column
            align="center"
            prop="companyNature"
            width="120"
            label="公司性质">
        </el-table-column>
        <el-table-column
            align="center"
            prop="companyStatus"
            width="120"
            label="公司融资情况">
        </el-table-column>
        <el-table-column
            align="center"
            prop="handledCompanyPeople"
            width="180"
            label="公司规模">
        </el-table-column>
      </el-table>
      <div style="margin: 10px;">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[5, 10, 15, 20]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import {getAllJobData, getSearchParameter} from "@/api/job";

export default {
  name: "dataOverview",
  data() {
    return {
      options1: [],
      options2: [],
      options3: [],
      options4: [],
      value1: '',
      value2: '',
      value3: '',
      value4: '',
      total: 0,
      tableData: [],
      currentPage: 1,
      pageSize: 5,
      searchParameterVo: {
        city: '',
        workExperience: '',
        education: '',
        jobType: '',
        currentPage: '',
        pageSize: ''
      }
    }
  },
  mounted() {
    this.getAllData()
    this.getJobData()
  },
  methods: {
    handleSizeChange(val) {
      this.pageSize = val;
      this.getJobData()
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.getJobData()
    },
    getAllData() {
      getSearchParameter().then(res => {
        let data = res.data
        for (let key in data) {
          let citys = data[key].city
          let workExperiences = data[key].workExperience
          let education = data[key].education
          let jobType = data[key].jobType
          for (let index in citys) {
            this.options1.push({
              "label": citys[index],
              "value": citys[index]
            })
          }
          for (let index in workExperiences) {
            this.options2.push({
              "label": workExperiences[index],
              "value": workExperiences[index]
            })
          }
          for (let index in education) {
            this.options3.push({
              "label": education[index],
              "value": education[index]
            })
          }
          for (let index in jobType) {
            this.options4.push({
              "label": jobType[index],
              "value": jobType[index]
            })
          }
        }
      })
    },
    getJobData() {
      this.searchParameterVo.currentPage = this.currentPage
      this.searchParameterVo.pageSize = this.pageSize
      getAllJobData(this.searchParameterVo).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    search() {
      this.searchParameterVo.city = this.value1
      this.searchParameterVo.workExperience = this.value2
      this.searchParameterVo.education = this.value3
      this.searchParameterVo.jobType = this.value4
      this.searchParameterVo.currentPage = 1
      this.searchParameterVo.pageSize = this.pageSize
      getAllJobData(this.searchParameterVo).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
        this.currentPage = 1
      })
    }
  },
}
</script>

<style lang="scss" scoped>

</style>

