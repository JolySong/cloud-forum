<template>
  <div class="pagination">
    <div class="pagination-info">
      共 <span class="highlight">{{ total }}</span> 条
    </div>
    
    <div class="pagination-size">
      <select v-model="currentSize" @change="handleSizeChange">
        <option v-for="size in pageSizes" :key="size" :value="size">
          {{ size }}条/页
        </option>
      </select>
    </div>

    <div class="pagination-pages">
      <!-- 首页按钮 -->
      <button 
        class="page-btn nav-btn"
        :disabled="currentPage === 1"
        @click="handlePageChange(1)"
      >
        首页
      </button>

      <!-- 上一页按钮 -->
      <button 
        class="page-btn nav-btn"
        :disabled="currentPage === 1"
        @click="handlePageChange(currentPage - 1)"
      >
        <i class="arrow left"></i>
      </button>

      <!-- 页码按钮 -->
      <div class="page-numbers">
        <button
          v-for="page in displayPages"
          :key="page"
          class="page-btn number-btn"
          :class="{ active: page === currentPage }"
          @click="handlePageChange(page)"
        >
          {{ page }}
        </button>
      </div>

      <!-- 下一页按钮 -->
      <button 
        class="page-btn nav-btn"
        :disabled="currentPage === totalPages"
        @click="handlePageChange(currentPage + 1)"
      >
        <i class="arrow right"></i>
      </button>

      <!-- 末页按钮 -->
      <button 
        class="page-btn nav-btn"
        :disabled="currentPage === totalPages"
        @click="handlePageChange(totalPages)"
      >
        末页
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue'

const props = defineProps({
  total: {
    type: Number,
    default: 0
  },
  pageSize: {
    type: Number,
    default: 10
  },
  currentPage: {
    type: Number,
    default: 1
  },
  pageSizes: {
    type: Array,
    default: () => [5, 10, 20, 30, 50]
  }
})

const emit = defineEmits(['update:currentPage', 'update:pageSize', 'size-change', 'current-change'])

const currentSize = ref(props.pageSize)
const internalCurrentPage = ref(props.currentPage)

// 计算总页数
const totalPages = computed(() => {
  return Math.ceil(props.total / currentSize.value)
})

// 计算显示的页码
const displayPages = computed(() => {
  const pages = []
  const maxDisplayPages = 7 // 最多显示的页码数
  const halfDisplay = Math.floor(maxDisplayPages / 2)
  
  let start = Math.max(1, props.currentPage - halfDisplay)
  let end = Math.min(totalPages.value, start + maxDisplayPages - 1)
  
  if (end - start + 1 < maxDisplayPages) {
    start = Math.max(1, end - maxDisplayPages + 1)
  }
  
  for (let i = start; i <= end; i++) {
    pages.push(i)
  }
  
  return pages
})

// 监听外部变化
watch(() => props.currentPage, (val) => {
  internalCurrentPage.value = val
})

watch(() => props.pageSize, (val) => {
  currentSize.value = val
})

// 处理页码变化
const handlePageChange = (page) => {
  if (page === props.currentPage) return
  emit('update:currentPage', page)
  emit('current-change', page)
}

// 处理每页条数变化
const handleSizeChange = () => {
  emit('update:pageSize', currentSize.value)
  emit('size-change', currentSize.value)
  // 重置到第一页
  handlePageChange(1)
}
</script>

<style scoped>
.pagination {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 12px 16px;
  background: var(--box-bg);
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.pagination-info {
  color: var(--text-light);
  font-size: 14px;
  white-space: nowrap;

  .highlight {
    color: var(--primary-color);
    font-weight: 500;
    margin: 0 4px;
  }
}

.pagination-size {
  select {
    padding: 6px 12px;
    padding-right: 32px;
    border-radius: 6px;
    border: 1px solid var(--border-color);
    background: var(--bg-color);
    color: var(--text-color);
    font-size: 14px;
    cursor: pointer;
    transition: all 0.3s;
    appearance: none;
    background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3e%3cpolyline points='6 9 12 15 18 9'%3e%3c/polyline%3e%3c/svg%3e");
    background-repeat: no-repeat;
    background-position: right 8px center;
    background-size: 16px;

    &:hover {
      border-color: var(--primary-color);
    }

    &:focus {
      outline: none;
      border-color: var(--primary-color);
      box-shadow: 0 0 0 2px rgba(var(--primary-color-rgb), 0.1);
    }

    option {
      background: var(--box-bg);
      color: var(--text-color);
      padding: 8px;
    }
  }
}

.pagination-pages {
  display: flex;
  gap: 6px;
  align-items: center;
}

.page-numbers {
  display: flex;
  gap: 6px;
}

.page-btn {
  min-width: 36px;
  height: 36px;
  padding: 0 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid var(--border-color);
  border-radius: 6px;
  background: var(--bg-color);
  color: var(--text-color);
  font-size: 14px;
  cursor: pointer;
  transition: all 0.3s;
  user-select: none;

  &:hover:not(:disabled) {
    border-color: var(--primary-color);
    color: var(--primary-color);
    transform: translateY(-1px);
    box-shadow: 0 2px 8px rgba(var(--primary-color-rgb), 0.1);
  }

  &:disabled {
    cursor: not-allowed;
    opacity: 0.5;
    background: var(--button-hover-bg);
  }

  &.active {
    background: var(--primary-color);
    border-color: var(--primary-color);
    color: white;
    font-weight: 500;
    box-shadow: 0 2px 8px rgba(var(--primary-color-rgb), 0.2);
  }
}

.nav-btn {
  font-size: 13px;
  padding: 0 12px;
}

.number-btn {
  font-family: 'Monaco', monospace;
}

/* 箭头样式 */
.arrow {
  display: inline-block;
  width: 6px;
  height: 6px;
  border: solid currentColor;
  border-width: 0 2px 2px 0;
  
  &.left {
    transform: rotate(135deg);
  }
  
  &.right {
    transform: rotate(-45deg);
  }
}

/* 移动端适配 */
@media (max-width: 640px) {
  .pagination {
    flex-direction: column;
    gap: 12px;
    align-items: stretch;
  }

  .pagination-pages {
    justify-content: center;
    flex-wrap: wrap;
  }

  .nav-btn {
    display: none;
  }
}
</style> 