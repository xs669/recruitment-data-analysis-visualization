<template>
  <div>
    <div style="padding: 20px;">
      <span>职位方向：</span>
      <el-select v-model="label1" placeholder="请选择" style="margin-right: 20px;" @change="selectProductType1">
        <el-option
            v-for="item in options1"
            :key="item.label"
            :label="item.label"
            :value="{ label: item.label, value: item.value  }">
        </el-option>
      </el-select>
      <span>城市：</span>
      <el-select v-model="label2" placeholder="请选择" style="margin-right: 20px;" @change="selectProductType2">
        <el-option
            v-for="item in options2"
            :key="item.label"
            :label="item.label"
            :value="{ label: item.label, value: item.value  }">
        </el-option>
      </el-select>
      <el-button icon="el-icon-search" circle type="primary" @click="search"></el-button>
    </div>
    <el-row :gutter="40" class="mgb20">
      <el-col :span="24">
        <h3 style="text-align: center">{{ label1 }}岗位--{{ label2 }}地区</h3>
        <div style="background-color: white;">
          <ve-bar :data="addressAndJobType" :theme="theme"></ve-bar>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {
  getAddressByGroupAndJobType, getAllAddress,
  getJobTypeAndHandledJobType
} from "@/api/job";

export default {
  name: "numberOfJobsAndUrbanAnalysis",
  data () {
    return {
      value1: 'dataAnalysis',
      label1: '数据分析',
      label2: '北京',
      value2: 'Beijing',
      options1: [],
      options2: [],
      addressAndJobType: {
        columns: ['地区', '岗位数量'],
        rows: []
      },
      theme: {
        color: ["#00BFFF"],
      },
      jobTypeAndAddressVo: {
        handledType: '',
        transformAddress: '',
        handledAddress: ''
      }
    }
  },
  mounted() {
    this.jobTypeAndAddressVo.handledType = this.value1
    this.jobTypeAndAddressVo.transformAddress = this.value2
    this.jobTypeAndAddressVo.handledAddress = this.label2
    this.getParameter()
    this.getData()
  },
  watch: {
    value1(val) {
      this.jobTypeAndAddressVo.handledType = val
    },

    value2(val) {
      this.jobTypeAndAddressVo.transformAddress = val
    },

    label2(val) {
      this.jobTypeAndAddressVo.handledAddress = val
    }

  },
  methods: {
    search() {
      this.addressAndJobType.rows = []
      this.getData()
    },

    getData() {
      getAddressByGroupAndJobType(this.jobTypeAndAddressVo).then(res => {
        let data = res.data
        for (let index in data) {
          this.addressAndJobType.rows.push({
            "地区": data[index].handledAddress,
            "岗位数量": data[index].count
          })
        }
      })
    },

    getParameter() {
      getJobTypeAndHandledJobType().then(res => {
        let data = res.data
        for (let key in data) {
          this.options1.push({
            "label": data[key].type,
            "value": data[key].handledType
          })
        }
      })

      getAllAddress().then(res => {
        let data = res.data
        for (let index in data) {
          this.options2.push({
            "label": data[index].address,
            "value": data[index].transformAddress
          })
        }
      })
    },

    selectProductType1(data){
      // 将data对象解构
      const { label, value } = data;
      this.label1 = label;
      this.value1 = value;
    },

    selectProductType2(data){
      // 将data对象解构
      const { label, value } = data;
      this.label2 = label;
      this.value2 = value;
    }
  }
}
</script>

<style scoped lang="scss">
.mgb20 {
  margin-bottom: 20px;
}
</style>
