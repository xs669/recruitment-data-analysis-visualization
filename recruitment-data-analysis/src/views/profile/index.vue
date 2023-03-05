<template>
  <div style="margin-left: 180px;">
    <el-form :model="admin" :rules="rules" ref="admin">
      <div style="display:flex;">
        <div style="margin-top: 150px;">
          <el-form-item prop="avatar">
            <el-upload
                class="avatar-uploader"
                action="/apm/upload"
                name="pic"
                :multiple="true"
                :auto-upload="true"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
              <img v-if="admin.avatar" :src="admin.avatar" class="avatar" alt="头像">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
        </div>
        <div style="margin-left: 30px;margin-top: 70px;">
          <el-form-item prop="username" label="用户名" label-width="100px">
            <el-input v-model="admin.username" style="width: 200px;" disabled></el-input>
          </el-form-item>
          <el-form-item prop="educational" label="学历" label-width="100px">
            <el-select v-model="admin.educational" placeholder="请选择">
              <el-option
                  v-for="item in options1"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="workExperience" label="工作经验" label-width="100px">
            <el-select v-model="admin.workExperience" placeholder="请选择">
              <el-option
                  v-for="item in options2"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="address" label="意向城市" label-width="100px">
            <el-select v-model="admin.address" placeholder="请选择">
              <el-option
                  v-for="item in options3"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="type" label="意向岗位" label-width="100px">
            <el-select v-model="admin.type" placeholder="请选择">
              <el-option
                  v-for="item in options4"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <div style="margin-left: 100px;">
            <el-button type="primary" round @click="handleChange">修改</el-button>
          </div>
        </div>
      </div>
    </el-form>
  </div>
</template>

<script>
import {getAdminDetail, getSearchParameter, updateAdminDetail} from "@/api/job";

export default {
  name: "profile",
  data() {
    return {
      value1: '本科',
      value2: '经验不限',
      value3: '广州',
      value4: '数据分析',
      options1: [],
      options2: [],
      options3: [],
      options4: [],
      src: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
      admin: {
        id: '',
        username: '',
        password: '',
        avatar: '',
        role: '',
        expiration: '',
        educational: '',
        workExperience: '',
        address: '',
        type: '',
        createTime: ''
      },
      rules: {
        avatar: [{ required: true, message: "请上传头像", trigger: "blur" }],
        username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
        educational: [{ required: true, message: "请选择学历", trigger: "blur" }],
        workExperience: [{ required: true, message: "请输入工作经验", trigger: "blur" },],
        address: [{ required: true, message: "请选择意向城市", trigger: "blur" },],
        type: [{ required: true, message: "请选择意向岗位", trigger: "blur" }],
      },
    }
  },
  mounted() {
    this.admin.username = localStorage.getItem("username")
    this.getAllData()
    this.getUser()
  },
  methods: {
    getUser() {
      getAdminDetail(this.admin).then(res => {
        this.admin = res.data
      })
    },
    handleChange() {
      this.$refs.admin.validate(valid => {
        if (valid) {
          updateAdminDetail(this.admin).then(res => {
            if (res.code === 200) {
              this.getUser()
              this.$message.success(res.msg)
            } else {
              this.$message.warning(res.msg)
            }
          })
        } else {
          return false
        }
      })
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
            this.options3.push({
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
            this.options1.push({
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
    handleAvatarSuccess(res) {
      if (res.code === 20000) {
        this.admin.avatar = res.data;
        this.$message.success(res.message);
      } else {
        this.$message.error(res.message);
      }
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      if (!isJPG) {
        this.$message.warning("上传头像图片只能是 JPG 格式!");
      }
      return isJPG;
    }
  }
}
</script>

<style scoped lang="scss">
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
