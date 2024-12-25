<template>
  <div class="topic-container">
    <!-- Banner轮播图 -->
    <div v-if="banners.length > 0" class="banner-section">
      <el-carousel height="300px" :interval="5000" arrow="hover">
        <el-carousel-item v-for="banner in banners" :key="banner.id">
          <div class="banner-item" :style="{ backgroundImage: `url(${banner.imageUrl})` }">
            <div class="banner-content">
              <h2 class="banner-title">{{ banner.title }}</h2>
              <p class="banner-desc">{{ banner.description }}</p>
            </div>
          </div>
        </el-carousel-item>
      </el-carousel>
    </div>

    <div class="topic-page-layout">
      <!-- 左侧主内容区域 -->
      <div class="topic-main-content">
        <!-- 话题列表 -->
        <div class="topic-section">
          <!-- 分类筛选 -->
          <div class="category-filter">
            <div class="category-list">
              <div 
                class="action-item"
                :class="{ active: filter.categoryId === null }"
                @click="handleCategoryClick(null)"
              >
                全部
              </div>
              <div class="divider"></div>
              <div 
                v-for="category in categories" 
                :key="category.id"
                class="action-item"
                :class="{ active: filter.categoryId === category.id }"
                @click="handleCategoryClick(category.id)"
              >
                {{ category.name }}
              </div>
            </div>
          </div>

          <!-- 筛选栏 -->
          <div class="filter-bar">
            <div class="filter-left">
              <div class="filter-tabs">
                <div 
                  v-for="type in typeOptions" 
                  :key="type.value"
                  class="action-item"
                  :class="{ active: filter.type === type.value }"
                  @click="handleTypeClick(type.value)"
                >
                  {{ type.label }}
                </div>
              </div>
            </div>
            <div class="filter-right">
              <div class="filter-sort">
                <div 
                  v-for="sort in sortOptions" 
                  :key="sort.value"
                  class="action-item"
                  :class="{ active: filter.sort === sort.value }"
                  @click="handleSortClick(sort.value)"
                >
                  {{ sort.label }}
                </div>
              </div>
              <!-- 添加发布按钮 -->
              <el-button 
                @click="handlePublish"
                type="primary"
                class="publish-btn"
              >
                <el-icon><Plus /></el-icon>
                发布话题
              </el-button>
            </div>
          </div>

          <!-- 话题列表 -->
          <div class="topic-list" v-loading="loading.main">
            <div v-for="topic in topics" :key="topic.id" class="topic-item">
              <div class="topic-main">
                <!-- 作者信息和状态标签行 -->
                <div class="topic-header">
                  <div class="topic-user">
                    <el-avatar :size="40" :src="topic.author.avatar" />
                    <div class="user-info">
                      <span class="username">{{ topic.author.nickname }}</span>
                      <span class="publish-time">{{ topic.createdAt }}</span>
                    </div>
                  </div>
                  <!-- 状态标签 -->
                  <div class="topic-status">
                    <el-tag 
                      v-if="topic.isTop" 
                      type="danger" 
                      size="small" 
                      effect="dark"
                      class="status-badge"
                    >
                      置顶
                    </el-tag>
                    <el-tag 
                      v-if="topic.isFeatured" 
                      type="success" 
                      size="small" 
                      effect="dark"
                      class="status-badge"
                    >
                      精选
                    </el-tag>
                  </div>
                </div>

                <!-- 话题内容 -->
                <div 
                  class="topic-content"
                  @click="handleTopicClick(topic)"
                >
                  <div class="title-section">
                    <h3 class="topic-title">{{ topic.title }}</h3>
                    <div class="topic-category">
                      <el-tag 
                        v-if="topic.category" 
                        size="small"
                        class="category-tag"
                        @click.stop="handleCategoryClick(topic.category.id)"
                      >
                        {{ topic.category.name }}
                      </el-tag>
                    </div>
                  </div>
                  <div class="topic-preview" v-html="formatContent(topic.content)"></div>
                  <p class="topic-summary">{{ topic.summary }}</p>
                  <div class="topic-cover" v-if="topic.coverUrl">
                    <img :src="topic.coverUrl" :alt="topic.title">
                  </div>
                </div>
              </div>

              <div class="topic-footer">
                <div class="topic-info">
                  <!-- 话题标签 -->
                  <div class="topic-tags">
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
                  <!-- 统计信息 -->
                  <div class="topic-stats">
                    <span><el-icon><View /></el-icon>{{ topic.viewCount }}</span>
                    <span><el-icon><ChatRound /></el-icon>{{ topic.commentCount }}</span>
                    <span>
                      <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24"><path fill="currentColor" fill-opacity="0" d="M7 11v9h-4v-9h4Z"><animate fill="freeze" attributeName="fill-opacity" begin="0.6s" dur="0.5s" values="0;1"/></path><path fill="none" stroke="currentColor" stroke-dasharray="80" stroke-dashoffset="80" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 11l5 -8l3 1l-1 6h7v3l-3 7h-11h-4v-9h4v9"><animate fill="freeze" attributeName="stroke-dashoffset" dur="0.6s" values="80;0"/></path></svg>
                      {{ topic.likeCount }}
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 分页 -->
          <div class="pagination">
            <Pagination
              v-model:current-page="page.current"
              v-model:page-size="page.size"
              :total="page.total"
              :page-sizes="[5, 10, 20, 30, 50]"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            />
          </div>
        </div>
      </div>

      <!-- 右侧边栏 -->
      <div class="topic-sidebar">
        <!-- 推荐话题 -->
        <div class="sidebar-section">
          <div class="section-header">
            <h3>推荐话题</h3>
            <router-link to="/topic/recommend" class="more-link">更多</router-link>
          </div>
          <div class="recommend-topics" v-loading="loading.recommend">
            <router-link 
              v-for="topic in recommendTopics" 
              :key="topic.id"
              :to="`/topic/${topic.id}`"
              class="recommend-topic-item"
            >
              <div class="recommend-topic-title">{{ topic.title }}</div>
              <div class="recommend-topic-meta">
                <span>{{ topic.viewCount }}浏览</span>
                <span>{{ topic.commentCount }}评论</span>
              </div>
            </router-link>
          </div>
        </div>

        <!-- 活跃用户 -->
        <div class="sidebar-section">
          <div class="section-header">
            <h3>活跃用户</h3>
            <router-link to="/users/active" class="more-link">更多</router-link>
          </div>
          <div class="active-users" v-loading="loading.activeUsers">
            <router-link 
              v-for="user in activeUsers" 
              :key="user.id"
              :to="`/user/${user.id}`"
              class="active-user-item"
            >
              <el-avatar :size="40" :src="user.avatar" />
              <div class="user-info">
                <div class="username">{{ user.nickname }}</div>
                <div class="user-stats">
                  <span>话题 {{ user.topicCount }}</span>
                  <span>评论 {{ user.commentCount }}</span>
                </div>
              </div>
            </router-link>
          </div>
        </div>
      </div>
    </div>

    <back-to-top />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { View, ChatRound, Plus } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/store/modules/user'
import { getTopics, getRecommendTopics, getActiveUsers } from '@/api'
import { getBanners } from '@/api'
import { getCategories } from '@/api'
import Pagination from '@/components/common/Pagination.vue'
import BackToTop from '@/components/common/BackToTop.vue'
import { getImageUrl } from '@/utils/image'

const router = useRouter()
const userStore = useUserStore()

// 轮播图数据
const banners = ref([])
const loading = ref({
  main: false,
  recommend: false,
  activeUsers: false
})

// 话题列表数据
const topics = ref([])

// 分类数据
const categories = ref([])

// 筛选条件
const filter = ref({
  type: 1,
  sort: 0,
  categoryId: null
})

// 分页参数
const page = ref({
  current: 1,
  size: 10,
  total: 0
})

// 类型选项
const typeOptions = [
  { label: '热门', value: 1 },
  { label: '最新', value: 2 }
]

// 排序选项
const sortOptions = [
  { label: '最新发布', value: 0 },
  { label: '最多浏览', value: 1 },
  { label: '最多评论', value: 2 }
]

// 推荐话题数据
const recommendTopics = ref([])
// 活跃用户数据
const activeUsers = ref([])

// 获取轮播图数据
const fetchBanners = async () => {
  try {
    const res = await getBanners()
    if (Array.isArray(res.data) && res.data.length > 0) {
      banners.value = res.data
    }
  } catch (error) {
    banners.value = []
  }
}

// 获取话题列表
const fetchTopics = async () => {
  loading.value.main = true

  try {
    const params = {
      page: page.value.current,
      size: page.value.size,
      type: filter.value.type,
      sort: filter.value.sort,
      categoryId: filter.value.categoryId
    }
    const res = await getTopics(params)
    topics.value = res.data.records
    page.value.total = res.data.total
    topics.value.forEach(async (topic) => {
      topic.author.avatar = await getImageUrl(topic.author.avatar)
    })
  } catch (error) {
    console.error('获取话题列表失败:', error)
  } finally {
    loading.value.main = false
  }
}

// 获取分类列表
const fetchCategories = async () => {
  try {
    const res = await getCategories()
    categories.value = res.data
  } catch (error) {
    console.error('获取分类列表失败:', error)
  }
}

// 获取推荐话题
const fetchRecommendTopics = async () => {
  loading.value.recommend = true
  try {
    const res = await getRecommendTopics({ limit: 5 })
    recommendTopics.value = res.data
  } catch (error) {
    console.error('获取推荐话题失败:', error)
  } finally {
    loading.value.recommend = false
  }
}

// 获取活跃用户
const fetchActiveUsers = async () => {
  loading.value.activeUsers = true
  try {
    const res = await getActiveUsers({ limit: 5 })
    activeUsers.value = res.data
    activeUsers.value.forEach(async (user) => {
      user.avatar = await getImageUrl(user.avatar)
    })
  } catch (error) {
    console.error('获取活跃用户失败:', error)
  } finally {
    loading.value.activeUsers = false
  }
}

// 筛选变
const handleFilterChange = () => {
  page.value.current = 1
  fetchTopics()
}

// 页码变化
const handleCurrentChange = (val) => {
  page.value.current = val
  fetchTopics()
}

// 每页条数变化
const handleSizeChange = (val) => {
  page.value.size = val
  page.value.current = 1
  fetchTopics()
}

// 点击分类
const handleCategoryClick = (categoryId) => {
  filter.value.categoryId = categoryId
  handleFilterChange()
}

// 点击类型
const handleTypeClick = (type) => {
  filter.value.type = type
  handleFilterChange()
}

// 点击排序
const handleSortClick = (sort) => {
  filter.value.sort = sort
  handleFilterChange()
}

// 格式化内容预览
const formatContent = (content) => {
  if (!content) return ''
  // 移除HTML标签，只保留文本
  const text = content.replace(/<[^>]+>/g, '')
  // 截取前200个字符
  return text.length > 200 ? text.slice(0, 200) + '...' : text
}

// 标签点击理
const handleTagClick = (tagId) => {
  router.push({
    path: '/topic',
    query: { tagId }
  })
}

const handleTopicClick = (topic) => {
  router.push({
    name: 'TopicDetail',
    params: { id: topic.id },
    state: { topic }
  })
}

// 发布话题
const handlePublish = () => {
  if (!userStore.isLogin) {
    ElMessage.warning('请先登录')
    router.push('/login')
    return
  }
  router.push('/topic/add')
}

onMounted(() => {
  fetchBanners()
  fetchCategories()
  fetchTopics()
  fetchRecommendTopics()
  fetchActiveUsers()
})
</script>

<style scoped>
/* 容器 */
.topic-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

/* Banner */
.banner-section {
  margin-bottom: 24px;
  border-radius: 8px;
  overflow: hidden;
  transition: all 0.3s ease;

  .banner-item {
    height: 100%;
    background-size: cover;
    background-position: center;
    position: relative;
    transition: transform 0.3s ease;
  }

  .banner-content {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    padding: 20px;
    background: linear-gradient(transparent, rgba(0, 0, 0, 0.7));
    color: white;
  }

  .banner-title {
    font-size: 24px;
    margin-bottom: 8px;
  }

  .banner-desc {
    font-size: 16px;
    opacity: 0.9;
  }
}


/* 分类筛选样式 */
.category-filter {
  margin-bottom: 20px;
  padding: 16px 20px;
  background: var(--bg-color);
  border-radius: 8px;
  overflow-x: auto;  /* 添加横向滚动 */

  .category-list {
    display: flex;
    align-items: center;
    gap: 8px;
    white-space: nowrap;  /* 防止换行 */
  }

  .divider {
    width: 1px;
    height: 14px;
    background-color: var(--border-color);
  }
}

/* 话题列表 */
.topic-section {
  background: var(--box-bg);
  border-radius: 8px;
  padding: 20px;

  .topic-item {
    padding: 20px;
    border-radius: 8px;
    background: var(--bg-color);
    transition: all 0.3s;
    border: 1px solid var(--border-color);
    position: relative;
    margin-bottom: 10px;

    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
      border-color: var(--primary-color);
    }

    .topic-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 16px;

      .topic-user {
        display: flex;
        align-items: center;
        gap: 12px;

        .user-info {
          display: flex;
          flex-direction: column;

          .username {
            color: var(--text-color);
            font-weight: 500;
          }

          .publish-time {
            color: var(--text-light);
            font-size: 12px;
          }
        }
      }

      .topic-status {
        display: flex;
        gap: 8px;

        .status-badge {
          font-size: 12px;
          padding: 0 6px;
          height: 20px;
          line-height: 18px;
        }
      }
    }

    .topic-content {
      .title-section {
        display: flex;
        align-items: center;
        gap: 12px;
        margin-bottom: 12px;

        .topic-title {
          color: var(--text-color);
          font-size: 18px;
          margin: 0;
          flex: 1;
        }

        .topic-category {
          flex-shrink: 0;

          .category-tag {
            cursor: pointer;
            transition: all 0.3s;

            &:hover {
              opacity: 0.8;
              transform: scale(1.05);
            }
          }
        }
      }

      .topic-preview {
        color: var(--text-color);
        font-size: 14px;
        line-height: 1.6;
        margin: 12px 0;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 3;
        -webkit-box-orient: vertical;
      }

      .topic-summary {
        color: var(--text-color);
        font-size: 14px;
        line-height: 1.6;
        margin-bottom: 12px;
      }
    }
  }
}

/* 筛选栏 */
.filter-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding: 16px 20px;
  background: var(--bg-color);
  border-radius: 8px;
  overflow-x: auto;  /* 添加横向滚动 */

  .filter-left,
  .filter-right {
    display: flex;
    align-items: center;
    gap: 16px;
    white-space: nowrap;  /* 防止换行 */
  }

  .filter-tabs,
  .filter-sort {
    display: flex;
    align-items: center;
    gap: 8px;
  }

  .publish-btn {
    display: flex;
    align-items: center;
    gap: 4px;
    padding: 8px 16px;
    font-size: 14px;
    flex-shrink: 0;  /* 防止按钮被压缩 */
  }
}

/* 话题列表布局 */
.topic-page-layout {
  display: flex;
  gap: 20px;

  .topic-main-content {
    flex: 1;
    min-width: 0;
  }

  .topic-sidebar {
    width: 300px;
    flex-shrink: 0;

    .sidebar-section {
      background: var(--box-bg);
      border-radius: 8px;
      padding: 16px;
      margin-bottom: 20px;

      .section-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 16px;

        h3 {
          font-size: 16px;
          color: var(--text-color);
          margin: 0;
        }

        .more-link {
          color: var(--primary-color);
          font-size: 14px;
          text-decoration: none;
        }
      }
    }

    /* 推荐话题 */
    .recommend-topics {
      display: flex;
      flex-direction: column;
      gap: 12px;

      .recommend-topic-item {
        padding: 12px;
        border-radius: 4px;
        background: var(--bg-color);
        text-decoration: none;
        transition: all 0.3s;

        &:hover {
          background: var(--button-hover-bg);
          transform: translateX(4px);
        }

        .recommend-topic-title {
          color: var(--text-color);
          font-size: 14px;
          margin-bottom: 8px;
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-line-clamp: 2;
          -webkit-box-orient: vertical;
        }

        .recommend-topic-meta {
          display: flex;
          gap: 12px;
          color: var(--text-light);
          font-size: 12px;
        }
      }
    }

    /* 活跃用户 */
    .active-users {
      display: flex;
      flex-direction: column;
      gap: 12px;

      .active-user-item {
        display: flex;
        align-items: center;
        gap: 12px;
        padding: 12px;
        border-radius: 4px;
        background: var(--bg-color);
        text-decoration: none;
        transition: all 0.3s;

        &:hover {
          background: var(--button-hover-bg);
          transform: translateX(4px);
        }

        .user-info {
          flex: 1;
          min-width: 0;

          .username {
            color: var(--text-color);
            font-size: 14px;
            font-weight: 500;
            margin-bottom: 4px;
          }

          .user-stats {
            display: flex;
            gap: 8px;
            color: var(--text-light);
            font-size: 12px;
          }
        }
      }
    }
  }
}

/* 分页 */
.pagination {
  background-color: var(--box-bg);
  margin-top: 24px;
  display: flex;
  justify-content: center;
}

/* Element Plus 组件覆盖 */
:deep(.el-carousel__item) {
  border-radius: 8px;
}

:deep(.el-pagination) {
  --el-pagination-bg-color: var(--box-bg);
  --el-pagination-hover-color: var(--primary-color);
  justify-content: center;

  .el-pagination button {
    background-color: var(--box-bg);
  }
}

/* 统一按钮样式 */
.action-item {
  padding: 6px 16px;
  color: var(--text-color);
  font-size: 14px;
  cursor: pointer;
  border-radius: 4px;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 4px;
  background-color: transparent;

  &:hover {
    color: var(--primary-color);
    background-color: var(--button-hover-bg);
  }

  &.active {
    color: var(--primary-color);
    font-weight: 500;
    background-color: transparent;

    &:hover {
      background-color: var(--button-hover-bg);
    }
  }
}

.topic-footer {
  margin-top: 16px;
  padding-top: 16px;
  border-top: 1px solid var(--border-color);

  .topic-info {
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-wrap: wrap;
    gap: 12px;

    .topic-tags {
      display: flex;
      flex-wrap: wrap;
      gap: 8px;
      flex: 1;
      min-width: 0;
    }

    .topic-stats {
      display: flex;
      gap: 16px;
      color: var(--text-light);
      font-size: 14px;
      flex-shrink: 0;

      span {
        display: flex;
        align-items: center;
        gap: 4px;
        transition: color 0.3s;

        &:hover {
          color: var(--primary-color);
        }
      }
    }
  }
}

</style>