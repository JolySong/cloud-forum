import request from '@/utils/request'

// 获取轮播图列表
export function getBanners() {
  return request({
    url: '/banner/list',
    method: 'get'
  })
} 