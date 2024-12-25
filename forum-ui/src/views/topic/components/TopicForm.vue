<template>
  <div class="topic-form">
    <!-- 标题 -->
    <div class="form-item">
      <div class="form-label">标题</div>
      <el-input
        v-model="formData.title"
        placeholder="请输入标题"
        maxlength="100"
        show-word-limit
      />
      <div class="form-tip">标题应简洁明了，能够清晰表达主题</div>
    </div>

    <!-- 分类 -->
    <div class="form-item">
      <div class="form-label">分类</div>
      <select 
        v-model="formData.categoryId" 
        class="native-select"
      >
        <option value="" disabled selected>请选择分类</option>
        <option 
          v-for="item in categories" 
          :key="item.id" 
          :value="item.id"
        >
          {{ item.name }}
        </option>
      </select>
      <div class="form-tip">选择合适的分类，便于他人查找</div>
    </div>

    <!-- 标签 -->
    <div class="form-item">
      <div class="form-label">标签</div>
      <div class="tag-select-wrapper">
        <div class="tag-list">
          <div 
            v-for="tag in availableTags" 
            :key="tag.id" 
            class="tag-option"
            :style="{ color: tag.color }"
            @click="toggleTag(tag.id)"
          >
            {{ tag.name }}
          </div>
        </div>
        <!-- 已选标签展示 -->
        <div class="selected-tags" v-if="formData.tags.length > 0">
          <div 
            v-for="tagId in formData.tags" 
            :key="tagId" 
            class="tag-item"
            :style="{ color: getTagColor(tagId) }"
          >
            {{ getTagName(tagId) }}
            <span class="tag-remove" @click="removeTag(tagId)">&times;</span>
          </div>
        </div>
      </div>
      <div class="form-tip">点击选择标签，最多可选择5个标签</div>
    </div>

    <!-- 内容编辑器 -->
    <div class="form-item">
      <div class="form-label">内容</div>
      <div class="editor-container">
        <Toolbar
          class="editor-toolbar"
          :editor="editorRef"
          :defaultConfig="toolbarConfig"
          :mode="mode"
        />
        <Editor
          class="editor-content"
          v-model="formData.content"
          :defaultConfig="editorConfig"
          :mode="mode"
          @onCreated="handleCreated"
          @onChange="handleChange"
        />
      </div>
      <div class="form-tip">支持 Markdown 格式，可以插入代码、链接等</div>
    </div>

    <!-- 操作按钮 -->
    <div class="form-actions">
      <el-button @click="handleCancel">取消</el-button>
      <el-button type="primary" @click="handleSubmit" :loading="submitting">
        {{ submitText }}
      </el-button>
    </div>
  </div>
</template>

<script setup>
import { ref, shallowRef, onBeforeUnmount, computed, watch } from 'vue'
import { ElMessage } from 'element-plus'
import { getCategories } from '@/api'
import { getTags } from '@/api'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import '@wangeditor/editor/dist/css/style.css'

const props = defineProps({
  initialData: {
    type: Object,
    default: () => ({
      title: '',
      categoryId: '',
      tags: [],
      content: ''
    })
  },
  submitText: {
    type: String,
    default: '发布'
  }
})

const emit = defineEmits(['submit', 'cancel'])

// 表单数据
const formData = ref({...props.initialData})

// 分类和标签数据
const categories = ref([])
const tags = ref([])
const submitting = ref(false)

// 编辑器相关配置
const editorRef = shallowRef()
const mode = 'default'
const toolbarConfig = {
  excludeKeys: [
    'uploadImage',
    'uploadVideo',
    'insertTable',
    'group-video',
    'group-image',
    'insertTable'
  ]
}
const editorConfig = {
  placeholder: '请输入内容...',
  MENU_CONF: {}
}

// 编辑器回调函数
const handleCreated = (editor) => {
  editorRef.value = editor
}

const handleChange = (editor) => {
  formData.value.content = editor.getHtml()
}

// 组件销毁时，销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value
  if (editor == null) return
  editor.destroy()
})

// 获取分类列表
const fetchCategories = async () => {
  try {
    const res = await getCategories()
    categories.value = res.data
  } catch (error) {
    console.error('获取分类失败:', error)
  }
}

// 获取标签列表
const fetchTags = async () => {
  try {
    const res = await getTags()
    tags.value = res.data
  } catch (error) {
    console.error('获取标签失败:', error)
  }
}

// 计算未选中的标签
const availableTags = computed(() => {
  return tags.value.filter(tag => !formData.value.tags.includes(tag.id))
})

// 获取标签颜色
const getTagColor = (tagId) => {
  const tag = tags.value.find(t => t.id === tagId)
  return tag?.color || 'var(--text-color)'
}

// 获取标签名称
const getTagName = (tagId) => {
  const tag = tags.value.find(t => t.id === tagId)
  return tag?.name || ''
}

// 移除标签
const removeTag = (tagId) => {
  formData.value.tags = formData.value.tags.filter(id => id !== tagId)
}

// 切换标签
const toggleTag = (tagId) => {
  if (formData.value.tags.length >= 5) {
    ElMessage.warning('最多只能选择5个标签')
    return
  }
  formData.value.tags.push(tagId)
}

// 提交表单
const handleSubmit = async () => {
  if (!formData.value.title.trim()) {
    ElMessage.warning('请输入标题')
    return
  }
  if (!formData.value.categoryId) {
    ElMessage.warning('请选择分类')
    return
  }
  if (!formData.value.content.trim()) {
    ElMessage.warning('请输入内容')
    return
  }

  submitting.value = true
  try {
    emit('submit', { ...formData.value })
  } finally {
    submitting.value = false
  }
}

// 取消
const handleCancel = () => {
  emit('cancel')
}

// 初始化数据
const initData = async () => {
  await Promise.all([
    fetchCategories(),
    fetchTags()
  ])
}

// 监听 initialData 的变化
watch(() => props.initialData, (newVal) => {
  if (newVal) {
    formData.value = {...newVal}
  }
}, { deep: true })

// 暴露方法给父组件
defineExpose({
  initData
})
</script>

<style scoped>
.topic-form {
  .form-item {
    margin-bottom: 20px;
  }

  .form-label {
    font-size: 14px;
    font-weight: 500;
    color: var(--text-color);
    margin-bottom: 8px;
  }

  .form-tip {
    font-size: 12px;
    color: var(--text-light);
    margin-top: 4px;
    line-height: 1.4;
  }

  /* 输入框样式适配 */
  :deep(.el-input) {
    .el-input__wrapper {
      background-color: var(--bg-color);
      box-shadow: 0 0 0 1px var(--border-color) inset;
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
      background-color: transparent;

      &::placeholder {
        color: var(--text-light);
      }
    }

    /* 字数限制样式 */
    .el-input__count {
      background: transparent !important;
      color: var(--text-light);

      .el-input__count-inner {
        background: transparent;
      }
    }
  }

  /* 原生下拉框样式 */
  .native-select {
    width: 100%;
    padding: 8px 12px;
    font-size: 14px;
    border-radius: 4px;
    border: 1px solid var(--border-color);
    background-color: var(--bg-color);
    color: var(--text-color);
    cursor: pointer;
    outline: none;
    transition: all 0.3s ease;
    appearance: none;
    background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3e%3cpolyline points='6 9 12 15 18 9'%3e%3c/polyline%3e%3c/svg%3e");
    background-repeat: no-repeat;
    background-position: right 8px center;
    background-size: 16px;
    padding-right: 32px;

    &:hover {
      border-color: var(--primary-color);
    }

    &:focus {
      border-color: var(--primary-color);
      box-shadow: 0 0 0 1px var(--primary-color);
    }

    /* 选项样式 */
    & option {
      background-color: var(--box-bg);
      color: var(--text-color);
      padding: 8px;

      &:hover {
        background-color: var(--button-hover-bg);
      }

      &:checked {
        background-color: var(--primary-color);
        color: white;
      }
    }

    /* 禁用状态 */
    &:disabled {
      background-color: var(--button-hover-bg);
      cursor: not-allowed;
      opacity: 0.7;
    }

    /* placeholder 样式 */
    & option[value=""][disabled] {
      color: var(--text-light);
      background-color: var(--bg-color);
    }
  }

  /* 标签选择器样式 */
  .tag-select-wrapper {
    position: relative;
  }

  .tag-list {
    display: flex;
    flex-wrap: wrap;
    gap: 8px;
    padding: 8px;
    background: var(--bg-color);
    border: 1px solid var(--border-color);
    border-radius: 4px;
    min-height: 100px;
    max-height: 200px;
    overflow-y: auto;
  }

  .tag-option {
    padding: 4px 12px;
    height: 20px;
    background: var(--button-hover-bg);
    border-radius: 4px;
    cursor: pointer;
    transition: all 0.3s;
    font-size: 13px;

    &:hover {
      background: var(--border-color);
    }

    &.active {
      background: var(--primary-color);
      color: white !important;
    }
  }

  .selected-tags {
    display: flex;
    flex-wrap: wrap;
    gap: 8px;
    margin-top: 12px;
    padding: 8px;
    background: var(--bg-color);
    border: 1px solid var(--border-color);
    border-radius: 4px;
  }

  .tag-item {
    display: inline-flex;
    align-items: center;
    gap: 4px;
    padding: 4px 8px;
    background: var(--button-hover-bg);
    border-radius: 4px;
    font-size: 13px;
    transition: all 0.3s;

    &:hover {
      background: var(--border-color);
    }

    .tag-remove {
      cursor: pointer;
      font-size: 16px;
      line-height: 1;
      opacity: 0.7;

      &:hover {
        opacity: 1;
      }
    }
  }

  /* 编辑器样式 */
  .editor-container {
    border: 1px solid var(--border-color);
    border-radius: 4px;
    overflow: hidden;
    min-height: 400px;
    display: flex;
    flex-direction: column;
  }

  .editor-toolbar {
    border-bottom: 1px solid var(--border-color);
    background-color: var(--bg-color);
    flex-shrink: 0;
  }

  .editor-content {
    flex: 1;
    min-height: 300px;
    background-color: var(--box-bg);
  }

  /* 编辑器深色模式适配 */
  :deep(.w-e-text-container) {
    background-color: var(--box-bg);
    flex: 1;
    height: 100% !important;
  }

  :deep(.w-e-text-container [data-slate-editor]) {
    color: var(--text-color) !important;
    height: 100% !important;
    min-height: 300px;
    padding: 16px;
  }

  :deep(.w-e-toolbar) {
    background-color: var(--bg-color) !important;
    border-bottom: 1px solid var(--border-color) !important;
  }

  :deep(.w-e-bar-item button) {
    color: var(--text-color) !important;
  }

  :deep(.w-e-panel) {
    background-color: var(--box-bg) !important;
    border-color: var(--border-color) !important;
  }

  :deep(.w-e-panel button) {
    background-color: var(--primary-color) !important;
    border-color: var(--primary-color) !important;
  }

  /* 操作按钮 */
  .form-actions {
    display: flex;
    justify-content: flex-end;
    gap: 12px;
    margin-top: 24px;
  }

  /* 编辑器工具栏样式 */
  :deep(.w-e-bar-item) {
    &:hover {
      background-color: var(--button-hover-bg) !important;
    }

    &.active {
      background-color: var(--button-hover-bg) !important;
      color: var(--primary-color) !important;
    }
  }

  /* 编辑器下拉菜单样式 */
  :deep(.w-e-drop-panel) {
    background-color: var(--box-bg) !important;
    border-color: var(--border-color) !important;
    
    .w-e-dropdown-item {
      color: var(--text-color) !important;
      
      &:hover {
        background-color: var(--button-hover-bg) !important;
      }
    }
  }

  /* 编辑器弹出面板样式 */
  :deep(.w-e-modal), 
  :deep(.w-e-panel-container) {
    background-color: var(--box-bg) !important;
    border-color: var(--border-color) !important;
    color: var(--text-color) !important;

    input, textarea {
      background-color: var(--bg-color) !important;
      border-color: var(--border-color) !important;
      color: var(--text-color) !important;

      &:focus {
        border-color: var(--primary-color) !important;
      }
    }

    button {
      background-color: var(--button-hover-bg) !important;
      border-color: var(--border-color) !important;
      color: var(--text-color) !important;

      &:hover {
        background-color: var(--primary-color) !important;
        color: white !important;
      }
    }
  }

  /* 编辑器全屏样式 */
  :deep(.w-e-full-screen-container) {
    position: fixed !important;
    top: 0 !important;
    left: 0 !important;
    width: 100% !important;
    height: 100vh !important;
    z-index: 9999 !important;
    background-color: var(--box-bg) !important;

    .editor-container {
      height: 100vh !important;
      border: none;
    }

    .editor-toolbar {
      position: sticky;
      top: 0;
      left: 0;
      right: 0;
      z-index: 10000;
      border-bottom: 1px solid var(--border-color);
    }

    .editor-content {
      height: calc(100vh - 42px) !important;
      overflow-y: auto;
    }

    .w-e-text-container {
      height: calc(100vh - 42px) !important;
    }

    .w-e-text-container [data-slate-editor] {
      height: calc(100vh - 42px) !important;
      min-height: unset !important;
    }
  }
}
</style> 