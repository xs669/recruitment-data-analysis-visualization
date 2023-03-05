import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '@/styles/index.scss' // global css
import '@/icons' // icon
import {VePie, VeHistogram, VeBar, VeMap, VeRing, VeRadar, VeWordcloud, VeLine, VeFunnel, VeScatter, VeHeatmap} from "v-charts/lib/index.esm";

Vue.component(VePie.name, VePie); // 饼图
Vue.component(VeHistogram.name, VeHistogram); // 柱状图
Vue.component(VeBar.name, VeBar) //条形图
Vue.component(VeMap.name, VeMap) //中国地图
Vue.component(VeRing.name, VeRing) // 环形图
Vue.component(VeRadar.name, VeRadar) // 雷达图
Vue.component(VeWordcloud.name, VeWordcloud)
Vue.component(VeLine.name, VeLine) // 折线图
Vue.component(VeFunnel.name, VeFunnel)  // 漏斗图
Vue.component(VeScatter.name, VeScatter)  // 散点图
Vue.component(VeHeatmap.name, VeHeatmap)  // 热力图

Vue.use(ElementUI)

Vue.config.productionTip = false

new Vue({
	router,
	store,
	render: h => h(App)
}).$mount('#app')
