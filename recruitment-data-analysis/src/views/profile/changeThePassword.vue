<template>
  <div style="margin: 80px auto auto 60px;">
    <el-form :model="password" :rules="rules" ref="password">
      <div style="margin-bottom: 30px;">
        <el-form-item prop="oldPassword" label="旧密码" label-width="100px">
          <el-input
              v-model="password.oldPassword"
              style="width: 200px;"
              show-password>
          </el-input>
        </el-form-item>
      </div>
      <div style="margin-bottom: 30px;">
        <el-form-item prop="newPassword" label="新密码" label-width="100px">
          <el-input
              v-model="password.newPassword"
              style="width: 200px;"
              show-password>
          </el-input>
        </el-form-item>
      </div>
      <div style="margin-bottom: 30px;">
        <el-form-item prop="rePassword" label="确认密码" label-width="100px">
          <el-input
              v-model="password.rePassword"
              style="width: 200px;"
              show-password>
          </el-input>
        </el-form-item>
      </div>
      <div style="margin-left: 80px;">
        <el-button type="primary" round @click="changePassword">修改</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import {changePassword} from "@/api/job";

export default {
  name: "changeThePassword",
  data() {
    return {
      password: {
        oldPassword: '',
        newPassword: '',
        rePassword: '',
        username: ''
      },
      rules: {
        oldPassword: [{ required: true, message: "请输入旧密码", trigger: "blur" }],
        newPassword: [{ required: true, message: "请输入新密码", trigger: "blur" }],
        rePassword: [{ required: true, message: "请输入确认密码", trigger: "blur" }]
      },
    }
  },
  mounted() {
    this.password.username = localStorage.getItem("username")
  },
  methods: {
    changePassword() {
      this.$refs.password.validate(valid => {
        if (valid) {
          changePassword(this.password).then(res => {
            if (res.code === 200) {
              this.$message.success(res.msg)
            } else {
              this.$message.warning(res.msg)
            }
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">

</style>
