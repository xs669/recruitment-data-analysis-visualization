import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/layout'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import getPageTitle from '@/utils/get-page-title'
import axios from "axios";

Vue.use(VueRouter)

const routes = [
	{
		path: '/login',
		component: () => import('@/views/login/index'),
		hidden: true,
		meta: {requireAuth: false}
	},
	{
		path: '/enroll',
		component: () => import('@/views/login/enroll'),
		hidden: true,
		meta: {requireAuth: false}
	},
	{
		path: '/404',
		component: () => import('@/views/404'),
		hidden: true,
		meta: {requireAuth: false}
	},
	{
		path: '/',
		component: Layout,
		redirect: '/overallAnalysis',
		name: '首页',
		meta: {title: '首页', icon: 'dashboard', requireAuth: true},
		children: [
			{
				path: '/overallAnalysis',
				name: 'overallAnalysis',
				component: () => import('@/views/dashboard/overallAnalysis'),
				meta: {title: '整体分析', icon: 'dashboard', requireAuth: true}
			},
			{
				path: '/dataOverview',
				name: 'dataOverview',
				component: () => import('@/views/dashboard/dataOverview'),
				meta: {title: '数据总览', icon: 'table', requireAuth: true}
			}
		]
	},
	{
		path: '/chart',
		component: Layout,
		redirect: '/chart/professionalSkillsAnalysis',
		name: 'Chart',
		meta: {title: '数据可视化', icon: 'el-icon-s-help', requireAuth: true},
		children: [
			{
				path: '/professionalSkillsAnalysis',
				name: 'professionalSkillsAnalysis',
				component: () => import('@/views/tree/professionalSkillsAnalysis'),
				meta: {title: '专业技能与岗位分析', icon: 'chart', requireAuth: true}
			},
			{
				path: '/personalSituationAndSalaryAnalysis',
				name: 'personalSituationAndSalaryAnalysis',
				component: () => import('@/views/tree/personalSituationAndSalaryAnalysis'),
				meta: {title: '个人情况与薪资分析', icon: 'chart', requireAuth: true}
			},
			{
				path: '/recruitmentRequirementsAnalysis',
				name: 'recruitmentRequirementsAnalysis',
				component: () => import('@/views/tree/recruitmentRequirementsAnalysis'),
				meta: {title: '招聘要求与岗位分析', icon: 'chart', requireAuth: true}
			},
			{
				path: '/companySituationAnalysis',
				name: 'companySituationAnalysis',
				component: () => import('@/views/tree/companySituationAnalysis'),
				meta: {title: '公司情况与岗位分析', icon: 'chart', requireAuth: true}
			},
			{
				path: '/companySituationAndSalaryAnalysis',
				name: 'companySituationAndSalaryAnalysis',
				component: () => import('@/views/tree/companySituationAndSalaryAnalysis'),
				meta: {title: '公司情况与薪资分析', icon: 'chart', requireAuth: true}
			},
			{
				path: '/numberOfJobsAndUrbanAnalysis',
				name: 'numberOfJobsAndUrbanAnalysis',
				component: () => import('@/views/tree/numberOfJobsAndUrbanAnalysis'),
				meta: {title: '岗位数量与城市分析', icon: 'chart', requireAuth: true}
			},
			{
				path: '/companyBenefitsAndJobAnalysis',
				name: 'companyBenefitsAndJobAnalysis',
				component: () => import('@/views/tree/companyBenefitsAndJobAnalysis'),
				meta: {title: '公司福利与岗位分析', icon: 'chart', requireAuth: true}
			},
		]
	},
	{
		path: '/predictionsAndRecommendations',
		component: Layout,
		hidden: true,
		redirect: '/predictionsAndRecommendations/recommend',
		name: 'PredictionsAndRecommendations',
		meta: {title: '预测和推荐', icon: 'yc', requireAuth: true},
		children: [
			{
				path: '/predictionsAndRecommendations/recommend',
				name: 'recommend',
				component: () => import('@/views/predictionsAndRecommendations/recommend'),
				meta: {title: '岗位推荐', icon: 'star', requireAuth: true}
			},
			{
				path: '/predictionsAndRecommendations/forecast',
				name: 'forecast',
				component: () => import('@/views/predictionsAndRecommendations/forecast'),
				meta: {title: '薪资预测', icon: 'yc', requireAuth: true}
			}
		]
	},
	{
		path: '/profile',
		component: Layout,
		redirect: '/profile/index',
		name: 'Profile',
		meta: {title: '个人中心', icon: 'user', requireAuth: true},
		children: [
			{
				path: '/profile/index',
				name: 'index',
				component: () => import('@/views/profile/index'),
				meta: {title: '个人信息', icon: 'user', requireAuth: true}
			},
			{
				path: '/profile/changeThePassword',
				name: 'changeThePassword',
				component: () => import('@/views/profile/changeThePassword'),
				meta: {title: '修改密码', icon: 'password', requireAuth: true}
			}
		]
	},

	// 404 page must be placed at the end !!!
	{path: '*', redirect: '/404', hidden: true}
]

const router = new VueRouter({
	mode: 'hash',
	base: process.env.BASE_URL,
	routes
})

NProgress.configure({ showSpinner: false })

const whiteList = ['/login']

router.beforeEach(async (to, from, next) => {
	NProgress.start()
	document.title = getPageTitle(to.meta.title)
	if (to.meta.requireAuth) {
		let token = localStorage.getItem("token")
		let username = localStorage.getItem("username")
		if (token) {
			await axios.get(`/apm/checkLoginState/${username}`).then(res => {
				if (res.data.data) {
					if (to.path === '/login') {
						next({ path: '/' })
						NProgress.done()
					} else {
						next()
					}
				} else {
					localStorage.removeItem("token")
					localStorage.removeItem("username")
					next({path: '/login'})
					NProgress.done()
				}
			})
		} else {
			if (whiteList.indexOf(to.path) !== -1) {
				next()
			} else {
				next({path: '/login'})
				NProgress.done()
			}
		}
	} else {
		next()
	}

})

router.afterEach(() => {
	NProgress.done()
})


export default router
