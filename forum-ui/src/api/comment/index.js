import request from '@/utils/request'

// 获取话题评论列表
export function getTopicComments(topicId, params) {
  return request({
    url: `/topic/comments/${topicId}`,
    method: 'get',
    params
  })
}

// 发表评论
export function createComment(data) {
  return request({
    url: '/topic/comments',
    method: 'post',
    data
  })
}

// 点赞评论
export function likeComment(id) {
  return request({
    url: `/topic/comments/${id}/like`,
    method: 'post'
  })
}

// 取消点赞评论
export function unlikeComment(id) {
  return request({
    url: `/topic/comments/${id}/like`,
    method: 'delete'
  })
}

// 回复评论
export function replyComment(id, data) {
  return request({
    url: `/topic/comments/${id}/reply`,
    method: 'post',
    data
  })
}

// 删除评论
export function deleteComment(id) {
  return request({
    url: `/topic/comments/${id}`,
    method: 'delete'
  })
}

// 置顶评论
export function topComment(id) {
  return request({
    url: `/topic/comments/${id}/top`,
    method: 'post'
  })
}

// 取消置顶
export function untopComment(id) {
  return request({
    url: `/topic/comments/${id}/top`,
    method: 'delete'
  })
}
