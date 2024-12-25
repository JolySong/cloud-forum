<template>
  <div class="message-container">
    <!-- 消息类型选项卡 -->
    <div class="message-tabs">
      <el-tabs v-model="activeTab" @tab-change="handleTabChange">
        <el-tab-pane label="全部消息" name="all">
          <template #label>
            <div class="tab-label">
              <el-icon><Bell /></el-icon>
              全部消息
              <el-badge v-if="unreadCounts.all > 0" :value="unreadCounts.all" />
            </div>
          </template>
        </el-tab-pane>
        <el-tab-pane label="我的消息" name="mine">
          <template #label>
            <div class="tab-label">
              <el-icon><UserFilled /></el-icon>
              我的消息
              <el-badge v-if="unreadCounts.mine > 0" :value="unreadCounts.mine" />
            </div>
          </template>
        </el-tab-pane>
        <el-tab-pane label="点赞" name="like">
          <template #label>
            <div class="tab-label">
              <el-icon><Star /></el-icon>
              收到的赞
              <el-badge v-if="unreadCounts.like > 0" :value="unreadCounts.like" />
            </div>
          </template>
        </el-tab-pane>
        
        <el-tab-pane label="评论" name="comment">
          <template #label>
            <div class="tab-label">
              <el-icon><ChatLineRound /></el-icon>
              回复我的
              <el-badge v-if="unreadCounts.comment > 0" :value="unreadCounts.comment" />
            </div>
          </template>
        </el-tab-pane>
        
        <el-tab-pane label="系统通知" name="system">
          <template #label>
            <div class="tab-label">
              <el-icon><InfoFilled /></el-icon>
              系统通知
              <el-badge v-if="unreadCounts.system > 0" :value="unreadCounts.system" />
            </div>
          </template>
        </el-tab-pane>
      </el-tabs>

      <!-- 操作按钮 -->
      <div class="message-actions">
        <el-button 
          type="primary" 
          :disabled="!hasUnread"
          @click="handleReadAll"
        >
          全部已读
        </el-button>
      </div>
    </div>

    <!-- 消息列表 -->
    <div class="message-list" v-loading="loading">
      <template v-if="messages.length > 0">
        <div 
          v-for="message in messages" 
          :key="message.id" 
          class="message-item"
          :class="{ 
            unread: !message.isRead,
            invalid: !message.status
          }"
          @click="handleMessageClick(message)"
        >
          <!-- 消息头像 -->
          <el-avatar 
            :size="40" 
            :src="message.fromUserAvatar"
            v-if="message.fromUserAvatar"
          />
          <el-avatar 
            :size="40" 
            v-else
            :icon="message.type === 'system' ? InfoFilled : UserFilled"
          />
          
          <!-- 消息内容 -->
          <div class="message-content">
            <div class="message-header">
              <span class="from-user">{{ message.fromUserName || '系统消息' }}</span>
              <span class="time">{{ formatTime(message.createdAt) }}</span>
            </div>
            
            <!-- 消息类型图标 -->
            <div class="message-type-icon" :class="message.type">
              <el-icon v-if="message.type === 'like'"><Star /></el-icon>
              <el-icon v-else-if="message.type === 'comment'"><ChatLineRound /></el-icon>
              <el-icon v-else-if="message.type === 'system'"><InfoFilled /></el-icon>
            </div>
            
            <!-- 消息主体 -->
            <div class="message-body">
              <p class="message-text">{{ message.content }}</p>
              
              <!-- 引用内容 -->
              <div v-if="message.quote" class="message-quote">
                <div class="quote-title">{{ message.quote.title }}</div>
                <div class="quote-content">{{ message.quote.content }}</div>
              </div>
            </div>
          </div>
          
          <!-- 如果是失效的消息，显示失效标记 -->
          <div v-if="!message.status" class="invalid-mark">
            已失效
          </div>
        </div>
      </template>
      <el-empty v-else description="暂无消息" />
    </div>

    <!-- 分页器 -->
    <div class="pagination-wrapper" v-if="total > 0">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        :page-sizes="[10, 20, 50]"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Bell, Star, ChatLineRound, InfoFilled, UserFilled } from '@element-plus/icons-vue'
import { getMessages, readMessage, readAllMessages } from '@/api/message'
import { getImageUrl } from '@/utils/image'

const router = useRouter()

// 状态变量
const activeTab = ref('all')
const loading = ref(false)
const messages = ref([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)
const unreadCounts = ref({
  all: 0,
  like: 0,
  comment: 0,
  system: 0
})

// 计算属性
const hasUnread = computed(() => {
  return unreadCounts.value.all > 0
})

// 获取消息列表
const fetchMessages = async () => {
  loading.value = true
  try {
    const res = await getMessages({
      page: currentPage.value,
      size: pageSize.value,
      type: activeTab.value === 'all' ? undefined : activeTab.value
    })
    
    messages.value = await Promise.all(res.data.records.map(async message => ({
      ...message,
      fromUserAvatar: await getImageUrl(message.fromUserAvatar)
    })))
    
    total.value = res.data.total
    unreadCounts.value = res.data.unreadCounts
  } catch (error) {
    console.error('获取消息失败:', error)
    ElMessage.error('获取消息失败')
  } finally {
    loading.value = false
  }
}

// 标记消息已读
const markAsRead = async (messageId) => {
  try {
    await readMessage(messageId)
    // 更新未读数
    const message = messages.value.find(m => m.id === messageId)
    if (message && !message.isRead) {
      message.isRead = true
      unreadCounts.value.all--
      unreadCounts.value[message.type]--
    }
  } catch (error) {
    console.error('标记已读失败:', error)
  }
}

// 全部已读
const handleReadAll = async () => {
  try {
    await readAllMessages()
    messages.value.forEach(message => {
      message.isRead = true
    })
    unreadCounts.value = {
      all: 0,
      like: 0,
      comment: 0,
      system: 0
    }
    ElMessage.success('已全部标记为已读')
  } catch (error) {
    console.error('标记全部已读失败:', error)
    ElMessage.error('操作失败')
  }
}

// 处理消息点击
const handleMessageClick = async (message) => {
  // 标记已读
  if (!message.isRead) {
    await markAsRead(message.id)
  }
  
  // 跳转到相关内容
  if (message.targetType === 'topic') {
    router.push(`/topic/detail/${message.targetId}`)
  } else if (message.targetType === 'comment') {
    router.push(`/topic/detail/${message.topicId}#comment-${message.targetId}`)
  }
}

// 切换标签
const handleTabChange = () => {
  currentPage.value = 1
  fetchMessages()
}

// 分页处理
const handleSizeChange = (val) => {
  pageSize.value = val
  currentPage.value = 1
  fetchMessages()
}

const handleCurrentChange = (val) => {
  currentPage.value = val
  fetchMessages()
}

// 格式化时间
const formatTime = (time) => {
  if (!time) return ''
  
  const date = new Date(time)
  const now = new Date()
  const diff = now - date
  
  // 小于1分钟
  if (diff < 60000) {
    return '刚刚'
  }
  
  // 小于1小时
  if (diff < 3600000) {
    const minutes = Math.floor(diff / 60000)
    return `${minutes}分钟前`
  }
  
  // 小于24小时
  if (diff < 86400000) {
    const hours = Math.floor(diff / 3600000)
    return `${hours}小时前`
  }
  
  // 小于30天
  if (diff < 2592000000) {
    const days = Math.floor(diff / 86400000)
    return `${days}天前`
  }
  
  // 大于30天，显示具体日期
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  const hours = String(date.getHours()).padStart(2, '0')
  const minutes = String(date.getMinutes()).padStart(2, '0')
  
  // 如果是今年，不显示年份
  if (year === now.getFullYear()) {
    return `${month}-${day} ${hours}:${minutes}`
  }
  
  return `${year}-${month}-${day} ${hours}:${minutes}`
}

onMounted(() => {
  fetchMessages()
})
</script>

<style scoped>
.message-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  min-height: calc(100vh - 64px - 60px - 40px);
  display: flex;
  flex-direction: column;
}

.message-tabs {
  background: var(--box-bg);
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.tab-label {
  display: flex;
  align-items: center;
  gap: 6px;
  
  .el-icon {
    font-size: 16px;
  }
}

:deep(.el-tabs__item) {
  font-size: 15px;
  padding: 0 20px 16px;
  height: auto;
  line-height: 1.5;
  transition: all 0.3s;
  color: var(--text-color);

  &.is-active {
    font-weight: 500;
    color: var(--primary-color);
  }

  &:hover {
    color: var(--primary-color);
  }
}

.message-list {
  background: var(--box-bg);
  border-radius: 8px;
  padding: 20px;
  flex: 1;
}

.message-item {
  display: flex;
  gap: 16px;
  padding: 16px;
  border-radius: 8px;
  transition: all 0.3s;
  cursor: pointer;
  position: relative;
  
  &:hover {
    background: var(--bg-color);
  }
  
  &.unread {
    background: var(--bg-color);
    
    &::before {
      content: '';
      position: absolute;
      left: 0;
      top: 50%;
      transform: translateY(-50%);
      width: 4px;
      height: 4px;
      border-radius: 50%;
      background: var(--primary-color);
    }
  }
  
  &.invalid {
    opacity: 0.6;
    
    .message-content {
      text-decoration: line-through;
    }
  }
}

.message-content {
  flex: 1;
}

.message-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
  
  .from-user {
    font-weight: 500;
    color: var(--text-color);
  }
  
  .time {
    font-size: 12px;
    color: var(--text-light);
  }
}

.message-body {
  color: var(--text-color);
  font-size: 14px;
  line-height: 1.6;
}

.message-quote {
  margin-top: 8px;
  padding: 12px;
  background: var(--bg-color);
  border-radius: 4px;
  border-left: 3px solid var(--primary-color);
  
  .quote-title {
    font-weight: 500;
    margin-bottom: 4px;
  }
  
  .quote-content {
    color: var(--text-light);
    font-size: 13px;
  }
}

.message-type-icon {
  position: absolute;
  right: 16px;
  top: 16px;
  width: 24px;
  height: 24px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  
  &.like {
    color: #ff4d4f;
    background: rgba(255, 77, 79, 0.1);
  }
  
  &.comment {
    color: #52c41a;
    background: rgba(82, 196, 26, 0.1);
  }
  
  &.system {
    color: #1890ff;
    background: rgba(24, 144, 255, 0.1);
  }
  
  .el-icon {
    font-size: 14px;
  }
}

.pagination-wrapper {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.invalid-mark {
  position: absolute;
  right: 16px;
  top: 16px;
  padding: 2px 6px;
  border-radius: 4px;
  background: var(--el-color-danger);
  color: white;
  font-size: 12px;
}

/* 移动端适配 */
@media (max-width: 768px) {
  .message-container {
    padding: 16px;
  }
  
  .message-tabs {
    padding: 16px;
  }
  
  .message-list {
    padding: 16px;
  }
  
  .message-item {
    padding: 12px;
  }
}
</style> 