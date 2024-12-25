<template>
  <div class="login-container">
    <button class="theme-toggle" @click="toggleTheme">
      {{ isDark ? '🌞' : '🌙' }}
    </button>
    
    <div class="login-box">
      <h2>登录到论坛</h2>
      <form @submit.prevent="handleLogin">
        <div class="form-item">
          <label for="username">用户名</label>
          <input 
            type="text" 
            id="username"
            v-model="loginForm.username"
            placeholder="请输入用户名"
            required
          >
        </div>
        <div class="form-item">
          <label for="password">密码</label>
          <input 
            type="password" 
            id="password"
            v-model="loginForm.password"
            placeholder="请输入密码"
            required
          >
        </div>
        <div class="form-options">
          <label class="checkbox-label">
            <input type="checkbox" v-model="loginForm.remember">
            <span>记住我</span>
          </label>
          <a href="#" class="forgot-password" @click.prevent="handleForgotPassword">忘记密码？</a>
        </div>
        <div class="form-item">
          <button type="submit" class="login-btn">登录</button>
        </div>
        <div class="other-login">
          <div class="divider">
            <span>其他登录方式</span>
          </div>
          <div class="social-buttons">
            <button type="button" class="social-btn github" @click="handleGithubLogin">
              GitHub登录
            </button>
            <button type="button" class="social-btn wechat" @click="handleWechatLogin">
              微信登录
            </button>
          </div>
        </div>
      </form>
    </div>

    <ConfirmLoginDialog v-model="confirmLoginDialog" />
    <ResetPasswordDialog :visible="isResetDialogVisible" @update:visible="isResetDialogVisible = $event" />
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import '@/assets/theme.css'  // 导入主题样式
import { useUserStore } from '@/store/modules/user'
import { ElMessage } from 'element-plus'
import ConfirmLoginDialog from './ConfirmLoginDialog.vue'
import ResetPasswordDialog from './ResetPasswordDialog.vue'
import { getSysConfig, checkLoginStatus } from '@/api'
import { set } from '@/utils/storage'


const router = useRouter()
const userStore = useUserStore()
const isDark = ref(false)
const singleDeviceLogin = ref(false)
const isLoggedIn = ref(false)
const confirmLoginDialog = ref(false)
const isResetDialogVisible = ref(false)
// 初始化主题
onMounted(() => {
  // 从本地存储获取主题设置
  const savedTheme = localStorage.getItem('theme')
  if (savedTheme) {
    isDark.value = savedTheme === 'dark'
    applyTheme(isDark.value)
  } else {
    // 如果没有保存的主题，则使用系统主题
    const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches
    isDark.value = prefersDark
    applyTheme(prefersDark)
  }
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

const loginForm = reactive({
  username: 'misan',
  password: '123456',
  remember: false,
  code: '',
  uuid: '',
  device: ''
})

const handleLogin = async () => {
  // 获取设备
  loginForm.device = navigator.userAgent

  await handleGetConfig()
  if (singleDeviceLogin.value) {
    await handleCheckLoginStatus(loginForm.username)
    if (isLoggedIn.value) {
      set('loginForm', loginForm)
      confirmLoginDialog.value = true
      return
    }
  }

  const res = await userStore.login(loginForm)
  if (res.success) {
    ElMessage.success('登录成功')
    // 登录成功后跳转到首页
    router.push('/home')
    return
  }
}

const handleGetConfig = async () => {
  const res = await getSysConfig()
  singleDeviceLogin.value = res.data.singleDeviceLogin
}

const handleCheckLoginStatus = async (username) => {
  const res = await checkLoginStatus(username)
  isLoggedIn.value = res.data.isLoggedIn
}

const handleForgotPassword = () => {
  isResetDialogVisible.value = true
}

const handleGithubLogin = () => {
  console.log('GitHub登录')
}

const handleWechatLogin = () => {
  console.log('微信登录')
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: var(--bg-color);
  transition: all 0.3s ease;
}

.login-box {
  width: 400px;
  padding: 30px;
  background: var(--box-bg, white);
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  margin-bottom: 30px;
  color: var(--text-color, #333);
}

.form-item {
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 8px;
  color: var(--label-color, #666);
}

input {
  width: 100%;
  padding: 10px 0px 10px 0px;
  border: 1px solid var(--border-color, #ddd);
  border-radius: 4px;
  font-size: 14px;
  text-indent: 10px;
  background-color: var(--input-bg, white);
  color: var(--text-color, #333);
}

input:focus {
  outline: none;
  border-color: var(--primary-color, #409eff);
}

.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.checkbox-label {
  display: flex;
  align-items: center;
  gap: 6px;
  cursor: pointer;
}

.checkbox-label input[type="checkbox"] {
  width: auto;
  margin: 0;
}

.forgot-password {
  color: var(--primary-color, #409eff);
  text-decoration: none;
  font-size: 14px;
}

.login-btn {
  width: 100%;
  padding: 12px;
  background-color: var(--primary-color, #409eff);
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
}

.login-btn:hover {
  background-color: var(--primary-hover, #66b1ff);
}

.other-login {
  margin-top: 24px;
}

.divider {
  display: flex;
  align-items: center;
  margin: 20px 0;
  color: var(--text-light, #999);
}

.divider::before,
.divider::after {
  content: '';
  flex: 1;
  height: 1px;
  background: var(--border-color, #ddd);
}

.divider span {
  padding: 0 10px;
  font-size: 14px;
}

.social-buttons {
  display: flex;
  gap: 12px;
}

.social-btn {
  flex: 1;
  padding: 10px;
  border: 1px solid var(--border-color, #ddd);
  border-radius: 4px;
  background: var(--button-bg, white);
  color: var(--text-color, #333);
  cursor: pointer;
  transition: all 0.3s;
}

.social-btn:hover {
  background: var(--button-hover-bg, #f5f5f5);
}

.github {
  background: #24292e;
  color: white;
}

.github:hover {
  background: #2f363d;
}

.wechat {
  background: #07c160;
  color: white;
}

.wechat:hover {
  background: #06ad56;
}

/* 添加主题切换按钮样式 */
.theme-toggle {
  position: fixed;
  top: 20px;
  right: 20px;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  border: none;
  background: var(--box-bg, white);
  color: var(--text-color, #333);
  cursor: pointer;
  font-size: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.theme-toggle:hover {
  transform: scale(1.1);
}
</style>