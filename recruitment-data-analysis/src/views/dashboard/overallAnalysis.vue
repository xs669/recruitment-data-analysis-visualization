<template>
  <div>
    <el-row :gutter="20" class="mgb20">
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <svg-icon icon-class="xiaolian" class="icon"></svg-icon>
              <div class="grid-num">{{ jobCount }}</div>
              <div class="text">招聘岗位</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <svg-icon icon-class="weixingbiao" class="icon" style="color: mediumpurple"></svg-icon>
              <div class="grid-num">{{ hotCityCount }}</div>
              <div class="text">热门城市</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <svg-icon icon-class="gongsi" class="icon" style="color:lightskyblue;"></svg-icon>
              <div class="grid-num">{{ companyCount }}</div>
              <div class="text">招聘公司</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <svg-icon icon-class="tuandui" class="icon" style="color: orange"></svg-icon>
              <div class="grid-num">{{ companyTypeCount }}</div>
              <div class="text">公司类型</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" class="mgb20">
      <el-col :span="12">
        <h3 style="text-align: center">各类岗位招聘数据统计</h3>
        <div style="background-color: white;">
          <ve-ring :data="jobDataCount" :extend="extend"></ve-ring>
        </div>
      </el-col>
      <el-col :span="12">
        <h3 style="text-align: center">前十名岗位平均薪资</h3>
        <div style="background-color: white">
          <ve-histogram :data="avgSalary" :extend="chartExtend" :theme="options"></ve-histogram>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="20" class="mgb20">
      <el-col :span="12">
        <h3 style="text-align: center">工作经验招聘比例</h3>
        <div style="background-color: white">
          <ve-funnel :data="workExperience"></ve-funnel>
        </div>
      </el-col>
      <el-col :span="12">
        <h3 style="text-align: center">教育背景招聘比例</h3>
        <div style="background-color: white">
          <ve-pie :data="education"></ve-pie>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {
  getAvgSalaryByJobType,
  getEducationalByGroup,
  getJobTypeCount,
  getMainDataCount,
  getWorkExperienceByGroup
} from "@/api/job";

export default {
  name: "overallAnalysis",
  data() {
    return {
      jobCount: 0,
      hotCityCount: 0,
      companyCount: 0,
      companyTypeCount: 0,
      jobDataCount: {
        columns: ["岗位", "数量"],
        rows: [],
      },
      avgSalary: {
        columns: ["type", "薪资"],
        rows: [],
      },
      workExperience: {
        columns: ["工作经验", "数量", "比例"],
        rows: [],
      },
      education: {
        columns: ["学历", "数量", "比例"],
        rows: [],
      },
      options: {
        color: ["#00BFFF"],
      },
      series: {
        center: ['50%', '50%'],
        ///柱状图颜色顺序，需要手动设置顺序
        itemStyle: {
          normal: {
            color: (params)=> {
              return this.colorList[params.dataIndex]
            }
          }
        },
        barWidth : 20,//柱图 -- 条宽度
      },
      colorList: [
        "#00a8e1",
        "#99cc00",
        "#e30039",
        "#fcd300",
        "#800080",
        "#00994e",
        "#ff6600",
        "#808000",
        "#db00c2",
        "#008080",
        "#0000ff",
        "#c8cc00"
      ],
      // 隐藏图上的指示线和文字
      settings: {
        label: {
          normal: {
            show: false
          }
        },
      },
      barSettings: {
        roseType: 'radius'
      },
      chartExtend: {
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
      extend: {
        // 隐藏图例
        legend: {
          show: false
        },
      }
    }
  },
  mounted() {
    this.getAllData();
  },
  methods: {
    getAllData() {
      getJobTypeCount().then(res => {
        let type = this.jobDataCount.rows
        let jobTypeCountData = res.data
        for (let key in jobTypeCountData) {
          type.push({
            "岗位": jobTypeCountData[key].jobName,
            "数量": jobTypeCountData[key].number
          })
        }
      })

      getAvgSalaryByJobType().then(res => {
        let type = this.avgSalary.rows
        let jobTypeCountData = res.data
        for (let key in jobTypeCountData) {
          type.push({
            "type": jobTypeCountData[key].type,
            "薪资": jobTypeCountData[key].avgSalary
          })
        }
      })

      getWorkExperienceByGroup().then(res => {
        let workExperience = this.workExperience.rows
        let workExperienceData = res.data
        for (let key in workExperienceData) {
          workExperience.push({
            "工作经验": workExperienceData[key].workExperience,
            "数量": workExperienceData[key].count,
            "比例": workExperienceData[key].percent,
          })
        }
      })

      getEducationalByGroup().then(res => {
        let educational = this.education.rows
        let educationalData = res.data
        for (let key in educationalData) {
          educational.push({
            "学历": educationalData[key].educational,
            "数量": educationalData[key].count,
            "比例": educationalData[key].percent
          })
        }
      })

      getMainDataCount().then(res => {
        let mainData = res.data
        for (let key in mainData) {
          this.jobCount = mainData[key].jobCount
          this.hotCityCount = mainData[key].hotCityCount
          this.companyCount = mainData[key].companyCount
          this.companyTypeCount = mainData[key].companyTypeCount
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.grid-content {
  display: flex;
  align-items: center;
  height: 100px;
}

.grid-cont-center {
  flex: 1;
  font-size: 14px;
  color: royalblue;
  text-align: center;
}

.text {
  padding-top: 5px;
  color: #333;
  font-size: small;
}

.grid-num {
  padding-top: 10px;
  font-size: 35px;
  font-weight: bold;
}

.mgb20 {
  margin-bottom: 20px;
}

.icon {
  margin-top: 0;
  font-size: 2.5rem;
  color: green;
}
</style>

