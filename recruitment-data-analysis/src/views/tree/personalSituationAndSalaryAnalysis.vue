<template>
  <div>
    <div style="padding: 20px;">
      <span>职位方向：</span>
      <el-select v-model="label" placeholder="请选择" style="margin-right: 20px;" @change="selectProductType">
        <el-option
            v-for="item in options"
            :key="item.label"
            :label="item.label"
            :value="{ label: item.label, value: item.value  }">
        </el-option>
      </el-select>
    </div>
    <el-row :gutter="40" class="mgb20">
      <el-col :span="24">
        <h3 style="text-align: center">{{ label }}岗位--工作经验--平均薪资</h3>
        <div style="background-color: white;">
          <ve-histogram :data="workExperienceAndAvgSalary" :theme="theme1"></ve-histogram>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="40" class="mgb20">
      <el-col :span="24">
        <h3 style="text-align: center">{{ label }}岗位--学历--平均薪资</h3>
        <div style="background-color: white">
          <ve-bar :data="educationAndAvgSalary" :theme="theme1"></ve-bar>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {
  getEducationAndAvgSalaryByJobType,
  getJobTypeAndHandledJobType,
  getWorkExperienceAndAvgSalaryByJobType
} from "@/api/job";

export default {
  name: "personalSituationAndSalaryAnalysis",
  data() {
    return {
      value: 'dataAnalysis',
      label: '数据分析',
      options: [],
      workExperienceAndAvgSalary:{
        columns: ["工作经验", "平均月薪"],
        rows: [],
      },
      educationAndAvgSalary: {
        columns: ["学历", "平均月薪"],
        rows: [],
      },
      jobType: {
        type: '',
        handledType: ''
      },
      theme1: {
        color: ["#00BFFF"],
      },
      theme2: {
        color: ["#FFA500"],
      },
      chartExtend1: {
        xAxis: {
          // ------------------------横坐标倾斜显示
          axisLabel: {
            margin: 15,//距离
            interval: 0,
            rotate: 30,//倾斜度
          },
          triggerEvent: true
        }
      },
      chartExtend2: {
        yAxis: {
          // ------------------------横坐标倾斜显示
          axisLabel: {
            margin: 15,//距离
            interval: 0,
            rotate: -55,//倾斜度
          },
          triggerEvent: true
        }
      }
    }
  },
  mounted() {
    this.jobType.handledType = this.value
    this.getParameter()
    this.getData()
  },
  watch: {
    value(val) {
      this.workExperienceAndAvgSalary.rows = []
      this.educationAndAvgSalary.rows = []
      this.jobType.handledType = val
      this.getData()
    }
  },
  methods: {
    getParameter() {
      getJobTypeAndHandledJobType().then(res => {
        let data = res.data
        for (let key in data) {
          this.options.push({
            "label": data[key].type,
            "value": data[key].handledType
          })
        }
      })
    },

    getData() {
      getWorkExperienceAndAvgSalaryByJobType(this.jobType).then(res => {
        let data = res.data
        let workExperienceAndAvgSalary = this.workExperienceAndAvgSalary.rows
        for (let key in data) {
          workExperienceAndAvgSalary.push({
            "工作经验": data[key].workExperience,
            "平均月薪": data[key].avgSalary
          })
        }
      })

      getEducationAndAvgSalaryByJobType(this.jobType).then(res => {
        let data = res.data
        let educationAndAvgSalary = this.educationAndAvgSalary.rows
        for (let index in data) {
          educationAndAvgSalary.push({
            "学历": data[index].educational,
            "平均月薪": data[index].avgSalary
          })
        }
      })
    },

    selectProductType(data){
      // 将data对象解构
      const { label, value } = data;
      this.label = label;
      this.value = value;
    }
  }
}
</script>

<style scoped lang="scss">
.mgb20 {
  margin-bottom: 20px;
}
</style>
