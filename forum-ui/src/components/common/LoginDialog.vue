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
        <div class="dialog-content">
          <el-icon class="warning-icon" :size="48"><Warning /></el-icon>
          <p class="message">{{ message }}</p>
        </div>
      </div>

      <div class="modal-footer">
        <button class="cancel-btn" @click="handleCancel">{{ cancelText }}</button>
        <button class="submit-btn" @click="handleConfirm">{{ confirmText }}</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { Close, Warning } from '@element-plus/icons-vue'

const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false
  },
  title: {
    type: String,
    default: '提示'
  },
  message: {
    type: String,
    default: '该操作需要登录，是否前往登录？'
  },
  confirmText: {
    type: String,
    default: '去登录'
  },
  cancelText: {
    type: String,
    default: '取消'
  }
})

const emit = defineEmits(['update:modelValue', 'confirm', 'cancel'])

const handleConfirm = () => {
  emit('confirm')
  emit('update:modelValue', false)
}

const handleCancel = () => {
  emit('cancel')
  emit('update:modelValue', false)
}

const handleClose = () => {
  emit('cancel')
  emit('update:modelValue', false)
}

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
}

.dialog-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.warning-icon {
  color: var(--el-color-warning);
  margin-bottom: 16px;
}

.message {
  font-size: 16px;
  color: var(--text-color);
  margin: 0;
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

  &:hover {
    opacity: 0.9;
    transform: translateY(-1px);
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