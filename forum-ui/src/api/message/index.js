import request from '@/utils/request'

// 获取消息列表
export function getMessages(params) {
  return request({
    url: '/messages',
    method: 'get',
    params
  })
}

// 标记消息已读
export function readMessage(messageId) {
  return request({
    url: `/messages/${messageId}/read`,
    method: 'put'
  })
}

// 全部标记已读
export function readAllMessages() {
  return request({
    url: '/messages/read-all',
    method: 'put'
  })
}

// 获取未读消息数
export function getUnreadCount() {
  return request({
    url: '/messages/unread-count',
    method: 'get'
  })
}

// 使消息失效
export function invalidateMessage(messageId) {
  return request({
    url: `/messages/${messageId}/invalidate`,
    method: 'put'
  })
} 