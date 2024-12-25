import request from '@/utils/request'


// 获取用户发布的话题
export function getUserTopics(params) {
    return request({
      url: '/user/topics',
      method: 'get',
      params: {
        ...params,
        keyword: params.keyword || undefined
      }
    })
  }
  
  // 获取用户收藏的话题
  export function getUserFavorites(params) {
    return request({
      url: '/user/collects',
      method: 'get',
      params: {
        ...params,
        keyword: params.keyword || undefined
      }
    })
  }
  
  // 获取用户的评论
  export function getUserComments(params) {
    return request({
      url: '/user/comments',
      method: 'get',
      params: {
        ...params,
        keyword: params.keyword || undefined
      }
    })
  }
  
  // 获取用户点赞的话题
  export function getUserLikes(params) {
    return request({
      url: '/user/likes',
      method: 'get',
      params: {
        ...params,
        keyword: params.keyword || undefined
      }
    })
  }
  