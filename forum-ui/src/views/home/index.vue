<template>
  <div class="home-container">
    <!-- 统计图表区域 -->
    <div class="stats-section">
      <!-- 基础统计卡片 -->
      <div class="stats-cards">
        <div class="stat-card">
          <div class="stat-icon">
            <el-icon><User /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.userCount || 0 }}</div>
            <div class="stat-label">注册用户</div>
          </div>
        </div>
        
        <div class="stat-card">
          <div class="stat-icon">
            <el-icon><Document /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.topicCount || 0 }}</div>
            <div class="stat-label">话题总数</div>
          </div>
        </div>

        <div class="stat-card">
          <div class="stat-icon">
            <el-icon><ChatRound /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.commentCount || 0 }}</div>
            <div class="stat-label">评论总数</div>
          </div>
        </div>

        <div class="stat-card">
          <div class="stat-icon">
            <el-icon><View /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.viewCount || 0 }}</div>
            <div class="stat-label">总浏览量</div>
          </div>
        </div>
      </div>

      <!-- 图表展示 -->
      <div class="stats-charts">
        <!-- 活跃度趋势图 -->
        <div class="chart-card">
          <h3>活跃度趋势</h3>
          <v-chart class="chart" :option="activityOption" autoresize />
        </div>
        
        <!-- 内容分布图 -->
        <div class="chart-card">
          <h3>内容分布</h3>
          <v-chart class="chart" :option="distributionOption" autoresize />
        </div>
      </div>
    </div>

    <!-- 内容区域包装器 -->
    <div class="content-wrapper">
      <!-- 主要内容区域 -->
      <div class="main-content">
        <!-- 热门话题 -->
        <div class="section">
          <div class="section-header">
            <h2>热门话题</h2>
            <router-link to="/topic/hot" class="more-link">更多</router-link>
          </div>
          <div class="topic-list" v-loading="loading.hot">
            <div v-for="topic in hotTopics" :key="topic.id" class="topic-item">
              <div class="topic-author">
                <el-avatar :size="32" :src="topic.author.avatar" />
                <div class="author-info">
                  <span class="author-name">{{ topic.author.nickname }}</span>
                  <span class="publish-time">{{ topic.createdAt }}</span>
                </div>
              </div>
              
              <router-link :to="`/topic/detail/${topic.id}`" class="topic-title">
                {{ topic.title }}
              </router-link>
              <div class="topic-meta">
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

      <!-- 侧边栏 -->
      <div class="sidebar">
        <!-- 热门分类 -->
        <div class="section">
          <div class="section-header">
            <h2>热门分类</h2>
            <router-link to="/category" class="more-link">更多</router-link>
          </div>
          <div class="category-list" v-loading="loading.categories">
            <div v-for="category in hotCategories" :key="category.id" class="category-item" 
            @click="gotoTopicByCategory(category.id)">
              <div class="category-info">
                <span class="category-name">{{ category.name }}</span>
                <span class="category-count">{{ category.topicCount }} 话题</span>
              </div>
              <div class="category-desc">{{ category.description }}</div>
            </div>
          </div>
        </div>

        <!-- 热门评论 -->
        <div class="section">
          <div class="section-header">
            <h2>热门评论</h2>
            <router-link to="/comment/hot" class="more-link">更多</router-link>
          </div>
          <div class="comment-list" v-loading="loading.comments">

            <div v-for="comment in hotComments" :key="comment.id" class="comment-item">
              <div class="comment-user" @click="goToTopicDetail(comment.topicId)">
                <el-avatar :size="32" :src="comment.userAvatar" />
                <div class="author-info">
                  <span class="author-name">{{ comment.nickname }}</span>
                  <span class="publish-time">{{ comment.createdAt }}</span>
                </div>
              </div>
              <div class="comment-bottom">
                <div class="comment-content">
                    {{ comment.content }}
                </div>
                <div class="like-count">
                  <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24"><path fill="currentColor" fill-opacity="0" d="M7 11v9h-4v-9h4Z"><animate fill="freeze" attributeName="fill-opacity" begin="0.6s" dur="0.5s" values="0;1"/></path><path fill="none" stroke="currentColor" stroke-dasharray="80" stroke-dashoffset="80" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 11l5 -8l3 1l-1 6h7v3l-3 7h-11h-4v-9h4v9"><animate fill="freeze" attributeName="stroke-dashoffset" dur="0.6s" values="80;0"/></path></svg>
                  {{ comment.likeCount }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { User, Document, ChatRound, View } from '@element-plus/icons-vue'
import { use } from 'echarts/core'
import { CanvasRenderer } from 'echarts/renderers'
import { LineChart, PieChart } from 'echarts/charts'
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent
} from 'echarts/components'
import VChart from 'vue-echarts'
import { getHotTopics, getHotComments, getHotCategories, getForumStats } from '@/api'
import { useRouter } from 'vue-router'
import { getImageUrl } from '@/utils/image'

const router = useRouter()

// 注册 ECharts 组件
use([
  CanvasRenderer,
  LineChart,
  PieChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent
])

// 数据
const hotTopics = ref([])
const recommendTopics = ref([])
const hotComments = ref([])
const hotCategories = ref([])

// 加载状态
const loading = ref({
  hot: false,
  recommend: false,
  comments: false,
  categories: false
})

// 统计数据
const stats = ref({
  userCount: 0,
  topicCount: 0,
  commentCount: 0,
  viewCount: 0
})

// 活跃度趋势图配置
const activityOption = computed(() => ({
  tooltip: {
    trigger: 'axis'
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: stats.value.activityTrend?.dates || [],
    axisLine: {
      lineStyle: {
        color: '#666'
      }
    }
  },
  yAxis: {
    type: 'value',
    axisLine: {
      lineStyle: {
        color: '#666'
      }
    }
  },
  series: [
    {
      name: '话题数',
      type: 'line',
      smooth: true,
      data: stats.value.activityTrend?.topics || [],
      itemStyle: {
        color: '#409eff'
      }
    },
    {
      name: '评论数',
      type: 'line',
      smooth: true,
      data: stats.value.activityTrend?.comments || [],
      itemStyle: {
        color: '#67c23a'
      }
    }
  ]
}))

// 内容分布图配置
const distributionOption = computed(() => ({
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left',
    textStyle: {
      color: '#666'
    }
  },
  series: [
    {
      name: '内容分布',
      type: 'pie',
      radius: '50%',
      data: [
        { value: stats.value.topicCount, name: '话题' },
        { value: stats.value.commentCount, name: '评论' }
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
}))

// 获取热门分类
const fetchHotCategories = async () => {
  loading.value.categories = true
  try {
    const res = await getHotCategories({ limit: 3 })
    hotCategories.value = res.data
  } catch (error) {
    console.error('获取热门分类失败:', error)
  } finally {
    loading.value.categories = false
  }
}

// 跳转到话题详情
const goToTopicDetail = (topicId) => {
  router.push(`/topic/detail/${topicId}`)
}

// 跳转到话题列表
const gotoTopicByCategory = (categoryId) => {
  router.push({
    path: '/topic',
    query: {
      categoryId: categoryId
    }
  })
}

// 获取热门话题
const fetchHotTopics = async () => {
  loading.value.hot = true
  try {
    const res = await getHotTopics({ limit: 4 })
    hotTopics.value = res.data
    hotTopics.value.forEach(async (topic) => {
      topic.author.avatar = await getImageUrl(topic.author.avatar)
    })
  } catch (error) {
    console.error('获取热门话题失败:', error)
  } finally {
    loading.value.hot = false
  }
}

// 获取热门评论
const fetchHotComments = async () => {
  loading.value.comments = true
  try {
    const res = await getHotComments({ limit: 3 })
    hotComments.value = res.data
    hotComments.value.forEach(async (comment) => {
      comment.userAvatar = await getImageUrl(comment.userAvatar)
    })
  } catch (error) {
    console.error('获取热门评论:', error)
  } finally {
    loading.value.comments = false
  }
}

// 获取统计数据
const fetchStats = async () => {
  try {
    const res = await getForumStats()
    stats.value = res.data
  } catch (error) {
    console.error('获取统计数据失败:', error)
  }
}

// 页面加载时获取数据
onMounted(() => {
  fetchHotTopics()
  fetchHotComments()
  fetchHotCategories()
  fetchStats()
})
</script>

<style scoped>
.home-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.stats-section {
  margin-bottom: 20px;
}

.content-wrapper {
  display: flex;
  gap: 20px;
}

.main-content {
  flex: 1;
  min-width: 0;
}

.sidebar {
  width: 300px;
  flex-shrink: 0;
}

.section {
  background: var(--box-bg);
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

h2 {
  font-size: 18px;
  color: var(--text-color);
  margin: 0;
}

.more-link {
  color: var(--primary-color);
  text-decoration: none;
  font-size: 14px;
}

.topic-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.topic-item {
  padding: 16px;
  border-radius: 4px;
  background: var(--bg-color);
  transition: all 0.3s;

  &:hover {
    background: var(--button-hover-bg);
    transform: translateX(4px);
  }
}

.topic-author {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;

  .author-info {
    display: flex;
    flex-direction: column;
    gap: 4px;
  }

  .author-name {
    color: var(--text-color);
    font-size: 14px;
    font-weight: 500;
  }

  .publish-time {
    color: var(--text-light);
    font-size: 12px;
  }
}

.topic-title {
  color: var(--text-color);
  text-decoration: none;
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 12px;
  display: block;
  line-height: 1.5;

  &:hover {
    color: var(--primary-color);
  }
}

.topic-meta {
  display: flex;
  gap: 16px;
  color: var(--text-light);
  font-size: 13px;

  span {
    display: flex;
    align-items: center;
    gap: 4px;
  }

  .el-icon {
    font-size: 16px;
  }
}

.comment-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.comment-item {
  padding: 5px;
  border-radius: 4px;
  background: var(--bg-color);
  transition: all 0.3s;

  &:hover {
    background: var(--button-hover-bg);
    transform: translateX(4px);
  }
}

.comment-user {
  display: flex;
  align-items: center;
  gap: 12px;
  margin: 2px 10px 12px 10px;
  /* margin-bottom: 12px;
  margin-left: 6px; */

  .author-info {
    display: flex;
    flex-direction: column;
    gap: 4px;
  }

  .author-name {
    color: var(--text-color);
    font-size: 12px;
    font-weight: 500;
  }

  .publish-time {
    color: var(--text-light);
    font-size: 10px;
  }
}

.comment-bottom {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 8px;
}

.comment-content {
  color: var(--text-color);
  font-size: 12px;
  line-height: 1.2;
  margin-left: 12px;
  flex: 1;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.like-count {
  display: flex;
  align-items: center;
  gap: 4px;
  color: var(--text-light);
  font-size: 12px;
  flex-shrink: 0;
  padding: 2px 4px;
  border-radius: 4px;
  transition: all 0.3s;

  &:hover {
    background: var(--button-hover-bg);
    color: var(--primary-color);
  }
}

/* 添加分类相关样式 */
.category-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.category-item {
  padding: 12px;
  border-radius: 4px;
  background: var(--bg-color);
  text-decoration: none;
  transition: all 0.3s;
}

.category-item:hover {
  background: var(--button-hover-bg);
  transform: translateX(4px);
}

.category-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 4px;
}

.category-name {
  color: var(--text-color);
  font-size: 16px;
  font-weight: 500;
}

.category-count {
  color: var(--text-light);
  font-size: 12px;
}

.category-desc {
  color: var(--text-light);
  font-size: 13px;
  line-height: 1.4;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.stats-cards {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-bottom: 20px;
}

.stat-card {
  background: var(--box-bg);
  border-radius: 8px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 16px;
  transition: all 0.3s;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
  }
}

.stat-icon {
  width: 48px;
  height: 48px;
  border-radius: 12px;
  background: var(--primary-color);
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 24px;
  opacity: 0.9;
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 24px;
  font-weight: 600;
  color: var(--text-color);
  line-height: 1.2;
}

.stat-label {
  font-size: 14px;
  color: var(--text-light);
  margin-top: 4px;
}

.stats-charts {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

.chart-card {
  background: var(--box-bg);
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);

  h3 {
    margin: 0 0 16px;
    font-size: 16px;
    color: var(--text-color);
  }
}

.chart {
  height: 230px;
}

/* 移动端适配 */
@media (max-width: 768px) {
  .content-wrapper {
    flex-direction: column;
  }

  .sidebar {
    width: 100%;
  }
}

@media (max-width: 480px) {
  .stats-cards {
    grid-template-columns: 1fr;
  }
}

/* 添加悬浮时显示完整内容的提示 */
.comment-item {
  &:hover .comment-content {
    white-space: normal;
    overflow: visible;
    position: relative;
  }
}
</style>


</```
rewritten_file>