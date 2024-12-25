<template>
  <div class="topic-detail-container">
    <!-- 添加面包屑导航 -->
    <div class="breadcrumb-nav">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/topic' }">话题</el-breadcrumb-item>
        <el-breadcrumb-item 
          v-if="topic.categoryId"
          :to="{ path: '/topic', query: { categoryId: topic.categoryId }}"
        >
          {{ topic.categoryName }}
        </el-breadcrumb-item>
        <el-breadcrumb-item>{{ topic.title || '话题详情' }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="topic-detail-layout">
      <!-- 左侧主内容 -->
      <div class="topic-detail-main">
        <div class="topic-detail-card" v-loading="loading">
          <!-- 话题头部信息 -->
          <div class="topic-header">
            <div class="topic-author">
                <!-- 话题标题 -->
                <h1 class="topic-title">{{ topic.title }}</h1>
            </div>
            <div class="topic-actions">
              <!-- 添加置顶标签 -->
              <div class="topic-badges">
                <el-tag 
                  v-if="topic.isTop" 
                  type="danger" 
                  size="small" 
                  effect="dark"
                  class="topic-badge"
                >
                  置顶
                </el-tag>
                <el-tag 
                  v-if="topic.isFeatured" 
                  type="success" 
                  size="small" 
                  effect="dark"
                  class="topic-badge"
                >
                  精选
                </el-tag>
              </div>
              <!-- 操作按钮 -->
              <div class="action-buttons">
                <div 
                  class="action-item"
                  :class="{ active: topic.isLiked }"
                  @click="handleLike"
                >
                  <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24"><path fill="currentColor" fill-opacity="0" d="M7 11v9h-4v-9h4Z"><animate fill="freeze" attributeName="fill-opacity" begin="0.6s" dur="0.5s" values="0;1"/></path><path fill="none" stroke="currentColor" stroke-dasharray="80" stroke-dashoffset="80" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 11l5 -8l3 1l-1 6h7v3l-3 7h-11h-4v-9h4v9"><animate fill="freeze" attributeName="stroke-dashoffset" dur="0.6s" values="80;0"/></path></svg>
                  点赞 {{ topic.likeCount }}
                </div>
                <div 
                  class="action-item"
                  :class="{ active: topic.isCollected }"
                  @click="handleFavorite"
                >
                  <el-icon><Star /></el-icon>
                  收藏 {{ topic.collectCount }}
                </div>
              </div>
            </div>
        </div>
        <div class="publish-info">
          <span>发布于 {{ topic.createdAt }}</span>
          <span>阅读 {{ topic.viewCount }}</span>
          <!-- 分类和标签 -->
          <div class="topic-tags">
            <!-- 分类标签 -->
            <el-tag 
              size="small"
              class="category-tag"
            >
              {{ topic.categoryName }}
            </el-tag>
            <!-- 话题标签 -->
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
              @click="handleTagClick(tag.id)"
            >
              {{ tag.name }}
            </el-tag>
          </div>
        </div>

          <!-- 话题内容 -->
          <div class="topic-content" ref="contentRef" v-html="formatContent(topic.content)"></div>

          <!-- 添加图片预览组件 -->
          <el-image-viewer
            v-if="showViewer"
            :url-list="imageList"
            :initial-index="currentImageIndex"
            @close="showViewer = false"
          />
        </div>

        <!-- 评论区 -->
        <div class="topic-comments">
          <comment-list 
            :topic-id="route.params.id"
            :is-topic-author="isTopicAuthor"
          />
        </div>
      </div>

      <!-- 右侧边栏 -->
      <div class="topic-detail-sidebar">
        <!-- 作者信息卡片 -->
        <div class="author-card">
          <div class="author-header">
            <el-avatar :size="64" :src="topic.author?.avatar" />
            <div class="author-name">{{ topic.author?.nickname }}</div>
          </div>
          <div class="author-stats">
            <div class="stat-item">
              <div class="stat-value">{{ topic.author?.topicCount }}</div>
              <div class="stat-label">话题</div>
            </div>
            <div class="stat-item">
              <div class="stat-value">{{ topic.author?.likeCount }}</div>
              <div class="stat-label">获赞</div>
            </div>
            <div class="stat-item">
              <div class="stat-value">{{ topic.author?.followerCount }}</div>
              <div class="stat-label">粉丝</div>
            </div>
          </div>

          <div class="author-actions" v-if="!isTopicAuthor">
            <div 
              class="action-item"
              :class="{ active: topic.author?.isFollowed }"
              @click="handleFollow"
            >
              <el-icon><Plus /></el-icon>
              {{ topic.author?.isFollowed ? '已关注' : '关注' }}
            </div>
            <div 
              class="action-item"
              @click="handleMessage"
            >
              <el-icon><Message /></el-icon>
              发消息
            </div>
          </div>
        </div>

        <!-- 相关话题 -->
        <div class="related-topics">
          <h3>相关话题</h3>
          <div class="topic-list">
            <router-link 
              v-for="item in relatedTopics" 
              :key="item.id"
              :to="`/topic/${item.id}`"
              class="topic-item"
            >
              <div class="topic-title">{{ item.title }}</div>
              <div class="topic-meta">
                <span>{{ item.viewCount }}浏览</span>
                <span>{{ item.commentCount }}评论</span>
              </div>
            </router-link>
          </div>
        </div>
      </div>
    </div>

    <back-to-top />

    <login-dialog
      v-model="showLoginDialog"
      @confirm="handleLoginConfirm"
    />
  </div>
</template>

<script setup>
import { ref, onMounted, computed, nextTick, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElImageViewer } from 'element-plus'
import { Star, Plus, Message } from '@element-plus/icons-vue'
import { useUserStore } from '@/store/modules/user'
import { getTopicDetail, likeTopic, favoriteTopic, unlikeTopic, unfavoriteTopic, followAuthor, unfollowAuthor, getAuthorInfo, getAuthorTopics } from '@/api'
import CommentList from './components/CommentList.vue'
import BackToTop from '@/components/common/BackToTop.vue'
import LoginDialog from '@/components/common/LoginDialog.vue'
import { getImageUrl } from '@/utils/image'
const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const loading = ref(false)
const topic = ref({})
const relatedTopics = ref([])
const showLoginDialog = ref(false)

// 计算是否是话题作者
const isTopicAuthor = computed(() => {
  return userStore.userInfo?.id === topic.value.userId
})

// 获取话题详情
const fetchTopicDetail = async () => {

  loading.value = true
  try {
    const res = await getTopicDetail(route.params.id)
    topic.value = res.data
    fetchUserStat()
    fetchAuthorTopics()
  } catch (error) {
    ElMessage.error('获取话题详情失败')
  } finally {
    loading.value = false
  }
}

// 获取作者信息
const fetchUserStat = async () => {
  const res = await getAuthorInfo(topic.value.userId)
  if (res.data) {
    res.data.avatar = await getImageUrl(res.data.avatar)
    topic.value.author = res.data
  }
}

// 获取作者话题
const fetchAuthorTopics = async () => {
  const res = await getAuthorTopics({ userId: topic.value.userId })
  relatedTopics.value = res.data
}

// 修改登录检查函数
const checkLogin = () => {
  if (!userStore.isLogin) {
    showLoginDialog.value = true
    return false
  }
  return true
}

// 修改点赞处理函数
const handleLike = async () => {
  if (!checkLogin()) return
  
  try {
    if (topic.value.isLiked) {
      await unlikeTopic(topic.value.id)
    } else {
      await likeTopic(topic.value.id)
    }
    topic.value.isLiked = !topic.value.isLiked
    topic.value.likeCount += topic.value.isLiked ? 1 : -1
    ElMessage.success(topic.value.isLiked ? '点赞成功' : '取消点赞成功')
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

// 修改收藏处理函数
const handleFavorite = async () => {
  if (!checkLogin()) return
  
  try {
    if (topic.value.isCollected) {
      await unfavoriteTopic(topic.value.id)
    } else {
      await favoriteTopic(topic.value.id)
    }
    topic.value.isCollected = !topic.value.isCollected
    topic.value.collectCount += topic.value.isCollected ? 1 : -1
    ElMessage.success(topic.value.isCollected ? '收藏成功' : '取消收藏成功')
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

// 修改关注作者处理函数
const handleFollow = async () => {
  if (!checkLogin()) return
  
  try {
    if (topic.value.author?.isFollowed) {
      await unfollowAuthor(topic.value.userId)
    } else {
      await followAuthor(topic.value.userId)
    }
    topic.value.author.isFollowed = !topic.value.author.isFollowed
    topic.value.author.followerCount += topic.value.author.isFollowed ? 1 : -1
    ElMessage.success(topic.value.author.isFollowed ? '关注成功' : '取消关注成功')
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

// 修改发送消息处理函数
const handleMessage = () => {
  if (!checkLogin()) return


}

// 点击标签
const handleTagClick = (tagId) => {
  router.push({
    path: '/topic',
    query: { tagId }
  })
}

// 图片预览相关
const showViewer = ref(false)
const imageList = ref([])
const currentImageIndex = ref(0)
const contentRef = ref(null)

// 格式化内容，处理图片
const formatContent = (content) => {
  if (!content) return ''
  
  // 将图片添加点击事件的类名
  return content.replace(/<img/g, '<img class="preview-image"')
}

// 初始化图片预览
const initImagePreview = () => {
  nextTick(() => {
    if (!contentRef.value) return
    
    // 获取所有图片
    const images = contentRef.value.querySelectorAll('img.preview-image')
    imageList.value = Array.from(images).map(img => img.src)

    // 为每个图片添加点击事件
    images.forEach((img, index) => {
      // 设置图片缩放
      img.style.maxWidth = '100%'
      img.style.cursor = 'zoom-in'
      img.onclick = () => {
        currentImageIndex.value = index
        showViewer.value = true
      }
    })
  })
}

// 监听话题内容变化
watch(() => topic.value.content, () => {
  initImagePreview()
})

// 添加处理登录确认的函数
const handleLoginConfirm = () => {
  router.push('/login')
}

onMounted(() => {
  fetchTopicDetail()
  initImagePreview()
})
</script>

<style scoped>
.topic-detail-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.topic-detail-layout {
  display: flex;
  gap: 20px;
}

.topic-detail-main {
  flex: 1;
  min-width: 0;
}

.topic-detail-card {
  background: var(--box-bg);
  border-radius: 8px;
  padding: 24px;
  margin-bottom: 20px;
  overflow: hidden;
}

.topic-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.topic-author {
  display: flex;
  align-items: center;
  gap: 12px;
}

.author-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.author-name {
  color: var(--text-color);
  font-size: 16px;
  font-weight: 500;
}

.publish-info {
  color: var(--text-light);
  font-size: 14px;
  display: flex;
  gap: 16px;
}

.topic-actions {
  display: flex;
  align-items: center;
  gap: 16px;
}

.topic-badges {
  display: flex;
  gap: 8px;
}

.topic-badge {
  font-size: 12px;
  padding: 0 6px;
  height: 20px;
  line-height: 18px;
}

.action-buttons {
  display: flex;
  gap: 12px;
}

.topic-title {
  font-size: 24px;
  color: var(--text-color);
  margin: 0 0 16px;
}

.topic-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 24px;
}

.category-tag {
  cursor: pointer;
  transition: all 0.3s;
}

.category-tag:hover {
  opacity: 0.8;
  transform: scale(1.05);
}

.topic-tag {
  cursor: pointer;
  transition: all 0.3s;
  border: none;
}

.topic-tag:hover {
  transform: scale(1.05);
  opacity: 0.9;
}

.topic-content {
  color: var(--text-color);
  font-size: 16px;
  line-height: 1.8;
  max-width: 100%;
  overflow-wrap: break-word;
  word-break: break-all;

  /* 图片样式 */
  :deep(img) {
    max-width: 100% !important;
    height: auto !important;
    display: block;
    margin: 16px auto;
    border-radius: 8px;
    object-fit: contain;
    transition: transform 0.3s ease;
    cursor: zoom-in;
    box-sizing: border-box;
    width: auto !important;

    &:hover {
      transform: scale(1.02);
    }
  }

  /* 图片容器样式 */
  :deep(p) {
    margin: 16px 0;
    max-width: 100%;
    
    img {
      margin: 0 auto;
      max-width: 100% !important;
    }
  }
}

/* 右侧边栏样式 */
.topic-detail-sidebar {
  width: 300px;
  flex-shrink: 0;
}

.author-card {
  background: var(--box-bg);
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
}

.author-header {
  text-align: center;
  margin-bottom: 16px;
}

.author-header .author-name {
  margin-top: 12px;
  font-size: 18px;
  font-weight: 500;
}

.author-stats {
  display: flex;
  justify-content: space-around;
  margin-bottom: 20px;
}

.stat-item {
  text-align: center;
}

.stat-value {
  font-size: 20px;
  font-weight: 500;
  color: var(--text-color);
}

.stat-label {
  font-size: 14px;
  color: var(--text-light);
}

.author-actions {
  display: flex;
  gap: 12px;
}

.related-topics {
  background: var(--box-bg);
  border-radius: 8px;
  padding: 20px;
}

.related-topics h3 {
  font-size: 16px;
  margin: 0 0 16px;
  color: var(--text-color);
}

.topic-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.topic-item {
  padding: 12px;
  border-radius: 4px;
  background: var(--bg-color);
  text-decoration: none;
  transition: all 0.3s;
}

.topic-item:hover {
  background: var(--button-hover-bg);
  transform: translateX(4px);
}

.topic-item .topic-title {
  color: var(--text-color);
  font-size: 14px;
  margin-bottom: 8px;
}

.topic-item .topic-meta {
  display: flex;
  gap: 12px;
  color: var(--text-light);
  font-size: 12px;
}

/* 添加包屑样式 */
.breadcrumb-nav {
  background: var(--box-bg);
  padding: 16px 24px;
  border-radius: 8px;
  margin-bottom: 20px;
}

:deep(.el-breadcrumb__item) {
  .el-breadcrumb__inner {
    color: var(--text-light);
    font-weight: normal;
    
    &.is-link {
      color: var(--text-color);
      
      &:hover {
        color: var(--primary-color);
      }
    }
  }
  
  &:last-child .el-breadcrumb__inner {
    color: var(--text-color);
    font-weight: 500;
  }
}

/* 操作按钮样式 */
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
}

.action-item:hover {
  color: var(--primary-color);
  background-color: var(--button-hover-bg);
}

.action-item.active {
  color: var(--primary-color);
  font-weight: 500;
  background-color: transparent;
}

.action-item.active:hover {
  background-color: var(--button-hover-bg);
}

.action-buttons {
  display: flex;
  gap: 8px;
}

.author-actions {
  display: flex;
  gap: 8px;
}

/* 图片预览组件样式适配 */
:deep(.el-image-viewer__wrapper) {
  background-color: rgba(0, 0, 0, 0.8);
}

:deep(.el-image-viewer__close) {
  color: #fff;
}

:deep(.el-image-viewer__btn) {
  color: #fff;
  
  &:hover {
    color: var(--primary-color);
  }
}
</style>