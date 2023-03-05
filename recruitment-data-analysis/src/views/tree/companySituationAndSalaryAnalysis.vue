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
        <h3 style="text-align: center">{{ label }}岗--公司性质--平均薪资</h3>
        <div style="background-color: white;">
          <ve-bar :data="companyNature" :theme="theme"></ve-bar>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="40" class="mgb20">
      <el-col :span="24">
        <h3 style="text-align: center">{{ label }}岗--公司规模--平均薪资</h3>
        <div style="background-color: white;">
          <ve-pie :data="companyPeople" :settings="barSettings"></ve-pie>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {
  getCompanyNatureAndAvgSalaryByJobType, getCompanyPeopleAndAvgSalaryByJobType,
  getJobTypeAndHandledJobType
} from "@/api/job";

export default {
  name: "companySituationAndSalaryAnalysis",
  data() {
    return {
      value: 'dataAnalysis',
      label: '数据分析',
      options: [],
      companyNature:{
        columns: ["性质", "薪资"],
        rows: [],
      },
      companyPeople: {
        columns: ["handledCompanyPeople", "avgSalary"],
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
      // 隐藏图上的指示线和文字
      settings: {
        xAxis: {
          show:false,
        },
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
      this.companyNature.rows = []
      this.companyPeople.rows = []
      this.jobType.handledType = val
      this.getData()
    }
  },
  methods: {
    getData() {
      getCompanyNatureAndAvgSalaryByJobType(this.jobType).then(res => {
        let data = res.data
        for (let index in data) {
          this.companyNature.rows.push({
            "性质": data[index].companyNature,
            "薪资": data[index].avgSalary
          })
        }
      })

      getCompanyPeopleAndAvgSalaryByJobType(this.jobType).then(res => {
        this.companyPeople.rows = res.data
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
