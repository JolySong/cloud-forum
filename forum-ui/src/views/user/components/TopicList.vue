<template>
  <div class="topic-list">
    <template v-if="topics.length > 0">
      <div class="topic-items">
        <div 
          v-for="topic in topics" 
          :key="topic.id" 
          class="topic-item"
          :class="{
            'is-pending': topic.audit === 0,
            'is-rejected': topic.audit === 2
          }"
        >
          <div class="topic-content" @click="handleTopicClick(topic)">
            <h3 class="topic-title">
              {{ topic.title }}
              <el-tag 
                v-if="topic.audit !== 1" 
                :type="getAuditTagType(topic.audit)"
                size="small"
                class="audit-tag"
              >
                {{ getAuditText(topic.audit) }}
              </el-tag>
            </h3>
            <div class="topic-meta">
              <span>{{ topic.createdAt }}</span>
              <span>{{ topic.viewCount }} 浏览</span>
              <span>{{ topic.commentCount }} 评论</span>
              <span>{{ topic.likeCount }} 点赞</span>
            </div>
            <div v-if="topic.audit === 2" class="reject-reason">
              驳回原因：{{ topic.auditReason || '内容不符合规范' }}
            </div>
          </div>
          <div class="topic-actions">
            <template v-if="type === 'topics'">
              <el-button 
                type="text" 
                class="action-btn edit-btn"
                
                @click="handleEdit(topic.id)"
              >
                <el-icon><Edit /></el-icon>
                编辑
              </el-button>
              <el-button 
                type="text" 
                class="action-btn delete-btn"
                @click.stop="$emit('delete', topic)"
              >
                <el-icon><Delete /></el-icon>
                删除
              </el-button>
            </template>
            <template v-else-if="type === 'favorites'">
              <el-button 
                type="text" 
                class="action-btn cancel-favorite"
                @click.stop="$emit('unfavorite', topic)"
              >
                <el-icon><Star /></el-icon>
                取消收藏
              </el-button>
            </template>
          </div>
        </div>
      </div>
    </template>
    <el-empty v-else description="暂无数据" />
    
    <div v-if="loading" class="loading">
      <el-skeleton :rows="3" animated />
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { Star, Edit, Delete } from '@element-plus/icons-vue'

const props = defineProps({
  topics: {
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
  },
  type: {
    type: String,
    default: 'topics'
  }
})

const emit = defineEmits(['load-more', 'unfavorite', 'edit', 'delete'])
const router = useRouter()

const handleTopicClick = (topic) => {
  router.push(`/topic/detail/${topic.id}`)
}

const handleEdit = (topicId) => {
  router.push(`/topic/edit/${topicId}`)
}

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
.topic-list {
  width: 100%;
  
  .topic-items {
    display: flex;
    flex-direction: column;
    gap: 16px;
    width: 100%;
  }
}

.topic-item {
  width: 100%;
  padding: 16px;
  background: var(--bg-color);
  border-radius: 8px;
  transition: all 0.3s;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 16px;
  box-sizing: border-box;

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

.topic-content {
  flex: 1;
  cursor: pointer;
  
  &:hover .topic-title {
    color: var(--primary-color);
  }
}

.topic-title {
  color: var(--text-color);
  font-size: 16px;
  margin: 0 0 12px;
  transition: color 0.3s;
}

.topic-meta {
  display: flex;
  gap: 16px;
  color: var(--text-light);
  font-size: 14px;
}

.topic-actions {
  display: flex;
  gap: 8px;
  flex-shrink: 0;
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

.edit-btn:hover {
  color: var(--primary-color);
}

.delete-btn:hover {
  color: #ff4d4f;
}

.cancel-favorite:hover {
  color: #ff4d4f;
}

.loading {
  margin-top: 16px;
}

.audit-tag {
  margin-left: 8px;
  vertical-align: middle;
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
</style> 