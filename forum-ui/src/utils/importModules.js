// src/utils/importModules.js
const modules = import.meta.glob('/src/api/**/*.js', { eager: true })

export function importApiModules() {
  const apiModules = {}
  
  for (const path in modules) {
    // 解析模块路径，例如: /src/api/user/info.js -> user/info
    const modulePath = path
      .replace('/src/api/', '')
      .replace('.js', '')
    
    // 解析模块名称，例如: user/info -> user:info
    const moduleName = modulePath.replace('/', ':')
    
    // 获取模块内容
    const moduleContent = modules[path]
    
    apiModules[moduleName] = {
      path: modulePath,
      name: getModuleName(modulePath),
      exports: moduleContent
    }
  }
  
  return apiModules
}

// 根据路径生成模块名称
function getModuleName(path) {
  const parts = path.split('/')
  return parts
    .map(part => part.charAt(0).toUpperCase() + part.slice(1))
    .join('/')
}