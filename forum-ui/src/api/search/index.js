import request from '@/utils/request'

// 搜索话题
export function searchTopics(params) {
  return request({
    url: '/search/topics',
    method: 'get',
    params
  })
} 