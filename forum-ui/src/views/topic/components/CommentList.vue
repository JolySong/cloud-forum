<template>
  <div class="comment-section">
    <!-- 评论输入框 -->
    <div class="comment-editor">
      <el-avatar :size="40" :src="userStore.currentUser?.avatar" />
      <div class="editor-wrapper">
        <el-input
          v-model="commentContent"
          type="textarea"
          :rows="2"
          placeholder="写下你的评论..."
          :disabled="!userStore.isAuthenticated"
          class="comment-input"
        />
        <div class="editor-footer">
          <span v-if="!userStore.isAuthenticated" class="login-tip">
            请<el-link type="primary" @click="handleLogin">登录</el-link>后发表评论
          </span>
          <el-button 
            type="primary" 
            :disabled="!commentContent.trim() || !userStore.isAuthenticated"
            @click="handleSubmit"
          >
            发表评论
          </el-button>
        </div>
      </div>
    </div>

    <!-- 评论列表 -->
    <div class="comment-list" v-loading="loading">
      <div class="comment-header">
        <h3>全部评论 {{ total }}</h3>
        <div class="filter-sort">
          <div 
            v-for="sort in sortOptions" 
            :key="sort.value"
            class="action-item"
            :class="{ active: currentSort === sort.value }"
            @click="handleSortClick(sort.value)"
          >
            {{ sort.label }}
          </div>
        </div>
      </div>
      <!-- 评论项 -->
      <div v-if="comments.length > 0" class="comment-items">
        <div v-for="comment in comments" :key="comment.id" class="comment-item">
          <el-avatar :size="40" :src="comment.userAvatar" />
          <div class="comment-content">
            <div class="comment-info">
              <span class="username">
                {{ comment.userName }}
                <el-tag v-if="isCommentOwner(comment)" size="small" effect="plain" class="owner-tag">
                  本人
                </el-tag>
                <el-tag v-if="comment.isAuthor" size="small" type="success" effect="plain" class="author-tag">
                  作者
                </el-tag>
              </span>
              <span class="time">{{ comment.createdAt }}</span>
            </div>
            <div class="comment-text">{{ comment.content }}</div>
            <div class="comment-actions">
              <el-button 
                text 
                :type="comment.isLiked ? 'primary' : 'default'"
                @click="handleLike(comment)"
              >
              <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24"><path fill="currentColor" fill-opacity="0" d="M7 11v9h-4v-9h4Z"><animate fill="freeze" attributeName="fill-opacity" begin="0.6s" dur="0.5s" values="0;1"/></path><path fill="none" stroke="currentColor" stroke-dasharray="80" stroke-dashoffset="80" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 11l5 -8l3 1l-1 6h7v3l-3 7h-11h-4v-9h4v9"><animate fill="freeze" attributeName="stroke-dashoffset" dur="0.6s" values="80;0"/></path></svg>
              {{ comment.likeCount > 0 ? comment.likeCount : '点赞' }}
              </el-button>
              <el-button text @click="handleReply(comment)">
                <el-icon><ChatLineRound /></el-icon>
                回复
              </el-button>
              <template v-if="isTopicAuthor">
                <el-button 
                  text 
                  :type="comment.isTop ? 'warning' : 'default'"
                  @click="handleTop(comment)"
                >
                  <el-icon><Top /></el-icon>
                  {{ comment.isTop ? '取消置顶' : '置顶' }}
                </el-button>
                <el-button 
                  text 
                  type="danger" 
                  @click="handleDelete(comment)"
                >
                  <el-icon><Delete /></el-icon>
                  删除
                </el-button>
              </template>
              <template v-else-if="isCommentOwner(comment)">
                <el-button 
                  text 
                  type="danger" 
                  @click="handleDelete(comment)"
                >
                  <el-icon><Delete /></el-icon>
                  删除
                </el-button>
              </template>
            </div>

            <!-- 回复列表 -->
            <div v-if="comment.children?.length > 0" class="reply-list">
              <div v-for="reply in comment.children" :key="reply.id" class="reply-item">
                <el-avatar :size="32" :src="reply.userAvatar" />
                <div class="reply-content">
                  <div class="reply-info">
                    <span class="username">
                      {{ reply.userName }}
                      <el-tag v-if="isCommentOwner(reply)" size="small" effect="plain" class="owner-tag">
                        本人
                      </el-tag>
                      <el-tag v-if="reply.isAuthor" size="small" type="success" effect="plain" class="author-tag">
                        作者
                      </el-tag>
                    </span>
                    <template v-if="reply.replyTo">
                      回复
                      <span class="username">{{ reply.replyToName }}</span>
                    </template>
                    <span class="time">{{ reply.createdAt }}</span>
                  </div>
                  <div class="reply-text">{{ reply.content }}</div>
                  <div class="reply-actions">
                    <el-button 
                      text 
                      :type="reply.isLiked ? 'primary' : 'default'"
                      @click="handleLike(reply)"
                    >
                      <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24"><path fill="currentColor" fill-opacity="0" d="M7 11v9h-4v-9h4Z"><animate fill="freeze" attributeName="fill-opacity" begin="0.6s" dur="0.5s" values="0;1"/></path><path fill="none" stroke="currentColor" stroke-dasharray="80" stroke-dashoffset="80" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 11l5 -8l3 1l-1 6h7v3l-3 7h-11h-4v-9h4v9"><animate fill="freeze" attributeName="stroke-dashoffset" dur="0.6s" values="80;0"/></path></svg>
                      {{ reply.likeCount > 0 ? reply.likeCount : '点赞' }}
                    </el-button>
                    <el-button text @click="handleReply(comment, reply)">
                      <el-icon><ChatLineRound /></el-icon>
                      回复
                    </el-button>
                    <template v-if="isTopicAuthor">
                      <el-button 
                        text 
                        type="danger" 
                        @click="handleDelete(reply)"
                      >
                        <el-icon><Delete /></el-icon>
                        删除
                      </el-button>
                    </template>
                    <template v-else-if="isCommentOwner(reply)">
                      <el-button 
                        text 
                        type="danger" 
                        @click="handleDelete(reply)"
                      >
                        <el-icon><Delete /></el-icon>
                        删除
                      </el-button>
                    </template>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <el-empty v-else description="暂无评论" />

      <!-- 分页 -->
      <div v-if="total > pageSize" class="comment-pagination">
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

    <!-- 添加回复对话框 -->
    <el-dialog
      v-model="replyDialogVisible"
      title="回复评论"
      width="500px"
      :close-on-click-modal="false"
      class="reply-dialog"
    >
      <div class="reply-dialog-content">
        <!-- 被回复的评论 -->
        <div class="reply-to-info" v-if="currentReply">
          <div class="reply-to-user">
            回复 {{ currentReply.userName }}：
          </div>
          <div class="reply-quote">{{ currentReply.content }}</div>
        </div>

        <!-- 回复输入框 -->
        <div class="form-item">
          <el-input
            v-model="replyContent"
            type="textarea"
            :rows="3"
            placeholder="写下你的回复..."
            maxlength="500"
            show-word-limit
          />
        </div>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="replyDialogVisible = false">取消</el-button>
          <el-button 
            type="primary" 
            @click="submitReply" 
            :loading="submitting"
            :disabled="!replyContent.trim()"
          >
            发表回复
          </el-button>
        </div>
      </template>
    </el-dialog>

    <login-dialog
      v-model="showLoginDialog"
      @confirm="handleLoginConfirm"
    />
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { ChatLineRound, Delete, Top } from '@element-plus/icons-vue'
import { useUserStore } from '@/store/modules/user'
import { getTopicComments, createComment, likeComment, unlikeComment, replyComment, deleteComment, topComment, untopComment } from '@/api'
import Pagination from '@/components/common/Pagination.vue'
import LoginDialog from '@/components/common/LoginDialog.vue'
import { getImageUrl } from '@/utils/image'


const showLoginDialog = ref(false)
// 修改登录检查函数
const checkLogin = () => {
  if (!userStore.isLogin) {
    showLoginDialog.value = true
    return false
  }
  return true
}

const props = defineProps({
  topicId: {
    type: [String, Number],
    required: true
  },
  initialComments: {
    type: Array,
    default: () => []
  },
  isTopicAuthor: {
    type: Boolean,
    default: false
  }
})

const router = useRouter()
const userStore = useUserStore()

// 评论列表数
const loading = ref(false)
const comments = ref([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)
const sort = ref('newest')
const commentContent = ref('')

// 排序选项
const sortOptions = [
  { label: '最新发布', value: 0 },
  { label: '最多点赞', value: 1 },
  { label: '最多回复', value: 2 }
]

// 当前排序
const currentSort = ref(0)

// 监听排序变化
watch(currentSort, () => {
  currentPage.value = 1
  fetchComments()
})

// 获取评论列表
const fetchComments = async () => {
  loading.value = true
  try {
    const res = await getTopicComments(props.topicId, {
      page: currentPage.value,
      size: pageSize.value,
      sort: currentSort.value
    })
    comments.value = res.data.records
    total.value = res.data.total
    comments.value.forEach(async (comment) => {
      comment.userAvatar = await getImageUrl(comment.userAvatar)
      comment.children.forEach(async (reply) => {
        reply.userAvatar = await getImageUrl(reply.userAvatar)
      })
    })

  } catch (error) {
    console.error('获取评论失败:', error)
  } finally {
    loading.value = false
  }
}

// 发表评论
const handleSubmit = async () => {
  if (!commentContent.value.trim()) return
  
  try {
    await createComment({
      topicId: props.topicId,
      content: commentContent.value
    })
    ElMessage.success('评论成功')
    commentContent.value = ''
    fetchComments()
  } catch (error) {
    ElMessage.error('发表失败')
  }
}

// 点赞论
const handleLike = async (comment) => {
  if (!checkLogin()) return

  try {
    if (comment.isLiked) {
      await unlikeComment(comment.id)
    } else {
      await likeComment(comment.id)
    }
    comment.isLiked = !comment.isLiked
    comment.likeCount += comment.isLiked ? 1 : -1
    ElMessage.success(comment.isLiked ? '点赞成功' : '取消点赞成功')
  } catch (error) {
    ElMessage.error('点赞失败')
  }
}

// 回复相关
const replyDialogVisible = ref(false)
const replyContent = ref('')
const currentComment = ref(null)
const currentReply = ref(null)
const submitting = ref(false)

// 回复评论
const handleReply = (comment, reply = null) => {
  if (!checkLogin()) return
  
  currentComment.value = comment
  currentReply.value = reply || comment
  replyDialogVisible.value = true
}

// 提交回复
const submitReply = async () => {
  if (!replyContent.value.trim()) {
    ElMessage.warning('请输入回复内容')
    return
  }

  submitting.value = true
  try {
    await replyComment(currentComment.value.id, {
      content: replyContent.value,
      topicId: props.topicId,
      parentId: currentReply.value.id,
      replyTo: currentReply.value.userId
    })
    
    ElMessage.success('回复成功')
    replyDialogVisible.value = false
    replyContent.value = ''
    currentComment.value = null
    currentReply.value = null
    
    // 刷新评论列表
    fetchComments()
  } catch (error) {
    ElMessage.error('回复失败')
  } finally {
    submitting.value = false
  }
}

// 监听对话框关闭
watch(replyDialogVisible, (val) => {
  if (!val) {
    replyContent.value = ''
    currentComment.value = null
    currentReply.value = null
  }
})

// 跳转登录
const handleLogin = () => {
  router.push('/login')
}

// 分页处理
const handleSizeChange = (val) => {
  pageSize.value = val
  currentPage.value = 1
  fetchComments()
}

const handleCurrentChange = (val) => {
  currentPage.value = val
  fetchComments()
}

// 排序点击处理
const handleSortClick = (value) => {
  currentSort.value = value
  fetchComments()
}

// 判断是否是评论作者
const isCommentOwner = (comment) => {
  return userStore.isAuthenticated && userStore.userInfo?.id === comment.userId
}

// 删除评论
const handleDelete = async (comment) => {
  try {
    await ElMessageBox.confirm('确定要删除这条评论吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await deleteComment(comment.id)
    ElMessage.success('删除成功')
    fetchComments()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

// 置顶/取消置顶评论
const handleTop = async (comment) => {
  try {
    if (comment.isTop) {
      await untopComment(comment.id)
    } else {
      await topComment(comment.id)
    }
    comment.isTop = !comment.isTop
    ElMessage.success(comment.isTop ? '置顶成功' : '取消置顶成功')
    // 刷新评论列表以更新顺序
    fetchComments()
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

// 添加处理登录确认的函数
const handleLoginConfirm = () => {
  router.push('/login')
}

// 初始化
fetchComments()
</script>

<style scoped>
.comment-section {
  background: var(--box-bg);
  border-radius: 8px;
  padding: 20px;
}

.comment-editor {
  display: flex;
  gap: 16px;
  margin-bottom: 24px;

  :deep(.el-textarea__inner) {
    background-color: var(--bg-color);
    border-color: var(--border-color);
    color: var(--text-color);
    transition: all 0.3s ease;

    &:hover {
      border-color: var(--primary-color);
    }

    &:focus {
      border-color: var(--primary-color);
      box-shadow: 0 0 0 1px var(--primary-color);
    }

    &::placeholder {
      color: var(--text-light);
    }
  }
}

.editor-wrapper {
  flex: 1;
  background: var(--box-bg);
  border-radius: 8px;
  padding: 16px;
  border: 1px solid var(--border-color);
  transition: all 0.3s ease;

  &:focus-within {
    border-color: var(--primary-color);
  }
}

.editor-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 12px;
}

.login-tip {
  color: var(--text-light);
  font-size: 14px;

  :deep(.el-link) {
    font-size: 14px;
    margin: 0 4px;
  }
}

.comment-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.comment-header h3 {
  margin: 0;
  font-size: 16px;
  color: var(--text-color);
}

.comment-items {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.comment-item {
  display: flex;
  gap: 16px;
}

.comment-content {
  flex: 1;
}

.comment-info {
  margin-bottom: 8px;
}

.username {
  color: var(--text-color);
  font-weight: 500;
  margin-right: 8px;
}

.time {
  color: var(--text-light);
  font-size: 12px;
}

.comment-text {
  color: var(--text-color);
  font-size: 14px;
  line-height: 1.6;
  margin-bottom: 8px;
}

.comment-actions {
  display: flex;
  gap: 16px;
}

.reply-list {
  /* background: var(--bg-color); */
  border-radius: 4px;
  padding: 12px;
  margin-top: 12px;
}

.reply-item {
  display: flex;
  gap: 12px;
  margin-bottom: 12px;

  &:last-child {
    margin-bottom: 0;
  }
}

.reply-content {
  flex: 1;
}

.reply-info {
  margin-bottom: 4px;
  font-size: 14px;
}

.reply-text {
  color: var(--text-color);
  font-size: 14px;
  line-height: 1.6;
  margin-bottom: 4px;
}

.reply-actions {
  display: flex;
  gap: 16px;
}

.comment-pagination {
  margin-top: 24px;
  display: flex;
  justify-content: center;
}

.filter-sort {
  display: flex;
  gap: 8px;
}

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

.owner-tag {
  margin-left: 8px;
  font-size: 12px;
  padding: 0 6px;
  height: 20px;
  line-height: 18px;
}

.author-tag {
  margin-left: 8px;
  font-size: 12px;
  padding: 0 6px;
  height: 20px;
  line-height: 18px;
}

.reply-quote {
  margin: 8px 0;
  padding: 8px 12px;
  background: var(--button-hover-bg);
  border-radius: 4px;
  font-size: 13px;
  color: var(--text-light);
  border-left: 3px solid var(--primary-color);
}

/* 添加置顶评论样式 */
.comment-item.is-top {
  background-color: var(--button-hover-bg);
  border-left: 3px solid var(--primary-color);
}

/* 回复对话框样式 */
:deep(.reply-dialog) {
  background: var(--box-bg);
  border-radius: 8px;
  
  .el-dialog__header {
    margin: 0;
    padding: 20px 24px;
    border-bottom: 1px solid var(--border-color);
    
    .el-dialog__title {
      font-size: 18px;
      font-weight: 500;
      color: var(--text-color);
    }
  }

  .el-dialog__body {
    padding: 24px;
  }

  .el-dialog__footer {
    padding: 16px 24px;
    border-top: 1px solid var(--border-color);
  }
}

.reply-dialog-content {
  .reply-to-info {
    margin-bottom: 16px;
    
    .reply-to-user {
      font-size: 14px;
      color: var(--text-color);
      margin-bottom: 8px;
    }
  }

  .reply-quote {
    padding: 12px 16px;
    background: var(--bg-color);
    border-radius: 4px;
    font-size: 14px;
    color: var(--text-light);
    border-left: 3px solid var(--primary-color);
    margin-bottom: 16px;
  }

  .form-item {
    :deep(.el-textarea__inner) {
      background-color: var(--bg-color);
      border-color: var(--border-color);
      color: var(--text-color);
      transition: all 0.3s ease;

      &:hover {
        border-color: var(--primary-color);
      }

      &:focus {
        border-color: var(--primary-color);
        box-shadow: 0 0 0 1px var(--primary-color);
      }

      &::placeholder {
        color: var(--text-light);
      }
    }

    :deep(.el-input__count) {
      background: transparent;
      color: var(--text-light);
    }
  }
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}
</style> 