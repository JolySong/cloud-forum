<template>
  <div class="user-container">
    <!-- 个人信息卡片 -->
    <div class="user-profile-card">
      <div class="profile-header">
        <div class="avatar-wrapper">
          <el-avatar :size="80" :src="userInfo?.avatar" />
          <div class="avatar-edit" @click="handleAvatarEdit">
            <el-icon><Camera /></el-icon>
            <span>更换头像</span>
          </div>
        </div>
        <div class="profile-info">
          <h2 class="username">{{ userInfo?.nickname }}</h2>
          <div class="user-bio">{{ userInfo?.bio || '这个人很懒，什么都没写~' }}</div>
          <div class="user-meta">
            <div class="meta-item">
              <el-icon><Calendar /></el-icon>
              <span>加入于: {{ formatDate(userInfo?.createdAt) }}</span>
            </div>
            <div class="meta-item">
              <el-icon><Message /></el-icon>
              <span>邮箱: {{ userInfo?.email }}</span>
            </div>
          </div>
        </div>
      </div>
      <div class="user-stats">
            <div class="stat-item">
              <div class="stat-value">{{ userInfo?.topicCount || 0 }}</div>
              <div class="stat-label">话题</div>
            </div>
            <div class="stat-item">
              <div class="stat-value">{{ userInfo?.likeCount || 0 }}</div>
              <div class="stat-label">获赞</div>
            </div>
            <div class="stat-item">
              <div class="stat-value">{{ userInfo?.followCount || 0 }}</div>
              <div class="stat-label">关注</div>
            </div>
            <div class="stat-item">
              <div class="stat-value">{{ userInfo?.followerCount || 0 }}</div>
              <div class="stat-label">粉丝</div>
            </div>
          </div>
      <div class="edit-button-wrapper">
        <el-button 
          class="edit-btn" 
          type="primary" 
          @click="handleEditProfile"
        >
          <el-icon><Edit /></el-icon>
          编辑资料
        </el-button>
      </div>
    </div>
    <!-- 内容区域 -->
    <div class="content-section">
      <!-- 导航标签和搜索框容器 -->
      <div class="tab-header">
        <el-tabs v-model="activeTab" @tab-change="handleTabChange">
          <el-tab-pane label="我的话题" name="topics">
            <div class="tab-content">
              <topic-list 
                v-if="activeTab === 'topics'"
                :loading="loading.topics"
                :topics="topics"
                :total="totals.topics"
                :current-page="page.topics.current"
                :page-size="page.topics.size"
                type="topics"
                @page-change="handleCurrentChange"
                @size-change="handleSizeChange"
                @edit="handleEdit"
                @delete="handleDelete"
              />
            </div>
          </el-tab-pane>

          <el-tab-pane label="我的收藏" name="favorites">
            <div class="tab-content">
              <topic-list 
                v-if="activeTab === 'favorites'"
                :loading="loading.favorites"
                :topics="favorites"
                :has-more="hasMore.favorites"
                type="favorites"
                @load-more="loadMore"
                @unfavorite="handleUnfavorite"
              />
            </div>
          </el-tab-pane>

          <el-tab-pane label="我的回复" name="comments">
            <div class="tab-content">
              <comment-list 
                v-if="activeTab === 'comments'"
                :loading="loading.comments"
                :comments="comments"
                :total="totals.comments"
                :current-page="page.comments.current"
                :page-size="page.comments.size"
                @page-change="handleCurrentChange"
                @size-change="handleSizeChange"
                @delete="handleCommentDelete"
              />
            </div>
          </el-tab-pane>

          <el-tab-pane label="我的点赞" name="likes">
            <div class="tab-content">
              <like-list 
                v-if="activeTab === 'likes'"
                :loading="loading.likes"
                :likes="likes"
                :has-more="hasMore.likes"
                @load-more="loadMore"
                @unlike="handleUnlike"
              />
            </div>
          </el-tab-pane>
        </el-tabs>

        <!-- 搜索框 -->
        <div class="search-bar">
          <el-input
            v-model="searchText"
            :placeholder="getSearchPlaceholder"
            clearable
            @keyup.enter="handleSearch"
            @clear="handleSearch"
          >
            <template #suffix>
              <el-icon class="search-icon" @click="handleSearch"><Search /></el-icon>
            </template>
          </el-input>
        </div>
      </div>

      <!-- 分页器 -->
      <div class="pagination-wrapper">
        <Pagination
          v-model:current-page="page[activeTab].current"
          v-model:page-size="page[activeTab].size"
          :total="totals[activeTab]"
          :page-sizes="[5, 10, 20, 30, 50]"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </div>
  </div>
  <!-- 编辑资料对话框 -->
  <div class="modal-overlay" v-if="showEditDialog" @click="handleModalClick">
    <div class="modal-content" @click.stop>
      <div class="modal-header">
        <h3>编辑个人资料</h3>
        <button class="close-btn" @click="showEditDialog = false">
          <el-icon><Close /></el-icon>
        </button>
      </div>

      <div class="modal-body">
        <form class="edit-form" @submit.prevent="handleSaveProfile">
          <div class="form-item">
            <label>用户名</label>
            <div class="input-wrapper">
              <input 
                type="text" 
                readonly="readonly"
                v-model="editForm.username" 
                maxlength="20"
                :class="{ error: formErrors.username }"
              >
            </div>
          </div>
          <div class="form-item">
            <label>邮箱</label>
            <div class="input-wrapper">
              <input 
                type="text" 
                readonly="readonly"
                v-model="editForm.email" 
                maxlength="20"
                :class="{ error: formErrors.email }"
              >
            </div>
          </div>

          <div class="form-item">
            <label>昵称</label>
            <div class="input-wrapper">
              <input 
                type="text" 
                v-model="editForm.nickname" 
                maxlength="20"
                :class="{ error: formErrors.nickname }"
              >
              <span class="word-count">{{ editForm.nickname.length }}/20</span>
            </div>
            <span class="error-message" v-if="formErrors.nickname">{{ formErrors.nickname }}</span>
          </div>

          <div class="form-item">
            <label>简介</label>
            <div class="input-wrapper">
              <textarea 
                v-model="editForm.bio" 
                rows="3" 
                maxlength="200"
                :class="{ error: formErrors.bio }"
              ></textarea>
              <span class="word-count">{{ editForm.bio.length }}/200</span>
            </div>
            <span class="error-message" v-if="formErrors.bio">{{ formErrors.bio }}</span>
          </div>
        </form>
      </div>

      <div class="modal-footer">
        <button class="cancel-btn" @click="showEditDialog = false">取消</button>
        <button 
          class="submit-btn" 
          :disabled="saving" 
          @click="handleSaveProfile"
        >
          {{ saving ? '保存中...' : '保存' }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useUserStore } from '@/store/modules/user'
import TopicList from './components/TopicList.vue'
import CommentList from './components/CommentList.vue'
import LikeList from './components/LikeList.vue'
import { getUserTopics, getUserFavorites, getUserComments, getUserLikes } from '@/api'
import { Search, Camera, Edit, Calendar } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { updateUserInfo, getUserStat } from '@/api'
import Pagination from '@/components/common/Pagination.vue'
import { unfavoriteTopic } from '@/api'
import { deleteTopic } from '@/api'
import { ElMessageBox } from 'element-plus'
import { unlikeTopic } from '@/api'
import { unlikeComment, uploadAvatar } from '@/api'
import { getImageUrl } from '@/utils/image'
import { deleteComment } from '@/api'

const userStore = useUserStore()
const userInfo = ref({})


const activeTab = ref('topics')
const loading = ref({
  topics: false,
  favorites: false,
  comments: false,
  likes: false
})

// 数据列表
const topics = ref([])
const favorites = ref([])
const comments = ref([])
const likes = ref([])

// 分页参数
const page = ref({
  topics: { current: 1, size: 5 },
  favorites: { current: 1, size: 5 },
  comments: { current: 1, size: 5 },
  likes: { current: 1, size: 5 }
})

const searchText = ref('')
const totals = ref({
  topics: 0,
  favorites: 0,
  comments: 0,
  likes: 0
})
const hasMore = ref({
  topics: false,
  favorites: false,
  comments: false,
  likes: false
})

// 加载数据
const loadData = async (type, reset = false) => {
  
  if (reset) {
    // page.value[type].current = 1
    getList(type).length = 0
  }

  loading.value[type] = true
  try {
    const params = {
      page: page.value[type].current,
      size: page.value[type].size,
      keyword: searchText.value
    }
    console.log('params', params)

    let res
    switch (type) {
      case 'topics':
        res = await getUserTopics(params)
        if (reset) {
          topics.value = res.data.records
        } else {
          topics.value = [...topics.value, ...res.data.records]
        }
        totals.value.topics = res.data.total
        hasMore.value.topics = topics.value.length < res.data.total
        break
      case 'favorites':
        res = await getUserFavorites(params)
        if (reset) {
          favorites.value = res.data.records
        } else {
          favorites.value = [...favorites.value, ...res.data.records]
        }
        totals.value.favorites = res.data.total
        hasMore.value.favorites = favorites.value.length < res.data.total
        break
      case 'comments':
        res = await getUserComments(params)
        if (reset) {
          comments.value = res.data.records
        } else {
          comments.value = [...comments.value, ...res.data.records]
        }
        totals.value.comments = res.data.total
        hasMore.value.comments = comments.value.length < res.data.total
        break
      case 'likes':
        res = await getUserLikes(params)
        if (reset) {
          likes.value = res.data.records
        } else {
          likes.value = [...likes.value, ...res.data.records]
        }
        totals.value.likes = res.data.total
        hasMore.value.likes = likes.value.length < res.data.total
        break
    }
  } catch (error) {
    console.error(`加载${type}失败:`, error)
  } finally {
    loading.value[type] = false
  }
}

// 切换标签
const handleTabChange = (tab) => {
  const type = tab
  if (getList(type).length === 0) {
    loadData(type)
  }
}

// 加载更多
const loadMore = () => {
  page.value[activeTab.value].current++
  loadData(activeTab.value)
}

// 获取当前列表
const getList = (type) => {
  switch (type) {
    case 'topics':
      return topics.value
    case 'favorites':
      return favorites.value
    case 'comments':
      return comments.value
    case 'likes':
      return likes.value
    default:
      return []
  }
}

// 搜索
const handleSearch = () => {
  loadData(activeTab.value, true)
}

// 切换每页显示数量
const handleSizeChange = (size) => {
  page.value[activeTab.value].size = size
  loadData(activeTab.value, true)
}

// 切换页码
const handleCurrentChange = (current) => {
  page.value[activeTab.value].current = current
  loadData(activeTab.value, true)
}

// 获取用户信息
const fetchUserInfo = async (userId) => {
  const res = await getUserStat(userId)
  userInfo.value = res.data
  userInfo.value.avatar = await getImageUrl(userInfo.value.avatar)
}

// 编辑相关
const showEditDialog = ref(false)
const saving = ref(false)
const editForm = ref({
  username: '',
  nickname: '',
  bio: '',
})

// 打开编辑对话框
const handleEditProfile = () => {
  console.log('userInfo', userInfo.value)
  editForm.value = {
    username: userInfo.value?.username || '',
    nickname: userInfo.value?.nickname || '',
    bio: userInfo.value?.bio || '',
    email: userInfo.value?.email || '',
  }
  showEditDialog.value = true
}

// 保存资料
const handleSaveProfile = async () => {
  // 表单验证
  if (!validateForm()) return

  saving.value = true
  try {
    const params = {
      nickname: editForm.value.nickname,
      bio: editForm.value.bio,
      background: editForm.value.background
    }
    await updateUserInfo(params)
    ElMessage.success('保存成功')
    showEditDialog.value = false
    // 重新获取用户信息
    await fetchUserInfo(userStore.userInfo.id)
  } catch (error) {
    console.error('保存失败:', error)
    ElMessage.error(error.response?.data?.message || '保存失败')
  } finally {
    saving.value = false
  }
}

// 更换头像
const handleAvatarEdit = async () => {
  const fileInput = document.createElement('input')
  fileInput.type = 'file'
  fileInput.accept = 'image/*'
  fileInput.click()

  // 监听文件变化
  fileInput.addEventListener('change', async () => {
    console.log('fileInput', fileInput)
    const file = fileInput.files[0]
    if (!file) {
      ElMessage.error('请选择一个文件')
      return
    }
    console.log('file', file)

    const formData = new FormData()
    formData.append('file', file)
    const res = await uploadAvatar(formData)
    if (res.status) {
      ElMessage.success('头像上传成功')
    } else {
      ElMessage.error('头像上传失败')
    }
  })
}

// 格式化日期
const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleDateString()
}


// 表单错误信息
const formErrors = ref({
  nickname: '',
  bio: '',
  location: '',
  website: ''
})

// 处理模态框点击
const handleModalClick = (e) => {
  if (e.target.classList.contains('modal-overlay')) {
    showEditDialog.value = false
  }
}

// 表单验证
const validateForm = () => {
  let isValid = true
  formErrors.value = {}

  if (!editForm.value.nickname.trim()) {
    formErrors.value.nickname = '昵称不能为空'
    isValid = false
  }

  if (editForm.value.bio && editForm.value.bio.length > 200) {
    formErrors.value.bio = '简介不能超过200个字符'
    isValid = false
  }

  return isValid
}

// 根据当前标签页获取搜索框占位符
const getSearchPlaceholder = computed(() => {
  switch (activeTab.value) {
    case 'topics':
    case 'favorites':
    case 'likes':
      return '搜索话题...'
    case 'comments':
      return '搜索回复内容...'
    default:
      return '搜索点赞话题或评论...'
  }
})

// 取消收藏
const handleUnfavorite = async (topic) => {
  try {
    await unfavoriteTopic(topic.id)
    ElMessage.success('取消收藏成功')
    // 重新加载收藏列表
    loadData('favorites', true)
  } catch (error) {
    console.error('取消收藏失败:', error)
    ElMessage.error('取消收藏失败')
  }
}

// 编辑话题
const handleEdit = (topic) => {
  router.push(`/topic/edit/${topic.id}`)
}

// 删除话题
const handleDelete = async (topic) => {
  try {
    await ElMessageBox.confirm('确定要删除这个话题吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await deleteTopic(topic.id)
    ElMessage.success('删除成功')
    // 重新加载话题列表
    loadData('topics', true)
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
      ElMessage.error('删除失败')
    }
  }
}

// 取消点赞
const handleUnlike = async (like) => {
  try {
    console.log('like', like)
    if (like.targetType === "0") {
      await unlikeTopic(like.targetId)
    } else {
      await unlikeComment(like.targetId)
    }
    ElMessage.success('取消点赞成功')
    // 重新加载点赞列表
    loadData('likes', true)
  } catch (error) {
    console.error('取消点赞失败:', error)
    ElMessage.error('取消点赞失败')
  }
}

// 处理评论删除
const handleCommentDelete = async (comment) => {
  try {
    await deleteComment(comment.id)
    ElMessage.success('删除成功')
    // 重新加载评论列表
    loadData('comments', true)
  } catch (error) {
    console.error('删除评论失败:', error)
    ElMessage.error('删除失败')
  }
}

onMounted(() => {
  loadData('topics')
  fetchUserInfo(userStore.userInfo.id)
})
</script>

<style scoped>
.user-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  min-height: calc(100vh - 64px - 60px - 40px);
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
}

.user-profile-card {
  background: var(--box-bg);
  border-radius: 12px;
  padding: 24px;
  margin-bottom: 20px;
  position: relative;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);

  &::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 160px;
    /* background-image: linear-gradient(to bottom right, var(--primary-color), #6366f1); */
    opacity: 0.8;
    z-index: 0;
  }

  .profile-header {
    position: relative;
    z-index: 1;
    display: flex;
    gap: 24px;
    align-items: flex-start;
    padding-top: 16px;
  }

  .avatar-wrapper {
    position: relative;
    flex-shrink: 0;

    :deep(.el-avatar) {
      width: 100px;
      height: 100px;
      border: 4px solid var(--box-bg);
      box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    }

    .avatar-edit {
      position: absolute;
      bottom: 0;
      left: 0;
      right: 0;
      background: rgba(0, 0, 0, 0.6);
      color: white;
      padding: 6px;
      font-size: 12px;
      text-align: center;
      opacity: 0;
      transition: opacity 0.3s;
      border-bottom-left-radius: 4px;
      border-bottom-right-radius: 4px;
      backdrop-filter: blur(4px);

      .el-icon {
        margin-right: 4px;
        font-size: 14px;
      }
    }

    &:hover .avatar-edit {
      opacity: 1;
    }
  }

  .profile-info {
    flex: 1;
    position: relative;
    z-index: 2;

    .username {
      font-size: 28px;
      font-weight: 600;
      margin: 0 0 8px;
      color: var(--text-color);
    }

    .user-bio {
      font-size: 14px;
      margin: 8px 0;
      line-height: 1.6;
      color: var(--text-light);
    }

    .user-meta {
      display: flex;
      flex-wrap: wrap;
      gap: 16px;
      margin: 16px 0;

      .meta-item {
        display: flex;
        align-items: center;
        gap: 6px;
        font-size: 14px;
        color: var(--text-light);

        .el-icon {
          font-size: 16px;
          color: var(--text-light);
        }
      }
    }
  }

  &[style*="--profile-bg-image"] {
    .profile-info {
      .username {
        color: white;
      }

      .user-bio {
        color: rgba(255, 255, 255, 0.9);
      }

      .user-meta {
        .meta-item {
          color: rgba(255, 255, 255, 0.85);

          .el-icon {
            color: rgba(255, 255, 255, 0.85);
          }
        }
      }
    }
  }

  .user-stats {
    display: flex;
    justify-content: center;
    gap: 32px;
    margin-top: 16px;
    padding: 16px 0;
    border-top: 1px solid var(--border-color);

    .stat-item {
      text-align: center;
      cursor: pointer;
      transition: all 0.3s;
      padding: 8px 16px;
      border-radius: 8px;
      min-width: 80px;

      &:hover {
        background: var(--button-hover-bg);
        transform: translateY(-2px);
      }

      .stat-value {
        font-size: 20px;
        font-weight: 600;
        color: var(--text-color);
        margin-bottom: 4px;
      }

      .stat-label {
        font-size: 14px;
        color: var(--text-light);
      }
    }
  }

  .edit-button-wrapper {
    position: absolute;
    right: 24px;
    top: 24px;
    z-index: 2;
  }

  .edit-btn {
    white-space: nowrap;
    display: flex;
    align-items: center;
    gap: 6px;
    padding: 8px 16px;
    border-radius: 4px;
    transition: all 0.3s;
    background: transparent;
    border: 1px solid var(--border-color);
    color: var(--text-color);
    font-size: 14px;
    
    .el-icon {
      font-size: 16px;
      color: var(--text-color);
      transition: color 0.3s;
    }

    &:hover {
      color: var(--primary-color);
      border-color: var(--primary-color);
      transform: translateY(-2px);
      background: var(--button-hover-bg);

      .el-icon {
        color: var(--primary-color);
      }
    }
  }

  &[style*="--profile-bg-image"] {
    .edit-btn {
      color: white;
      border-color: rgba(255, 255, 255, 0.3);
      background: rgba(255, 255, 255, 0.1);
      backdrop-filter: blur(4px);

      .el-icon {
        color: white;
      }

      &:hover {
        color: white;
        border-color: rgba(255, 255, 255, 0.8);
        background: rgba(255, 255, 255, 0.2);

        .el-icon {
          color: white;
        }
      }
    }
  }
}

.content-section {
  background: var(--box-bg);
  border-radius: 8px;
  padding: 24px;
  display: flex;
  flex-direction: column;
  flex: 1;
  height: calc(100vh - 64px - 60px - 40px - 320px);
  min-height: 400px;
  overflow: hidden;
}

.tab-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 20px;
  border-bottom: 1px solid var(--border-color);
  position: relative;
}

:deep(.el-tabs) {
  flex: 1;
}

:deep(.el-tabs__header) {
  margin: 0;
  border: none;
}

:deep(.el-tabs__nav-wrap::after) {
  display: none;
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

:deep(.el-tabs__active-bar) {
  background-color: var(--primary-color);
}

:deep(.el-tabs__nav-wrap::after) {
  background-color: var(--border-color);
}

.tab-content {
  flex: 1;
  padding-top: 20px;
  height: calc(100% - 60px - 76px);
  overflow-y: auto;
  width: 100%;
  box-sizing: border-box;

  &::-webkit-scrollbar {
    width: 4px;
  }

  &::-webkit-scrollbar-track {
    background: transparent;
  }

  &::-webkit-scrollbar-thumb {
    background: var(--border-color);
    border-radius: 2px;
  }

  &::-webkit-scrollbar-thumb:hover {
    background: var(--text-light);
  }
}

.search-bar {
  width: 240px;
  padding-bottom: 12px;
  margin-left: 20px;
  flex-shrink: 0;
  position: absolute;
  right: 0;
  top: 0;
  z-index: 1;

  :deep(.el-input__wrapper) {
    background-color: var(--bg-color);
    box-shadow: 0 0 0 1px var(--border-color) inset;
    border-radius: 20px;
    transition: all 0.3s ease;

    &:hover {
      box-shadow: 0 0 0 1px var(--primary-color) inset;
    }

    &.is-focus {
      box-shadow: 0 0 0 1px var(--primary-color) inset !important;
      background-color: var(--box-bg);
      width: 300px;
    }
  }

  :deep(.el-input__inner) {
    height: 36px;
    font-size: 14px;
    transition: width 0.3s;

    &::placeholder {
      color: var(--text-light);
    }
  }

  .search-icon {
    font-size: 16px;
    color: var(--text-light);
    cursor: pointer;
    transition: color 0.3s;

    &:hover {
      color: var(--primary-color);
    }
  }
}

.pagination-wrapper {
  padding-top: 20px;
  margin-top: auto;
  border-top: 1px solid var(--border-color);
  display: flex;
  justify-content: center;
  flex-shrink: 0;
  height: 76px;
  box-sizing: border-box;
}

/* 移动端适配 */
@media (max-width: 768px) {
  .user-container {
    padding: 16px;
    min-height: calc(100vh - 64px - 50px - 32px);
  }

  .content-section {
    padding: 16px;
    height: calc(100vh - 64px - 50px - 32px - 280px);
    min-height: 300px;
  }

  .tab-content {
    height: calc(100% - 50px - 66px);
  }

  .pagination-wrapper {
    height: 66px;
  }
}

.avatar-wrapper {
  position: relative;
  cursor: pointer;

  &:hover .avatar-edit {
    opacity: 1;
  }

  .avatar-edit {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    background: rgba(0, 0, 0, 0.6);
    color: white;
    padding: 4px;
    font-size: 12px;
    text-align: center;
    opacity: 0;
    transition: opacity 0.3s;
    border-bottom-left-radius: 4px;
    border-bottom-right-radius: 4px;

    .el-icon {
      margin-right: 4px;
      font-size: 14px;
    }
  }
}

.edit-button-wrapper {
  position: absolute;
  right: 24px;
  bottom: 24px;
}

.edit-btn {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 8px 16px;
  border-radius: 20px;
  transition: all 0.3s;
  
  .el-icon {
    font-size: 16px;
  }

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(var(--primary-color-rgb), 0.2);
  }
}

.user-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
  margin-bottom: 16px;

  .meta-item {
    display: flex;
    align-items: center;
    gap: 6px;
    color: var(--text-light);
    font-size: 14px;

    .el-icon {
      font-size: 16px;
    }

    a {
      color: var(--primary-color);
      text-decoration: none;
      
      &:hover {
        text-decoration: underline;
      }
    }
  }
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  backdrop-filter: blur(4px);
  animation: fadeIn 0.3s ease;
}

.modal-content {
  width: 600px;
  max-width: calc(100vw - 48px);
  background: var(--box-bg);
  border-radius: 12px;
  box-shadow: 0 4px 24px rgba(0, 0, 0, 0.2);
  animation: slideIn 0.3s ease;
}

.modal-header {
  padding: 24px 32px;
  border-bottom: 1px solid var(--border-color);
  display: flex;
  justify-content: space-between;
  align-items: center;

  h3 {
    margin: 0;
    color: var(--text-color);
    font-size: 20px;
    font-weight: 500;
  }
}

.close-btn {
  background: transparent;
  border: none;
  color: var(--text-light);
  cursor: pointer;
  padding: 4px;
  border-radius: 4px;
  transition: all 0.3s;

  &:hover {
    background: var(--button-hover-bg);
    color: var(--text-color);
  }
}

.modal-body {
  padding: 32px;
  max-width: 100%;
  box-sizing: border-box;
}

.edit-form {
  display: flex;
  flex-direction: column;
  gap: 24px;
  width: 100%;
  max-width: 100%;
}

.form-item {
  display: flex;
  flex-direction: column;
  gap: 8px;
  width: 100%;
  label {
    color: var(--text-color);
    font-size: 14px;
    font-weight: 500;
    margin-bottom: 4px;
  }
}

.input-wrapper {
  position: relative;

  input,textarea {
    width: 100%;
    box-sizing: border-box;
    padding: 12px 16px;
    padding-right: 60px;
    border: 1px solid var(--border-color);
    border-radius: 8px;
    background: var(--bg-color);
    color: var(--text-color);
    font-size: 14px;
    transition: all 0.3s;
    resize: none;

    &[readonly] {
      background-color: var(--button-hover-bg);
      cursor: not-allowed;
      color: var(--text-light);
      padding-right: 16px;
    }

    &:hover:not([readonly]) {
      border-color: var(--primary-color);
    }

    &:focus:not([readonly]) {
      outline: none;
      border-color: var(--primary-color);
      box-shadow: 0 0 0 2px rgba(var(--primary-color-rgb), 0.1);
    }
  }

  textarea {
    min-height: 100px;
  }

  .word-count {
    position: absolute;
    right: 12px;
    bottom: 12px;
    color: var(--text-light);
    font-size: 12px;
    background: var(--bg-color);
    padding: 2px 6px;
    border-radius: 4px;
  }
}

.error-message {
  color: var(--el-color-danger);
  font-size: 12px;
}

.modal-footer {
  padding: 24px 32px;
  border-top: 1px solid var(--border-color);
  display: flex;
  justify-content: flex-end;
  gap: 16px;
}

.cancel-btn, .submit-btn {
  padding: 10px 24px;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s;
}

.cancel-btn {
  background: var(--bg-color);
  border: 1px solid var(--border-color);
  color: var(--text-color);

  &:hover {
    border-color: var(--primary-color);
    color: var(--primary-color);
    transform: translateY(-1px);
  }
}

.submit-btn {
  background: var(--primary-color);
  border: 1px solid var(--primary-color);
  color: white;

  &:hover:not(:disabled) {
    opacity: 0.9;
    transform: translateY(-1px);
  }

  &:disabled {
    opacity: 0.5;
    cursor: not-allowed;
    transform: none;
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

@keyframes slideIn {
  from {
    transform: translateY(-20px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

.topic-detail {
  .topic-title {
    font-size: 24px;
    color: var(--text-color);
    margin: 0 0 16px;
  }

  .topic-meta {
    display: flex;
    gap: 16px;
    color: var(--text-light);
    font-size: 14px;
    margin-bottom: 24px;
  }

  .topic-content {
    color: var(--text-color);
    font-size: 16px;
    line-height: 1.8;
  }
}

.delete-confirm {
  p {
    color: var(--text-color);
    font-size: 16px;
    margin: 0 0 24px;
  }

  .dialog-footer {
    display: flex;
    justify-content: flex-end;
    gap: 12px;
  }
}

.topic-actions {
  display: flex;
  gap: 16px;

  .el-button {
    display: flex;
    align-items: center;
    gap: 4px;
    padding: 4px 8px;
    
    &:hover {
      color: var(--primary-color);
    }
  }
}

.action-btn {
  display: flex;
  align-items: center;
  gap: 4px;
  padding: 8px 16px;
  border-radius: 4px;
  transition: all 0.3s;
  background: var(--primary-color);
  border: none;
  color: white;
  font-size: 14px;

  .el-icon {
    font-size: 16px;
  }

  &:hover {
    opacity: 0.9;
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(var(--primary-color-rgb), 0.2);
  }

  &.edit-btn {
    background: var(--primary-color);
  }

  &.delete-btn {
    background: var(--el-color-danger);
  }
}
</style> 