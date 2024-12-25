<template>
  <div class="topic-add-container">
    <div class="topic-add-card">
      <div class="card-header">
        <h2>编辑话题</h2>
      </div>
      <div class="card-body">
        <topic-form
          ref="formRef"
          :initial-data="initialData"
          submit-text="更新"
          @submit="handleSubmit"
          @cancel="handleCancel"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/store/modules/user'
import { getTopicDetail, updateTopic } from '@/api'
import TopicForm from './components/TopicForm.vue'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const formRef = ref(null)
const initialData = ref({
  title: '',
  categoryId: '',
  tags: [],
  content: ''
})

// 获取话题详情
const fetchTopicDetail = async () => {
  try {
    // 确保有 id 参数
    if (!route.params.id) {
      ElMessage.error('缺少话题ID')
      router.back()
      return
    }

    const res = await getTopicDetail(route.params.id)
    if (!res.data) {
      ElMessage.error('话题不存在')
      router.back()
      return
    }

    const { title, categoryId, tags, content } = res.data
    // 更新表单初始数据
    initialData.value = {
      title,
      categoryId,
      tags: tags?.map(tag => tag.id) || [],
      content
    }
  } catch (error) {
    console.error('获取话题详情失败:', error)
    ElMessage.error('获取话题详情失败')
    router.back()
  }
}

// 提交表单
const handleSubmit = async (formData) => {
  try {
    await updateTopic(route.params.id, formData)
    ElMessage.success('更新成功')
    router.back()
  } catch (error) {
    console.error('更新失败:', error)
    ElMessage.error('更新失败')
  }
}

// 取消
const handleCancel = () => {
  router.back()
}

onMounted(async () => {
  // 检查登录状态
  if (!userStore.isLogin) {
    ElMessage.warning('请先登录')
    router.push('/login')
    return
  }
  
  // 先获取数据，再初始化表单
  await fetchTopicDetail()
  // 确保数据加载完成后再初始化表单
  nextTick(() => {
    formRef.value?.initData()
  })
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