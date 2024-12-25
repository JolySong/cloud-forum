import request from '@/utils/request'

// 获取用户信息
export function getUserInfo() {
  return request({
    url: '/user/info',
    method: 'get'
  })
}

// 获取用户统计信息
export function getUserStat(userId) {
  return request({
    url: `/user/stat/${userId}`,
    method: 'get'
  })
}

