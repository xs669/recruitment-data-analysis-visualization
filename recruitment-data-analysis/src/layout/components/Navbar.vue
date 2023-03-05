<template>
	<div class="navbar">
		<hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar"/>
		<breadcrumb class="breadcrumb-container"/>
    <div class="right-menu">
      <Screenfull id="screenfull" class="right-menu-item hover-effect"></Screenfull>
      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
        <div class="avatar-wrapper">
          <img :src="admin.avatar ? admin.avatar : imgUrl + '?imageView2/1/w/80/h/80'" class="user-avatar" alt="">
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/profile/index">
            <el-dropdown-item>个人中心</el-dropdown-item>
          </router-link>
          <el-dropdown-item divided @click.native="handleLogout">
            <span style="display:block;">退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
	</div>
</template>

<script>
	import {mapGetters} from 'vuex'
	import Breadcrumb from '@/components/Breadcrumb'
	import Hamburger from '@/components/Hamburger'
  import Screenfull from "@/components/Screenfull";
  import {getAdminDetail, logout} from "@/api/job";

	export default {
    data() {
      return {
        imgUrl: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
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
      }
    },
		components: {
      Screenfull,
			Breadcrumb,
			Hamburger
		},
		computed: {
			...mapGetters([
				'sidebar',
			])
		},
    mounted() {
      this.admin.username = localStorage.getItem("username")
      this.getUser()
    },
    methods: {
      getUser() {
        getAdminDetail(this.admin).then(res => {
          this.admin = res.data
        })
      },
			toggleSideBar() {
				this.$store.dispatch('app/toggleSideBar')
			},
      handleLogout() {
        this.admin.username = localStorage.getItem("username")
        logout(this.admin).then(res => {
          if (res.code === 200) {
            this.$message.success(res.msg)
            localStorage.removeItem("token")
            localStorage.removeItem("username")
            this.$router.push({path: '/login'})
          } else {
            this.$message.warning(res.msg)
          }
        })
      }
		}
	}
</script>

<style lang="scss" scoped>
	.navbar {
		height: 50px;
		overflow: hidden;
		position: relative;
		background: #fff;
		box-shadow: 0 1px 4px rgba(0, 21, 41, .08);
		user-select: none;

		.hamburger-container {
			line-height: 46px;
			height: 100%;
			float: left;
			cursor: pointer;
			transition: background .3s;
			-webkit-tap-highlight-color: transparent;

			&:hover {
				background: rgba(0, 0, 0, .025)
			}
		}

		.breadcrumb-container {
			float: left;
		}

    .right-menu {
      float: right;
      height: 100%;
      line-height: 50px;

      &:focus {
        outline: none;
      }

      .right-menu-item {
        display: inline-block;
        padding: 0 8px;
        height: 100%;
        font-size: 18px;
        color: #5a5e66;
        vertical-align: text-bottom;

        &.hover-effect {
          cursor: pointer;
          transition: background .3s;

          &:hover {
            background: rgba(0, 0, 0, .025)
          }
        }
      }

      .avatar-container {
        margin-right: 30px;

        .avatar-wrapper {
          margin-top: 5px;
          position: relative;

          .user-avatar {
            cursor: pointer;
            width: 40px;
            height: 40px;
            border-radius: 10px;
          }

          .el-icon-caret-bottom {
            cursor: pointer;
            position: absolute;
            right: -20px;
            top: 25px;
            font-size: 12px;
          }
        }
      }
    }
	}
</style>
