const baseUrl = import.meta.env.VITE_IMAGE_URL

import { getFileUrl } from '@/api'



// 获取图片 URL
export async function getImageUrl(path) {
  try {
    if (!path) return ''
    
    // 如果是完整的 URL，直接返回
    if (path.startsWith('http://') || path.startsWith('https://')) {
      return path
    }
    
    const response = await getFileUrl(path)
    const imageUrl = baseUrl + response.data
    return imageUrl
    
  } catch (error) {
    console.error('获取图片失败:', error)
    return ''
  }
}

// 图片预加载
export function preloadImage(path) {
  return new Promise((resolve, reject) => {
    const img = new Image()
    img.onload = () => resolve(img)
    img.onerror = reject
    getImageUrl(path).then(url => {
      img.src = url
    }).catch(reject)
  })
}

// 检查文件类型是否为图片
export function isImageFile(file) {
  return file.type.startsWith('image/')
}

// 压缩图片
export function compressImage(file, options = {}) {
  const { maxWidth = 1920, maxHeight = 1080, quality = 0.8 } = options
  
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.onload = (e) => {
      const img = new Image()
      img.onload = () => {
        const canvas = document.createElement('canvas')
        let width = img.width
        let height = img.height
        
        // 等比例缩放
        if (width > maxWidth) {
          height = (maxWidth * height) / width
          width = maxWidth
        }
        if (height > maxHeight) {
          width = (maxHeight * width) / height
          height = maxHeight
        }
        
        canvas.width = width
        canvas.height = height
        
        const ctx = canvas.getContext('2d')
        ctx.drawImage(img, 0, 0, width, height)
        
        canvas.toBlob((blob) => {
          resolve(new File([blob], file.name, { type: file.type }))
        }, file.type, quality)
      }
      img.onerror = reject
      img.src = e.target.result
    }
    reader.onerror = reject
    reader.readAsDataURL(file)
  })
} 