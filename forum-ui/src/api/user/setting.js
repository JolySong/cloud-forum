import request from '@/utils/request'

// 更新用户信息
export function updateUserInfo(data) {
  return request({
    url: '/user/info',
    method: 'put',
    data
  })
} 
  
// 修改密码
export function updateUserPassword(data) {
  return request({
    url: '/user/update/password',
    method: 'put',
    data
  })
}

// 头像上传
export function uploadAvatar(data) {
  return request({
    url: '/user/avatar',
    method: 'post',
    data
  })
}