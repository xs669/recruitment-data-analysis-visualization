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
        <h3 style="text-align: center">{{ label }}岗位各公司类型占比</h3>
        <div style="background-color: white;">
          <ve-ring :data="companyNature"></ve-ring>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="40" class="mgb20">
      <el-col :span="24">
        <h3 style="text-align: center">{{ label }}岗位各公司规模占比</h3>
        <div style="background-color: white;">
          <ve-pie :data="companyPeople" :settings="barSettings"></ve-pie>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {
  getCompanyNatureByGroupAndJobType,
  getCompanyPeopleByGroupAndJobType,
  getJobTypeAndHandledJobType
} from "@/api/job";

export default {
  name: "companySituationAnalysis",
  data() {
    return {
      value: 'dataAnalysis',
      label: '数据分析',
      options: [],
      companyNature:{
        columns: ["性质", "数量"],
        rows: [],
      },
      companyPeople: {
        columns: ["规模", "数量"],
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
      getCompanyNatureByGroupAndJobType(this.jobType).then(res => {
        let data = res.data
        for (let index in data) {
          this.companyNature.rows.push({
            "性质": data[index].companyNature,
            "数量": data[index].count
          })
        }
      })

      getCompanyPeopleByGroupAndJobType(this.jobType).then(res => {
        let data = res.data
        for (let index in data) {
          this.companyPeople.rows.push({
            "规模": data[index].handledCompanyPeople,
            "数量": data[index].count
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
