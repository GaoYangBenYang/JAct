import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'

import './assets/main.css'

const app = createApp(App)



// 创建一个Axios实例
// const AxiosInstance = axios.create({
//     baseURL: 'http://localhost:8080', // API请求的默认前缀
//     timeout: 5000, // 请求超时时间
//     headers: {
//       'Content-Type': 'application/json' // 默认请求头
//     }
//   })

// // 将Axios实例挂载到Vue原型上，方便在组件中使用
// createApp.prototype.$axios = AxiosInstance


app.use(router)

app.mount('#app')