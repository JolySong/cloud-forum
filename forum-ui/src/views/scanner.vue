// src/views/system/resource/scanner.vue
<template>
  <div class="scanner-container">
    <div class="scanner-header">
      <h2>资源扫描</h2>
      <div class="header-actions">
        <el-button type="primary" @click="handleScan">
          <el-icon><Refresh /></el-icon>
          重新扫描
        </el-button>
        <el-button type="success" @click="handleExport">
          <el-icon><Download /></el-icon>
          导出JSON
        </el-button>
      </div>
    </div>

    <div class="scanner-content">
      <el-tabs v-model="activeTab">
        <el-tab-pane label="资源树" name="tree">
          <el-tree
            :data="resourceTree"
            :props="defaultProps"
            node-key="id"
            default-expand-all
          >
            <template #default="{ node, data }">
              <div class="custom-tree-node">
                <span :class="['resource-type', `type-${data.resourceType}`]">
                  {{ getResourceTypeLabel(data.resourceType) }}
                </span>
                <span class="resource-name">{{ data.resourceName }}</span>
                <span class="resource-key" v-if="data.resourceKey">
                  {{ data.resourceKey }}
                </span>
                <span class="resource-url" v-if="data.resourceUrl || data.apiUrl">
                  {{ data.resourceUrl || data.apiUrl }}
                </span>
                <span class="resource-method" v-if="data.method">
                  {{ data.method }}
                </span>
              </div>
            </template>
          </el-tree>
        </el-tab-pane>
        
        <el-tab-pane label="JSON预览" name="json">
          <div class="json-preview">
            <pre>{{ JSON.stringify(resourceData, null, 2) }}</pre>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { Download, Refresh } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import router from '@/router'
import * as apiModules from '@/api'

const activeTab = ref('tree')
const resourceTree = ref([])
const resourceData = ref({})

const defaultProps = {
  children: 'children',
  label: 'resourceName'
}

// 资源类型映射
const resourceTypes = {
  M: '目录',
  C: '菜单',
  F: '按钮',
  A: '接口'
}

// 获取资源类型标签
const getResourceTypeLabel = (type) => {
  return resourceTypes[type] || type
}

// 扫描路由生成菜单资源
const scanRoutes = (routes, parentId = null) => {
  const resources = []
  
  routes.forEach((route, index) => {
    if (route.path && !route.path.includes('*')) {
      const resource = {
        id: parentId ? `${parentId}-${index}` : String(index),
        resourceName: route.name || route.path,
        resourceType: parentId ? 'C' : 'M',
        resourceKey: route.name ? route.name.toLowerCase() : '',
        resourceUrl: route.path,
        component: route.component?.name,
        sort: index,
        children: []
      }

      if (route.children?.length) {
        resource.children = scanRoutes(route.children, resource.id)
      }

      resources.push(resource)
    }
  })

  return resources
}

// 扫描API模块生成接口资源
const scanApis = () => {
  const resources = []
  let id = 1000 // API资源起始ID

  Object.entries(apiModules).forEach(([moduleName, module]) => {
    Object.entries(module).forEach(([funcName, func]) => {
      const comments = func.toString().match(/\/\*\*([\s\S]*?)\*\//)
      const description = comments ? comments[1].replace(/\*/g, '').trim() : funcName

      // 从函数定义中提取URL和方法
      const funcStr = func.toString()
      const urlMatch = funcStr.match(/url: ['"]([^'"]+)['"]/)
      const methodMatch = funcStr.match(/method: ['"]([^'"]+)['"]/)

      if (urlMatch && methodMatch) {
        resources.push({
          id: String(id++),
          resourceName: description,
          resourceType: 'A',
          resourceKey: `${moduleName}:${funcName}`.toLowerCase(),
          apiUrl: urlMatch[1],
          method: methodMatch[1],
          sort: id
        })
      }
    })
  })

  return resources
}

// 扫描项目资源
const scanResources = () => {
  try {
    // 扫描路由
    const menuResources = scanRoutes(router.options.routes)
    
    // 扫描API
    const apiResources = scanApis()
    
    // 合并资源
    resourceTree.value = [
      ...menuResources,
      {
        id: 'api',
        resourceName: 'API接口',
        resourceType: 'M',
        resourceKey: 'api',
        sort: 999,
        children: apiResources
      }
    ]

    // 生成JSON数据
    resourceData.value = {
      version: '1.0',
      timestamp: new Date().toISOString(),
      total: countResources(resourceTree.value),
      resources: resourceTree.value
    }

    ElMessage.success('扫描完成')
  } catch (error) {
    console.error('扫描失败:', error)
    ElMessage.error('扫描失败')
  }
}

// 计算资源总数
const countResources = (resources) => {
  let count = resources.length
  resources.forEach(resource => {
    if (resource.children?.length) {
      count += countResources(resource.children)
    }
  })
  return count
}

// 导出JSON文件
const handleExport = () => {
  try {
    const jsonStr = JSON.stringify(resourceData.value, null, 2)
    const blob = new Blob([jsonStr], { type: 'application/json' })
    const link = document.createElement('a')
    link.href = URL.createObjectURL(blob)
    link.download = `resources-${new Date().getTime()}.json`
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
    ElMessage.success('导出成功')
  } catch (error) {
    console.error('导出失败:', error)
    ElMessage.error('导出失败')
  }
}

// 重新扫描
const handleScan = () => {
  scanResources()
}

onMounted(() => {
  scanResources()
})
</script>

<style scoped>
.scanner-container {
  padding: 20px;
  
  .scanner-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    
    h2 {
      margin: 0;
      color: var(--text-color);
    }

    .header-actions {
      display: flex;
      gap: 12px;
    }
  }
}

.custom-tree-node {
  display: flex;
  align-items: center;
  gap: 8px;
  flex-wrap: wrap;
}

.resource-type {
  display: inline-block;
  padding: 2px 6px;
  border-radius: 4px;
  font-size: 12px;
  
  &.type-M {
    background: #e6f7ff;
    color: #1890ff;
  }
  
  &.type-C {
    background: #f6ffed;
    color: #52c41a;
  }
  
  &.type-F {
    background: #fff7e6;
    color: #fa8c16;
  }
  
  &.type-A {
    background: #f9f0ff;
    color: #722ed1;
  }
}

.resource-name {
  font-weight: 500;
  color: var(--text-color);
}

.resource-key {
  color: var(--text-light);
  font-size: 13px;
}

.resource-url {
  color: var(--primary-color);
  font-size: 13px;
}

.resource-method {
  background: var(--bg-color);
  padding: 2px 6px;
  border-radius: 4px;
  font-size: 12px;
  color: var(--text-light);
}

.json-preview {
  background: var(--bg-color);
  padding: 16px;
  border-radius: 8px;
  
  pre {
    margin: 0;
    color: var(--text-color);
    font-family: monospace;
    white-space: pre-wrap;
    word-wrap: break-word;
  }
}

:deep(.el-tabs__item) {
  color: var(--text-color);
}

:deep(.el-tree) {
  background: transparent;
  color: var(--text-color);
}

:deep(.el-tree-node__content:hover) {
  background: var(--button-hover-bg);
}
</style>