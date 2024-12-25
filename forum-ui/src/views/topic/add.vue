<template>
  <div class="topic-add-container">
    <div class="topic-add-card">
      <div class="card-header">
        <h2>发布话题</h2>
      </div>
      <div class="card-body">
        <topic-form
          ref="formRef"
          submit-text="发布"
          @submit="handleSubmit"
          @cancel="handleCancel"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/store/modules/user'
import { createTopic } from '@/api'
import TopicForm from './components/TopicForm.vue'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref(null)

// 提交表单
const handleSubmit = async (formData) => {
  try {
    await createTopic(formData)
    ElMessage.success('发布成功')
    router.push('/topic')
  } catch (error) {
    console.error('发布失败:', error)
    ElMessage.error('发布失败')
  }
}

// 取消
const handleCancel = () => {
  router.back()
}

onMounted(() => {
  // 检查登录状态
  if (!userStore.isLogin) {
    ElMessage.warning('请先登录')
    router.push('/login')
    return
  }
  
  formRef.value?.initData()
})
</script>

<style scoped>
.topic-add-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.topic-add-card {
  background: var(--box-bg);
  border-radius: 8px;
  padding: 24px;

  .card-header {
    margin-bottom: 24px;

    h2 {
      color: var(--text-color);
      font-size: 20px;
      margin: 0;
    }
  }
}
</style> 