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
        <h3 style="text-align: center">{{ label }}岗位所需技能词云图</h3>
        <div style="background-color: white;">
          <ve-wordcloud :data="workTag"></ve-wordcloud>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="40" class="mgb20">
      <el-col :span="24">
        <h3 style="text-align: center">{{ label }}排名前十的所需岗位技能</h3>
        <div style="background-color: white;">
          <ve-pie :data="workTagOrderBy" :settings="barSettings"></ve-pie>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {getJobTypeAndHandledJobType, getWorkTagByType} from "@/api/job";

export default {
  name: "professionalSkillsAnalysis",
  data() {
    return {
      value: 'dataAnalysis',
      label: '数据分析',
      options: [],
      workTag:{
        columns: ["name", "value"],
        rows: [],
      },
      workTagOrderBy: {
        columns: ["技能", "数量"],
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
      this.workTag.rows = []
      this.workTagOrderBy.rows = []
      this.jobType.handledType = val
      this.getData()
    }
  },
  methods: {
    getData() {
      getWorkTagByType(this.jobType).then(res => {
        this.workTag.rows = res.data
        let data = res.data
        for (let i = 0; i < data.length; i++) {
          if (i === 10) {
            break
          }
          this.workTagOrderBy.rows.push({
            "技能": data[i].name,
            "数量": data[i].value
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
