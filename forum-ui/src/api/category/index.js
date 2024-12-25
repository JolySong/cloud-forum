import request from '@/utils/request'

// 获取分类列表
export function getCategories() {
  return request({
    url: '/topic/categories',
    method: 'get'
  })
}

// 获取分类详情
export function getCategory(id) {
  return request({
    url: `/topic/categories/${id}`,
    method: 'get'
  })
}

// 创建分类
export function createCategory(data) {
  return request({
    url: '/topic/categories',
    method: 'post',
    data
  })
}

// 更新分类
export function updateCategory(data) {
  return request({
    url: `/topic/categories`,
    method: 'put',
    data
  })
}

// 删除分类
export function deleteCategory(id) {
  return request({
    url: `/topic/categories/${id}`,
    method: 'delete'
  })
}

// 排序分类
export function sortCategory(data) {
  return request({
    url: `/topic/categories/sort`,
    method: 'post',
    data
  })
}
