import request from '@/utils/request'

// 登录
export function login(data) {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

// 登出
export function logout() {
  return request({
    url: '/auth/logout',
    method: 'get'
  })
} 

// 检查登录状态
export function checkLoginStatus(username) {
  return request({
    url: '/auth/checkLoginStatus',
    method: 'get',
    params: { username }
  })
}

// 获取系统配置
export function getSysConfig() {
  return request({
    url: '/auth/config',
    method: 'get'
  })
}

// 检查用户名和邮箱是否一致
export function checkUsernameAndEmail(data) {
  return request({
    url: '/auth/checkUsernameAndEmail',
    method: 'post',
    data
  })
}

// 发送验证码邮件
export function sendResetPasswordEmail(data) {
  return request({
    url: '/auth/sendCode',
    method: 'post',
    data
  })
}

// 重置密码
export function resetPassword(data) {
  return request({
    url: '/auth/resetPassword',
    method: 'post',
    data
  })
}