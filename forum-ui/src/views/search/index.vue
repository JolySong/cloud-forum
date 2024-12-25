<template>
  <div class="search-container">
    <!-- 搜索头部 -->
    <div class="search-header">
      <div class="search-input-wrapper">
        <el-input
          v-model="searchText"
          placeholder="搜索..."
          class="search-input"
          @keyup.enter="handleSearch"
        >
          <template #prefix>
            <el-icon class="search-icon"><Search /></el-icon>
          </template>
        </el-input>
      </div>
      <div class="search-info" v-if="keyword">
        找到 {{ total }} 个与 "{{ keyword }}" 相关的结果
      </div>
    </div>

    <!-- 搜索结果 -->
    <div class="search-results" v-loading="loading">
      <!-- 话题列表 -->
      <div v-if="results.length > 0" class="result-list">
        <div v-for="topic in results" :key="topic.id" class="result-item">
          <!-- 话题信息 -->
          <router-link :to="`/topic/${topic.id}`" class="topic-link">
            <h3 class="topic-title" v-html="highlightKeyword(topic.title)"></h3>
          </router-link>
          
          <!-- 话题摘要 -->
          <p class="topic-summary" v-html="highlightKeyword(topic.summary || '')"></p>
          
          <!-- 话题元信息 -->
          <div class="topic-meta">
            <div class="meta-left">
              <el-avatar :size="24" :src="topic.author?.avatar" />
              <span class="author-name">{{ topic.author?.nickname }}</span>
              <span class="publish-time">{{ topic.createdAt }}</span>
            </div>
            <div class="meta-right">
              <span><el-icon><View /></el-icon>{{ topic.viewCount }}</span>
              <span><el-icon><ChatRound /></el-icon>{{ topic.commentCount }}</span>
              <span><el-icon><Star /></el-icon>{{ topic.likeCount }}</span>
            </div>
          </div>

          <!-- 标签 -->
          <div class="topic-tags">
            <el-tag 
              v-if="topic.category"
              size="small"
              class="category-tag"
              @click.stop="handleCategoryClick(topic.category.id)"
            >
              {{ topic.category.name }}
            </el-tag>
            <el-tag
              v-for="tag in topic.tags"
              :key="tag.id"
              size="small"
              :style="{ 
                backgroundColor: tag.bgColor || 'var(--button-hover-bg)',
                color: tag.color || 'var(--text-light)',
                borderColor: 'transparent'
              }"
              class="topic-tag"
              @click.stop="handleTagClick(tag.id)"
            >
              {{ tag.name }}
            </el-tag>
          </div>
        </div>
      </div>
      
      <!-- 空状态 -->
      <el-empty 
        v-else 
        :description="loading ? '' : (keyword ? '暂无搜索结果' : '请输入搜索关键词')"
      />

      <!-- 分页 -->
      <div v-if="total > pageSize" class="pagination">
        <Pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :total="total"
          :page-sizes="[10, 20, 50]"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import { Search, View, ChatRound, Star } from '@element-plus/icons-vue'
import { searchTopics } from '@/api'
import Pagination from '@/components/common/Pagination.vue'

const router = useRouter()

// 搜索相关
const searchText = ref('')
const keyword = ref('')
const loading = ref(false)
const results = ref([])
const total = ref(0)

// 分页参数
const currentPage = ref(1)
const pageSize = ref(10)

// 监听路由变化
watch(

)

// 搜索处理
const handleSearch = () => {
  const trimmedText = searchText.value?.trim()
  if (!trimmedText) return
  
  fetchResults()
}

// 获取搜索结果
const fetchResults = async () => {
  if (!keyword.value) return
  
  loading.value = true
  try {
    const res = await searchTopics({
      keyword: keyword.value,
      page: currentPage.value,
      size: pageSize.value
    })
    results.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    console.error('搜索失败:', error)
  } finally {
    loading.value = false
  }
}

// 高亮关键词
const highlightKeyword = (text) => {
  if (!text || !keyword.value) return text
  const reg = new RegExp(keyword.value, 'gi')
  return text.replace(reg, match => `<span class="highlight">${match}</span>`)
}

// 分页处理
const handleSizeChange = (val) => {
  pageSize.value = val
  currentPage.value = 1
  fetchResults()
}

const handleCurrentChange = (val) => {
  currentPage.value = val
  fetchResults()
}

// 标签点击
const handleTagClick = (tagId) => {
  router.push({
    path: '/topic',
    query: { tagId }
  })
}

// 分类点击
const handleCategoryClick = (categoryId) => {
  router.push({
    path: '/topic',
    query: { categoryId }
  })
}
</script>

<style scoped>
.search-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.search-header {
  margin-bottom: 24px;
}

.search-input-wrapper {
  max-width: 600px;
  margin-bottom: 16px;
}

:deep(.search-input) {
  .el-input__wrapper {
    background-color: var(--bg-color);
    box-shadow: 0 0 0 1px var(--border-color) inset;
    border-radius: 20px;
    padding-left: 12px;
    transition: all 0.3s ease;

    &:hover {
      box-shadow: 0 0 0 1px var(--primary-color) inset;
    }

    &.is-focus {
      box-shadow: 0 0 0 1px var(--primary-color) inset !important;
      background-color: var(--box-bg);
    }
  }

  .el-input__inner {
    color: var(--text-color);
    height: 40px;
    font-size: 16px;

    &::placeholder {
      color: var(--text-light);
    }
  }

  .search-icon {
    font-size: 18px;
    color: var(--text-light);
    margin-right: 4px;
  }
}

.search-info {
  color: var(--text-light);
  font-size: 14px;
}

.result-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.result-item {
  background: var(--box-bg);
  border-radius: 8px;
  padding: 20px;
  transition: all 0.3s;
}

.result-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.topic-link {
  text-decoration: none;
}

.topic-title {
  color: var(--text-color);
  font-size: 18px;
  margin: 0 0 12px;
  line-height: 1.4;

  &:hover {
    color: var(--primary-color);
  }
}

.topic-summary {
  color: var(--text-color);
  font-size: 14px;
  line-height: 1.6;
  margin-bottom: 16px;
}

.topic-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.meta-left {
  display: flex;
  align-items: center;
  gap: 8px;
}

.author-name {
  color: var(--text-color);
  font-size: 14px;
}

.publish-time {
  color: var(--text-light);
  font-size: 12px;
}

.meta-right {
  display: flex;
  gap: 16px;
  color: var(--text-light);
  font-size: 14px;
}

.meta-right span {
  display: flex;
  align-items: center;
  gap: 4px;
}

.topic-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.category-tag,
.topic-tag {
  cursor: pointer;
  transition: all 0.3s;
}

.category-tag:hover,
.topic-tag:hover {
  transform: scale(1.05);
  opacity: 0.9;
}

.highlight {
  color: var(--primary-color);
  font-weight: 500;
}

.pagination {
  margin-top: 24px;
  display: flex;
  justify-content: center;
}
</style> 