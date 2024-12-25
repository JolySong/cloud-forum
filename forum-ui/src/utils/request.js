import axios from 'axios'
import { ElMessage } from 'element-plus'
import { getToken, removeToken } from '@/utils/storage'

// 创建 axios 实例
const request = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 6000
})

// 用于存储错误消息的 Set
const errorMessageSet = new Set()

// 清除错误消息的函数
const clearErrorMessage = (message) => {
  setTimeout(() => {
    errorMessageSet.delete(message)
  }, 3000) // 3秒后清除错误消息
}

// 显示错误消息
const showErrorMessage = (message) => {
  if (!errorMessageSet.has(message)) {
    errorMessageSet.add(message)
    ElMessage.error(message)
    clearErrorMessage(message)
  }
}

// 请求拦截器
request.interceptors.request.use(
  config => {
    const token = getToken()
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {    
    const res = response.data
    if (res.status) {
      return res
    }

    showErrorMessage(res.message)
    return Promise.reject(new Error(res.message))
  },
  error => {
    if (error.response?.data.code === 402) {
      removeToken()
      showErrorMessage('你的帐号已失效或已在其它设备登陆')
      setTimeout(() => {
        window.location.reload();
      }, 2000); 
      return Promise.reject(new Error('你的帐号已失效或已在其它设备登陆'))
    }

    const errorMessage = error.response?.data?.message || error.message || '请求失败'
    showErrorMessage(errorMessage)
    return Promise.reject(error)
  }
)

export default request 