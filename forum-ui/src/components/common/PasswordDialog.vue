<template>
  <div class="modal-overlay" v-if="modelValue" @click="handleModalClick">
    <div class="modal-content" @click.stop>
      <div class="modal-header">
        <h3>{{ title }}</h3>
        <button class="close-btn" @click="handleClose">
          <el-icon><Close /></el-icon>
        </button>
      </div>

      <div class="modal-body">
        <form class="edit-form" @submit.prevent="handleSubmit">
          <template v-if="title === '修改密码'">
            <div class="form-item">
              <label>当前密码</label>
              <div class="input-wrapper">
                <el-input 
                  v-model="form.oldPassword"
                  type="password"
                  show-password
                  :class="{ error: errors.oldPassword }"
                />
              </div>
              <span class="error-message" v-if="errors.oldPassword">{{ errors.oldPassword }}</span>
            </div>

            <div class="form-item">
              <label>新密码</label>
              <div class="input-wrapper">
                <el-input 
                  v-model="form.newPassword"
                  type="password"
                  show-password
                  :class="{ error: errors.newPassword }"
                />
              </div>
              <span class="error-message" v-if="errors.newPassword">{{ errors.newPassword }}</span>
            </div>

            <div class="form-item">
              <label>确认新密码</label>
              <div class="input-wrapper">
                <el-input 
                  v-model="form.confirmPassword"
                  type="password"
                  show-password
                  :class="{ error: errors.confirmPassword }"
                />
              </div>
              <span class="error-message" v-if="errors.confirmPassword">{{ errors.confirmPassword }}</span>
            </div>
          </template>
          <template v-else>
            <div class="form-item">
              <slot name="description"></slot>
              <div class="input-wrapper">
                <el-input 
                  v-model="form.content"
                  type="textarea"
                  :rows="3"
                  :placeholder="placeholder"
                />
              </div>
            </div>
          </template>
        </form>
      </div>

      <div class="modal-footer">
        <button class="cancel-btn" @click="handleClose">取消</button>
        <button 
          class="submit-btn" 
          :disabled="saving" 
          @click="handleSubmit"
        >
          {{ saving ? '保存中...' : confirmText }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { Close } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { updateUserPassword } from '@/api'
import { useUserStore } from '@/store/modules/user'

const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false
  },
  title: {
    type: String,
    default: '修改密码'
  },
  confirmText: {
    type: String,
    default: '保存'
  },
  placeholder: {
    type: String,
    default: ''
  }
})

const emit = defineEmits(['update:modelValue', 'success', 'confirm'])

const saving = ref(false)
const form = ref({
  content: '',
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})
const errors = ref({})

// 表单验证
const validate = () => {
  let isValid = true
  errors.value = {}

  if (!form.value.oldPassword) {
    errors.value.oldPassword = '请输入当前密码'
    isValid = false
  }

  if (!form.value.newPassword) {
    errors.value.newPassword = '请输入新密码'
    isValid = false
  } else if (form.value.newPassword.length < 6) {
    errors.value.newPassword = '新密码不能少于6个字符'
    isValid = false
  }

  if (!form.value.confirmPassword) {
    errors.value.confirmPassword = '请确认新密码'
    isValid = false
  } else if (form.value.confirmPassword !== form.value.newPassword) {
    errors.value.confirmPassword = '两次输入的密码不一致'
    isValid = false
  }

  return isValid
}

// 提交表单
const handleSubmit = async () => {
  if (props.title === '修改密码') {
    if (!validate()) return
    
    saving.value = true
    try {
      await updateUserPassword({
        password: form.value.oldPassword,
        newPassword: form.value.newPassword
      })
      emit('success')
      handleClose()
      useUserStore.logout()
      ElMessage.success('密码修改成功')
    } catch (error) {
      console.error('密码修改失败:', error)
      ElMessage.error(error.response?.data?.message || '密码修改失败')
    } finally {
      saving.value = false
    }
  } else {
    if (!form.value.content.trim()) {
      ElMessage.warning('请输入内容')
      return
    }
    emit('confirm', form.value.content)
    handleClose()
  }
}

// 关闭对话框
const handleClose = () => {
  emit('update:modelValue', false)
  // 重置表单
  form.value = {
    content: '',
    oldPassword: '',
    newPassword: '',
    confirmPassword: ''
  }
  errors.value = {}
}

// 处理模态框点击
const handleModalClick = (e) => {
  if (e.target.classList.contains('modal-overlay')) {
    handleClose()
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
}

.modal-content {
  width: 400px;
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

  :deep(.el-input__wrapper) {
    background-color: var(--bg-color);
    box-shadow: 0 0 0 1px var(--border-color) inset;
    border-radius: 4px;
    transition: all 0.3s ease;

    &:hover {
      box-shadow: 0 0 0 1px var(--primary-color) inset;
    }

    &.is-focus {
      box-shadow: 0 0 0 1px var(--primary-color) inset !important;
      background-color: var(--box-bg);
    }

    &.error {
      box-shadow: 0 0 0 1px var(--el-color-danger) inset !important;
    }
  }

  :deep(.el-input__inner) {
    height: 40px;
    font-size: 14px;
    color: var(--text-color);

    &::placeholder {
      color: var(--text-light);
    }
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
</style> 