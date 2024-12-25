import request from '@/utils/request'

// 获取热门分类
export function getHotCategories(params) {
    return request({
      url: '/topic/hot/categories',
      method: 'get',
      params
    })
} 

// 获取热门标签
export function getHotTags() {
    return request({
      url: '/topic/hot/tags',
      method: 'get'
    })
}

// 获取热门话题
export function getHotTopics(params) {
    return request({
      url: '/topic/hot/topics',
      method: 'get',
      params
    })
}


// 获取热门评论
export function getHotComments(params) {
    return request({
      url: '/topic/hot/comments',
      method: 'get',
      params
    })
} 

// 获取统计数据
export function getForumStats() {
  return request({
    url: '/analysis/siteCount',
    method: 'get'
  })
}