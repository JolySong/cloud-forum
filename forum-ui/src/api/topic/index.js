import request from '@/utils/request'

// 获取话题列表
export function getTopics(params) {
    return request({
      url: '/topic',
      method: 'get',
      params: {
        ...params,
        categoryId: params.categoryId || undefined
      }
    })
  }
  
  // 获取话题详情
  export function getTopicDetail(id) {
    return request({
      url: `/topic/${id}`,
      method: 'get'
    })
  }

  // 发布话题
export function createTopic(data) {
    return request({
      url: '/topic',
      method: 'post',
      data
    })
  }

// 删除话题
export function deleteTopic(id) {
    return request({
      url: `/topic/${id}`,
      method: 'delete'
    })
  }


// 更新话题
export function updateTopic(id, data) {
    return request({
      url: `/topic/${id}`,
      method: 'put',
      data
    })
  }


// 作者信息
export function getAuthorInfo(id) {
    return request({
      url: `/topic/author/${id}`,
      method: 'get'
    })
  }

// 获取话题列表
export function getAuthorTopics(params) {
  return request({
    url: '/topic/author',
    method: 'get',
    params: {
      ...params,
      userId: params.userId,
      limit: params.limit || 5,
      keyword: params.keyword || undefined
    }
  })
}

// 获取推荐话题
export function getRecommendTopics(params) {
    return request({
      url: '/topic/recommend',
      method: 'get',
      params
    })
  } 
  
  
  // 获取热门用户
  export function getActiveUsers(params) {
    return request({
      url: '/topic/activeUser',
      method: 'get',
      params
    })
  }
  
  // 点赞话题
export function likeTopic(id) {
    return request({
      url: `/topic/${id}/like`,
      method: 'post'
    })
  }
  
  // 取消点赞
  export function unlikeTopic(id) {
    return request({
      url: `/topic/${id}/like`,
      method: 'delete'
    })
  }
  
  // 收藏话题
  export function favoriteTopic(id) {
    return request({
      url: `/topic/${id}/collect`,
      method: 'post'
    })
  }
  
  // 取消收藏
  export function unfavoriteTopic(id) {
    return request({
      url: `/topic/${id}/collect`,
      method: 'delete'
    })
  }
  
  // 关注作者
  export function followAuthor(id) {
    return request({
      url: `/topic/follow/${id}`,
      method: 'post'
    })
  } 
  
  // 取消关注作者
  export function unfollowAuthor(id) {
    return request({
      url: `/topic/follow/${id}`,
      method: 'delete'
    })
  }