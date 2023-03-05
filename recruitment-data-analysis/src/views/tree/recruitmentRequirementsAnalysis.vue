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
        <h3 style="text-align: center">{{ label }}岗位--学历</h3>
        <div style="background-color: white;">
          <ve-pie :data="education"></ve-pie>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="40" class="mgb20">
      <el-col :span="24">
        <h3 style="text-align: center">{{ label }}岗位--工作经验</h3>
        <div style="background-color: white;">
          <ve-bar :data="workExperience" :theme="theme"></ve-bar>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {
  getEducationalByGroupAndJobType,
  getJobTypeAndHandledJobType,
  getWorkExperienceByGroupAndJobType
} from "@/api/job";

export default {
  name: "recruitmentRequirementsAnalysis",
  data() {
    return {
      value: 'dataAnalysis',
      label: '数据分析',
      options: [],
      education:{
        columns: ["学历", "数量"],
        rows: [],
      },
      workExperience: {
        columns: ["工作经验", "数量"],
        rows: [],
      },
      theme: {
        color: ["#00BFFF"],
      },
      jobType: {
        type: '',
        handledType: ''
      },
      barSettings: {
        roseType: 'radius'
      },
    }
  },
  mounted() {
    this.jobType.handledType = this.value
    this.getParameter()
    this.getData()
  },
  watch: {
    value(val) {
      this.education.rows = []
      this.workExperience.rows = []
      this.jobType.handledType = val
      this.getData()
    }
  },
  methods: {
    getData() {
      getEducationalByGroupAndJobType(this.jobType).then(res => {
        let data = res.data
        for (let index in data) {
          this.education.rows.push({
            "学历": data[index].educational,
            "数量": data[index].count
          })
        }
      })

      getWorkExperienceByGroupAndJobType(this.jobType).then(res => {
        let data = res.data
        for (let key in data) {
          this.workExperience.rows.push({
            "工作经验": data[key].workExperience,
            "数量": data[key].count
          })
        }
      })
    },

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
