<template>
  <div class="like-list">
    <template v-if="likes.length > 0">
      <div class="like-items">
        <!-- 话题点赞 -->
        <div v-for="like in topicLikes" :key="like.likeId" class="like-item">
          <div class="like-content" @click="handleTopicClick(like)">
            <div class="like-type topic-like">话题点赞</div>
            <h3 class="topic-title">{{ like.title }}</h3>
            <div class="topic-meta">
              <span>点赞于：{{ formatDate(like.likeTime) }}</span>
              <span>作者：{{ like.authorName }}</span>
              <span>{{ like.viewCount }} 浏览</span>
              <span>{{ like.commentCount }} 评论</span>
              <span>{{ like.likeCount }} 点赞</span>
            </div>
          </div>
          <div class="like-actions">
            <el-button 
              type="text" 
              class="action-btn unlike-btn"
              @click.stop="$emit('unlike', like)"
            >
              <el-icon><CloseBold /></el-icon>
              取消点赞
            </el-button>
          </div>
        </div>

        <!-- 评论点赞 -->
        <div v-for="like in commentLikes" :key="like.likeId" class="like-item">
          <div class="like-content" @click="handleCommentClick(like)">
            <div class="like-type comment-like">评论点赞</div>
            <div class="comment-content">{{ like.title }}</div>
            <div class="comment-meta">
              <span>点赞于：{{ formatDate(like.likeTime) }}</span>
              <span>作者：{{ like.authorName }}</span>
              <span>来自话题：{{ like.topicTitle }}</span>
              <span>{{ like.likeCount }} 点赞</span>
            </div>
          </div>
          <div class="like-actions">
            <el-button 
              type="text" 
              class="action-btn unlike-btn"
              @click.stop="$emit('unlike', like)"
            >
              <el-icon><CloseBold /></el-icon>
              取消点赞
            </el-button>
          </div>
        </div>
      </div>
    </template>
    <el-empty v-else description="暂无点赞" />
    
    <div v-if="loading" class="loading">
      <el-skeleton :rows="3" animated />
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { CloseBold } from '@element-plus/icons-vue'

const props = defineProps({
  likes: {
    type: Array,
    default: () => []
  },
  loading: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['unlike'])
const router = useRouter()

// 分类点赞数据
const topicLikes = computed(() => props.likes.filter(like => like.targetType === "0"))
const commentLikes = computed(() => props.likes.filter(like => like.targetType === "1"))

// 点击话题跳转
const handleTopicClick = (like) => {
  router.push(`/topic/detail/${like.targetId}`)
}

// 点击评论跳转
const handleCommentClick = (like) => {
  router.push(`/topic/detail/${like.topicId}`)
}

// 格式化日期
const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}
</script>

<style scoped>
.like-list {
  width: 100%;
  
  .like-items {
    display: flex;
    flex-direction: column;
    width: 100%;
  }
}

.like-item {
  width: 100%;
  margin-bottom: 16px;
  padding: 16px;
  background: var(--bg-color);
  border-radius: 8px;
  transition: all 0.3s;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 16px;
  box-sizing: border-box;

  &:hover {
    background: var(--button-hover-bg);
  }
}

.like-content {
  flex: 1;
  cursor: pointer;
  min-width: 0;
  
  &:hover .topic-title {
    color: var(--primary-color);
  }
}

.like-type {
  display: inline-block;
  padding: 4px 12px;
  border-radius: 4px;
  color: white;
  font-size: 12px;
  margin-bottom: 12px;
  font-weight: 500;
  transition: all 0.3s;

  &.topic-like {
    background: #ff9800; /* 橙色 */
    box-shadow: 0 2px 4px rgba(255, 152, 0, 0.2);

    &:hover {
      background: #f57c00;
    }
  }

  &.comment-like {
    background: #ff4081; /* 粉色 */
    box-shadow: 0 2px 4px rgba(255, 64, 129, 0.2);

    &:hover {
      background: #f50057;
    }
  }
}

.topic-title {
  color: var(--text-color);
  font-size: 16px;
  margin: 0 0 12px;
  transition: color 0.3s;
}

.topic-meta,
.comment-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
  color: var(--text-light);
  font-size: 14px;
}

.comment-content {
  color: var(--text-color);
  font-size: 14px;
  line-height: 1.6;
  margin: 12px 0;
  background: var(--bg-color);
  padding: 12px;
  border-radius: 6px;
  border-left: 3px solid var(--primary-color);
  white-space: pre-wrap;
}

.loading {
  margin-top: 16px;
}

.like-actions {
  display: flex;
  gap: 8px;
  flex-shrink: 0;
  margin-top: 8px;
}

.action-btn {
  display: flex;
  align-items: center;
  gap: 4px;
  padding: 8px 12px;
  color: var(--text-light);
  transition: all 0.3s;
  
  .el-icon {
    font-size: 16px;
  }

  &:hover {
    background: var(--button-hover-bg);
  }
}

.unlike-btn:hover {
  color: #ff4d4f;
}
</style> 