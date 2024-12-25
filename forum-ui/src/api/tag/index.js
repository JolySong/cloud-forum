import request from '@/utils/request'

// 获取标签列表
export function getTags(keyword) {
  return request({
    url: `/topic/tags`,
    method: 'get',
    params: { keyword: keyword || '' }
  })
}

