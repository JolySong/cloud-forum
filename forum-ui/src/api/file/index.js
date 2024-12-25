import request from '@/utils/request'

// 上传文件
export function uploadFile(data) {
  return request({
    url: '/file/upload',
    method: 'post',
    data
  })
}

// 获取文件
export function getFileUrl(path) {
  return request({
    url: '/file/getUrl',
    method: 'get',
    params: {
      fileName: path
    }
  })
}