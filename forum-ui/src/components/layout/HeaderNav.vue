<template>
  <header class="header">
    <div class="header-content">
      <!-- Logo -->
      <div class="logo">
        <router-link to="/home" class="logo-text">Forum</router-link>
      </div>

      <!-- 导航菜单 -->
      <nav class="nav-menu">
        <router-link to="/home" class="nav-item">首页</router-link>
        <router-link to="/topic" class="nav-item">话题</router-link>
      </nav>

      <!-- 右侧功能区 -->
      <div class="right-section">
        <!-- 搜索框 -->
        <div class="search-box" v-show="!hideSearch">
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

        <!-- 主题切换按钮 -->
        <el-button
          class="theme-toggle"
          :icon="isDark ? Sunny : Moon"
          circle
          @click="toggleTheme"
        />

        <!-- 消息中心按钮 -->
        <div 
          v-if="userStore.isLogin" 
          class="message-btn"
          @click="handleMessageClick"
        >
          <el-badge 
            :value="unreadCount" 
            :hidden="unreadCount === 0"
            :max="99"
          >
            <el-icon><Bell /></el-icon>
          </el-badge>
        </div>

        <!-- 用户菜单 -->
        <div class="user-menu" v-if="userStore.isLogin">
          <div class="dropdown" @mouseenter="showDropdown = true" @mouseleave="showDropdown = false">
            <div class="user-info">
              <el-avatar :size="32" :src="userStore.userInfo?.avatar" @error="handleAvatarError" />
              <span class="username">{{ userStore.userInfo?.nickname }}</span>
              <i class="arrow" :class="{ 'arrow-up': showDropdown }"></i>
            </div>
            
            <div class="dropdown-menu" :class="{ show: showDropdown }">
              <router-link to="/user" class="dropdown-item">
                <el-icon><User /></el-icon>
                个人中心
              </router-link>
              <router-link to="/topic/add" class="dropdown-item">
                <el-icon><Edit /></el-icon>
                发布话题
              </router-link>
              <div class="dropdown-item" @click="handlePasswordClick">
                <el-icon><Lock /></el-icon>
                修改密码
              </div>
              <div class="dropdown-divider"></div>
              <div class="dropdown-item" @click="handleLogout">
                <el-icon><SwitchButton /></el-icon>
                退出登录
              </div>
            </div>
          </div>
        </div>
        <template v-else>
          <el-button type="primary" @click="$router.push('/login')">
            登录
          </el-button>
        </template>
      </div>
    </div>
  </header>

  <!-- 修改密码弹窗 -->
  <password-dialog v-model:visible="showPasswordDialog" @success="handlePasswordSuccess" />
</template>

<script setup>
import { ref, onMounted, computed, onBeforeUnmount, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/store/modules/user'
import { ElMessage } from 'element-plus'
import { Search, Moon, Sunny, User, SwitchButton, Edit, Lock, Bell } from '@element-plus/icons-vue'
import PasswordDialog from '@/components/common/PasswordDialog.vue'
import { getUnreadCount } from '@/api'

const router = useRouter()
const userStore = useUserStore()
const searchText = ref('')
const isDark = ref(false)
const route = useRoute()
const showDropdown = ref(false)
const showPasswordDialog = ref(false)
const unreadCount = ref(0)
let pollingTimer = null

// 计算属性：是否隐藏搜索框
const hideSearch = computed(() => route.meta.hideSearch)

// 初始化主题
onMounted(() => {
  const savedTheme = localStorage.getItem('theme')
  if (savedTheme) {
    isDark.value = savedTheme === 'dark'
    applyTheme(isDark.value)
  } else {
    const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches
    isDark.value = prefersDark
    applyTheme(prefersDark)
  }
  startPolling()
})

// 切换主题
const toggleTheme = () => {
  isDark.value = !isDark.value
  applyTheme(isDark.value)
  localStorage.setItem('theme', isDark.value ? 'dark' : 'light')
}

// 应用主题
const applyTheme = (dark) => {
  document.documentElement.classList.toggle('dark-theme', dark)
}

const handleSearch = () => {
  if (!searchText.value.trim()) {
    return
  }
  router.push({
    path: '/search',
    query: { keyword: searchText.value.trim() }
  })
  searchText.value = ''
}

const handleLogout = async () => {
  try {
    await userStore.logout()
    ElMessage.success('退出成功')
  } catch (error) {
    ElMessage.error('退出失败')
  }
}

const editForm = ref({
  oldPassword: '',
  password: '',
  confirmPassword: ''
})

// 处理模态框点击
const handleModalClick = (e) => {
  if (e.target.classList.contains('modal-overlay')) {
    showEditDialog.value = false
  }
}

// 打开重置密码对话框
const handleOpenResetPasswordDialog = () => {
  editForm.value = {
    oldPassword: '',
    password: '',
    confirmPassword: ''
  }
  showResetPasswordDialog.value = true
}

// 头像加载失败处理
const handleAvatarError = (e) => {
  const img = e.target
  img.src = 'https://api.dicebear.com/7.x/avataaars/svg?seed=2'
}

// 点击修改密码
const handlePasswordClick = () => {
  showPasswordDialog.value = true
  showDropdown.value = false // 关闭下拉菜单
}

// 密码修改成功回调
const handlePasswordSuccess = () => {
  ElMessage.success('密码修改成功')
}

// 获取未读消息数
const fetchUnreadCount = async () => {
  try {
    const res = await getUnreadCount()
    unreadCount.value = res.data
  } catch (error) {
    console.error('获取未读消息数失败:', error)
  }
}

// 跳转到消息页面
const handleMessageClick = () => {
  router.push('/message')
}

// 开始轮询
const startPolling = () => {
  if (userStore.isLogin) {
    fetchUnreadCount()
    pollingTimer = setInterval(fetchUnreadCount, 30000) // 每30秒轮询一次
  }
}

// 停止轮询
const stopPolling = () => {
  if (pollingTimer) {
    clearInterval(pollingTimer)
    pollingTimer = null
  }
}

// 监听登录状态变化
watch(() => userStore.isLogin, (newVal) => {
  if (newVal) {
    startPolling()
  } else {
    stopPolling()
    unreadCount.value = 0
  }
})

onBeforeUnmount(() => {
  stopPolling()
})
</script>

<style scoped>
.header {
  background: var(--box-bg);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-content {
  max-width: 1200px;

  margin: 0 auto;
  padding: 12px 20px;
  display: flex;
  align-items: center;
  gap: 24px;
}

.logo-text {
  color: var(--primary-color);
  font-size: 24px;
  font-weight: bold;
  text-decoration: none;
}

.nav-menu {
  display: flex;
  gap: 20px;
}

.nav-item {
  color: var(--text-color);
  text-decoration: none;
  font-size: 16px;
  padding: 6px 12px;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.nav-item:hover,
.nav-item.router-link-active {
  background: var(--button-hover-bg);
}

.right-section {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-left: auto;
}

.search-box {
  width: 200px;
  transition: width 0.3s ease;
}

.search-box:focus-within {
  width: 300px;
}

.theme-toggle {
  color: var(--text-color);
  background: transparent;
  border: none;
  padding: 8px;
  cursor: pointer;
  transition: all 0.3s;
}

.theme-toggle:hover {
  transform: rotate(30deg);
  background: transparent !important;
}

.user-menu {
  display: flex;
  align-items: center;
  gap: 12px;
}

.dropdown {
  position: relative;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 4px 8px;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.3s;

  &:hover {
    background: var(--button-hover-bg);
  }

  .username {
    color: var(--text-color);
    font-size: 14px;
  }

  .arrow {
    width: 0;
    height: 0;
    border-left: 4px solid transparent;
    border-right: 4px solid transparent;
    border-top: 4px solid var(--text-color);
    transition: transform 0.3s;
    margin-left: 4px;

    &.arrow-up {
      transform: rotate(180deg);
    }
  }
}

.dropdown-menu {
  position: absolute;
  top: calc(100% + 8px);
  right: 0;
  min-width: 160px;
  background: var(--box-bg);
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  padding: 8px 0;
  opacity: 0;
  visibility: hidden;
  transform: translateY(-8px);
  transition: all 0.3s;
  z-index: 1000;

  &.show {
    opacity: 1;
    visibility: visible;
    transform: translateY(0);
  }

  &::before {
    content: '';
    position: absolute;
    top: -4px;
    right: 20px;
    width: 8px;
    height: 8px;
    background: var(--box-bg);
    transform: rotate(45deg);
  }
}

.dropdown-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 16px;
  color: var(--text-color);
  font-size: 14px;
  text-decoration: none;
  transition: all 0.3s;
  cursor: pointer;

  &:hover {
    background: var(--button-hover-bg);
    color: var(--primary-color);
  }

  .el-icon {
    font-size: 16px;
  }
}

.dropdown-divider {
  height: 1px;
  background: var(--border-color);
  margin: 8px 0;
}

:deep(.el-button) {
  --el-button-bg-color: var(--primary-color);
  --el-button-border-color: var(--primary-color);
  --el-button-hover-bg-color: var(--primary-hover);
  --el-button-hover-border-color: var(--primary-hover);
}

/* 自定义搜索框样式 */
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
    height: 36px;
    font-size: 14px;

    &::placeholder {
      color: var(--text-light);
    }
  }

  .search-icon {
    font-size: 16px;
    color: var(--text-light);
    margin-right: 4px;
  }
}

:deep(.theme-toggle) {
  --el-button-hover-bg-color: transparent !important;
  --el-button-hover-border-color: transparent !important;
  --el-button-hover-text-color: var(--primary-color) !important;
  
  &:focus-visible {
    outline: none;
    box-shadow: none;
  }
}

.message-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s;
  margin-right: 8px;
  color: var(--text-color);
  
  &:hover {
    background: var(--button-hover-bg);
    color: var(--primary-color);
  }
  
  .el-icon {
    font-size: 20px;
  }

  :deep(.el-badge__content) {
    background-color: var(--el-color-danger);
  }
}

/* 移动端适配 */
@media (max-width: 768px) {
  .message-btn {
    width: 32px;
    height: 32px;
    margin-right: 4px;
    
    .el-icon {
      font-size: 18px;
    }
  }
}
</style> 