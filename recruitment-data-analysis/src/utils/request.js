import axios from 'axios'
import {Message} from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

const request = axios.create({
	baseURL: '/apj',
	timeout: 5000
})

// request interceptor
request.interceptors.request.use(config => {
		NProgress.start()
		const token = window.localStorage.getItem("token");
		if (token) {
			config.headers.Authorization = token;
		}
		return config
	},
	error => {
		console.log(error)
		return Promise.reject(error)
	}
)

// 响应拦截
request.interceptors.response.use(
	(response) => {
		const res = response.data;
		if (res.code !== 200) {
			if (res.code === 500 && res.data === null) {
				console.log("暂无数据");
			} else {
				let msg = res.msg || "Error";
				Message.error(msg);
				return Promise.reject(new Error(msg));
			}
		}
		return res;
	},
	(error) => {
		console.info(error);
		Message.error(error.message);
		return Promise.reject(error);
	}
);


export default request
