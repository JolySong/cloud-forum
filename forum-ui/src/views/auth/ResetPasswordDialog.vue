<!-- src/components/ResetPasswordDialog.vue -->
<template>
  <div class="modal-overlay" v-if="visible" @click="handleModalClick">
    <div class="modal-content" @click.stop>
      <div class="modal-header">
        <h3>重置密码</h3>
        <button class="close-btn" @click="resetForm">
          <el-icon><Close /></el-icon>
        </button>
      </div>

      <div class="modal-body">
        <form class="edit-form" @submit.prevent="handleNextStep">
          <div v-if="step >= 1" class="form-item">
            
            <div class="input-wrapper flex-container">
              <label class="label">用户名</label>
              <input 
                type="text" 
                v-model="form.username" 
                placeholder="请输入用户名"
                @keyup.enter="goToNextStep"
                required
              >
              <div class="username-input"></div>
            </div>
          </div>

          <div v-if="step >= 2" class="form-item">
            
            <div class="input-wrapper flex-container">
              <label class="label">邮箱</label>
              <input 
                type="email" 
                v-model="form.email" 
                placeholder="请输入邮箱"
                @blur="validateEmail"
                required
              >
              <button 
                class="email-btn" 
                :disabled="isButtonDisabled" 
                @click="sendVerificationCode"
                :class="{
                  'btn-normal': !isButtonDisabled,
                  'btn-disabled': isButtonDisabled
                }"
              >
              {{ isButtonDisabled ? countdown + '秒' : '获取验证码' }}
              </button>
              <div class="email-input"></div>
            </div>
            <span class="error-message" v-if="formErrors.email">{{ formErrors.email }}</span>
          </div>

          <div v-if="showCodeInput" class="form-item">
            <div class="input-wrapper flex-container">
              <label class="label">验证码</label>
                <input 
                type="text" 
                v-model="form.code" 
                placeholder="验证码"
                maxlength="6"
                @blur="validateCode"
                required
              >
              <div class="code-inputs"></div>
              </div>
              <span class="error-message" v-if="formErrors.code">{{ formErrors.code }}</span>
          </div>

          <div v-if="step >= 3" class="form-item">
            <div class="input-wrapper flex-container">
              <label class="label">新密码</label>
              <input 
                type="password" 
                v-model="form.newPassword" 
                placeholder="请输入新密码"
                @blur="validatePassword"
                required
              >
              <div class="password-input"></div>
            </div>
            <span class="error-message" v-if="formErrors.newPassword">{{ formErrors.newPassword }}</span>
          </div>
          <div v-if="step >= 3" class="form-item">
            <div class="input-wrapper flex-container">
              <label class="label">确认密码</label>
              <input 
                type="password" 
                v-model="form.confirmPassword" 
                placeholder="请确认新密码"
                @blur="validateConfirmPassword"
                required
              >
              <div class="password-input"></div>
            </div>
            <span class="error-message" v-if="formErrors.confirmPassword">{{ formErrors.confirmPassword }}</span>
          </div>
        </form>
        <div v-if="loading" class="loading-overlay">
          <div class="spinner"></div>
        </div>
      </div>

      <div class="modal-footer">
        <button class="cancel-btn" @click="resetForm">取消</button>
        <button v-if="step >= 3"
          class="next-btn" 
          :disabled="isNextButtonDisabled" 
          @click="handleNextStep"
          :class="{
            'btn-normal': !isNextButtonDisabled,
            'btn-disabled': isNextButtonDisabled,
          }"
        >
          确认修改
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { sendResetPasswordEmail, checkUsernameAndEmail, resetPassword } from '@/api/auth'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'

const router = useRouter()

const props = defineProps(['visible'])
const emit = defineEmits(['update:visible'])
const isButtonDisabled = ref(false)
const countdown = ref(60)
const showCodeInput = ref(false)
const isNextButtonDisabled = ref(true)

const isCodeValid = ref(false)

const form = ref({
  username: '',
  email: '',
  code: '',
  newPassword: '',
  confirmPassword: ''
})

const formErrors = ref({
  email: ''
})

const step = ref(1) // 1: 输入用户名, 2: 输入邮箱, 输入验证码, 3: 重置密码
const loading = ref(false) // 加载状态
const isEmailValid = computed(() => {
  const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  return emailPattern.test(form.value.email)
})

const resetForm = () => {
  form.value.username = ''
  form.value.email = ''
  form.value.newPassword = ''
  form.value.confirmPassword = ''
  formErrors.value.email = ''
  step.value = 1
  emit('update:visible', false)
}

const validateEmail = () => {
  const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!emailPattern.test(form.value.email)) {
    formErrors.value.email = '请输入有效的邮箱地址'
    showCodeInput.value = false
  } else {
    formErrors.value.email = ''
  }
}

const validateCode = () => {
  if (form.value.code.length !== 6) {
    formErrors.value.code = '请输入6位验证码'
  } else {
    formErrors.value.code = ''
    step.value = 3
  }
}

const validatePassword = () => {
  if (form.value.newPassword.length < 6) {
    formErrors.value.newPassword = '密码长度至少为6位'
  } else {
    formErrors.value.newPassword = ''
  }
}

const validateConfirmPassword = () => {
  if (form.value.confirmPassword && form.value.newPassword !== form.value.confirmPassword) {
    formErrors.value.confirmPassword = '两次输入的密码不一致'
  } else {
    formErrors.value.confirmPassword = ''
    isNextButtonDisabled.value = false
  }
}

const sendVerificationCode = async () => {

  if (!isEmailValid.value) {
    formErrors.value.email = '请输入有效的邮箱地址'
    return
  }
  
  // 检查用户名和邮箱是否一致
  const validEmailRes = await checkUsernameAndEmail({
    username: form.value.username,
    email: form.value.email
  })
  
  if (validEmailRes.code != 200) {
    formErrors.value.email = '邮箱或用户名错误'
    return
  }

  const codeRes = await sendResetPasswordEmail({
    username: form.value.username,
    email: form.value.email
  })
  if (codeRes.code != 200) {
    formErrors.value.email = '发送验证码失败,请稍后再试'
    ElMessage.error('发送验证码失败,请稍后再试')
    return 
  }

  isCodeValid.value = true
  isButtonDisabled.value = true
  countdown.value = 60
  showCodeInput.value = true // 显示验证码输入框

  const timer = setInterval(() => {
    countdown.value--
    if (countdown.value <= 0) {
      clearInterval(timer)
      isButtonDisabled.value = false
    }
  }, 1000)
}

const goToNextStep = () => {
  if (step.value === 1) {
    loading.value = true
    setTimeout(() => {
      step.value = 2
      loading.value = false
    }, 500); // 延迟 0.5 秒
  }
}

const handleNextStep = () => {
  if (step.value === 2) {
    loading.value = true
    setTimeout(() => {
      if (isCodeValid.value) {
        step.value = 3
      }
      loading.value = false
    }, 500); // 延迟 0.5 秒
  } else if (step.value === 3) {
    loading.value = true
    setTimeout(() => {
      loading.value = false
    }, 500); // 延迟 0.5 秒
     // 在这里添加重置密码的逻辑，例如调用 API
     resetPassword({
      username: form.value.username,
      email: form.value.email,
      code: form.value.code,
      password: form.value.newPassword
     }).then(res => {      
      if (res.code != 200) {
        formErrors.value.email = '重置密码失败'
        return
      }
      resetForm()
      ElMessage.success('重置密码成功')
      router.push('/login')
     })
    
  }
}
</script>

<style scoped>
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

  .modal-content {
    width: 600px;
    max-width: calc(100vw - 48px);
    background: var(--box-bg);
    border-radius: 12px;
    box-shadow: 0 4px 24px rgba(0, 0, 0, 0.2);
    animation: slideIn 0.3s ease;
    position: relative; /* 使加载动画相对于弹窗定位 */


    .modal-header {
      padding: 24px 32px;
      border-bottom: 1px solid var(--border-color);
      display: flex;
      justify-content: space-between;
      align-items: center;


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
    }

    .modal-body {
      padding: 32px;
    }

    .modal-footer {
      padding: 24px 32px;
      border-top: 1px solid var(--border-color);
      display: flex;
      justify-content: flex-end;
      gap: 16px;


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

      .next-btn {
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
    }
  }
}

.edit-form {
  display: flex;
  flex-direction: column;
  gap: 24px;

  .form-item {
    display: flex;
    flex-direction: column;
    gap: 8px;

    .input-wrapper {
      .label {
        font-size: 14px;
        width: 60px;
        color: var(--text-light);
      }

      position: relative;

      input {
        flex: 1;
        width: 100%;
        box-sizing: border-box;
        padding: 12px 16px;
        border: 1px solid var(--border-color);
        border-radius: 8px;
        background: var(--bg-color);
        color: var(--text-color);
        font-size: 14px;

        &.error {
          border-color: var(--el-color-danger);
        }
      }

    }
    .flex-container {
      display: flex;
      align-items: center; /* 垂直居中对齐 */
      gap: 8px; /* 输入框和按钮之间的间距 */
    }
  }
}

.username-input {
  min-width: 50px;
}
.email-input {
  min-width: 50px;
}
.code-inputs {
  min-width: 70%;
  gap: 8px;
}
.password-input {
  min-width: 50px;
  gap: 8px;
}
.btn-normal {
  background: var(--primary-color);
  color: white;
}

.btn-disabled {
  background: gray;
  color: var(--text-light);
  cursor: not-allowed;
}

.error-message {
  color: var(--el-color-danger);
  padding: 12px 16px;
  margin-left: 50px;
  font-size: 12px;
}

.loading-overlay {
  position: absolute; /* 绝对定位 */
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.8); /* 弹窗置灰 */
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 10; /* 确保在弹窗内容上方 */
}

.spinner {
  border: 4px solid rgba(255, 255, 255, 0.3);
  border-top: 4px solid var(--primary-color);
  border-radius: 50%;
  width: 30px;
  height: 30px;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}



.cancel-btn, .next-btn, .email-btn {
  padding: 10px 24px;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s;
}


.email-btn {
  min-width: 100px;
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
</style>