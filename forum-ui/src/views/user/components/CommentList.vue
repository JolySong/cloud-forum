<template>
  <div class="comment-list">
    <template v-if="comments.length > 0">
      <div 
        v-for="comment in comments" 
        :key="comment.id" 
        class="comment-item"
        :class="{
          'is-pending': comment.audit === 0,
          'is-rejected': comment.audit === 2
        }"
      >
        <div class="comment-content" @click="handleCommentClick(comment)">
          <!-- 标题和审核状态 -->
          <div class="comment-header">
            <span class="topic-title">
              {{ comment.topicTitle }}
              <el-tag 
                v-if="comment.audit !== 1" 
                :type="getAuditTagType(comment.audit)"
                size="small"
                class="audit-tag"
              >
                {{ getAuditText(comment.audit) }}
              </el-tag>
            </span>
          </div>

          <!-- 评论内容 -->
          <div class="comment-text">
            {{ comment.content }}
          </div>
          
          <!-- 评论元信息 -->
          <div class="comment-meta">
            <span>{{ comment.createdAt }}</span>
            <span>{{ comment.likeCount }} 点赞</span>
          </div>

          <!-- 审核不通过原因 -->
          <div v-if="comment.audit === 2" class="reject-reason">
            驳回原因：{{ comment.auditReason || '内容不符合规范' }}
          </div>
        </div>

        <!-- 操作按钮 -->
        <div class="comment-actions">
          <el-button 
            type="text" 
            class="action-btn delete-btn"
            @click.stop="$emit('delete', comment)"
          >
            <el-icon><Delete /></el-icon>
            删除
          </el-button>
        </div>
      </div>
    </template>
    <el-empty v-else description="暂无评论" />
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { Delete } from '@element-plus/icons-vue'

const props = defineProps({
  comments: {
    type: Array,
    default: () => []
  },
  loading: {
    type: Boolean,
    default: false
  },
  hasMore: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['load-more'])
const router = useRouter()

const handleCommentClick = (comment) => {
  router.push(`/topic/detail/${comment.topicId}`)
}

// 获取审核状态标签类型
const getAuditTagType = (audit) => {
  switch (audit) {
    case 0:
      return 'warning'
    case 2:
      return 'danger'
    default:
      return 'info'
  }
}

// 获取审核状态文本
const getAuditText = (audit) => {
  switch (audit) {
    case 0:
      return '审核中'
    case 2:
      return '未通过'
    default:
      return '未知状态'
  }
}
</script>

<style scoped>
.comment-list {
  width: 100%;
}

.comment-item {
  width: 100%;
  padding: 16px;
  background: var(--bg-color);
  border-radius: 8px;
  transition: all 0.3s;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 16px;
  box-sizing: border-box;
  margin-bottom: 16px;

  &:hover {
    background: var(--button-hover-bg);
  }

  &.is-pending {
    opacity: 0.8;
    background: var(--bg-color);
  }

  &.is-rejected {
    opacity: 0.7;
    background: rgba(var(--el-color-danger-rgb), 0.1);
  }
}

.comment-content {
  flex: 1;
  cursor: pointer;
  padding: 4px 0;
}

.comment-header {
  margin-bottom: 12px;
  
  .topic-title {
    color: var(--text-color);
    font-size: 16px;
    font-weight: 500;
    transition: color 0.3s;
    
    &:hover {
      color: var(--primary-color);
    }
  }
}

.audit-tag {
  margin-left: 8px;
  vertical-align: middle;
}

.comment-text {
  color: var(--text-color);
  font-size: 14px;
  line-height: 1.6;
  margin-bottom: 12px;
}

.comment-meta {
  display: flex;
  gap: 16px;
  color: var(--text-light);
  font-size: 14px;
}

.reject-reason {
  margin-top: 8px;
  padding: 8px 12px;
  background: var(--bg-color);
  border-radius: 4px;
  font-size: 13px;
  color: var(--el-color-danger);
  border-left: 3px solid var(--el-color-danger);
}

.comment-actions {
  display: flex;
  gap: 8px;
  flex-shrink: 0;
  align-self: center;
  height: 100%;
  align-items: center;
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

.delete-btn:hover {
  color: #ff4d4f;
}
</style> 