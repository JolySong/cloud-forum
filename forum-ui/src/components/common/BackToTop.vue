// src/components/common/BackToTop.vue
<template>
  <el-backtop 
    :right="right" 
    :bottom="bottom"
    :visibility-height="visibilityHeight"
    :target="target"
  >
    <div class="back-to-top">
      <el-icon><Top /></el-icon>
      <span v-if="showText">{{ text }}</span>
    </div>
  </el-backtop>
</template>

<script setup>
import { Top } from '@element-plus/icons-vue'

defineProps({
  // 距离右侧距离
  right: {
    type: Number,
    default: 160
  },
  // 距离底部距离
  bottom: {
    type: Number,
    default: 120
  },
  // 显示触发的滚动高度
  visibilityHeight: {
    type: Number,
    default: 160
  },
  // 滚动目标元素
  target: {
    type: String,
    default: ''
  },
  // 是否显示文字
  showText: {
    type: Boolean,
    default: true
  },
  // 文字内容
  text: {
    type: String,
    default: ''
  }
})
</script>

<style scoped>
.back-to-top {
  background: var(--box-bg);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  padding: 8px;
  color: var(--text-color);
  transition: all 0.3s;

  .el-icon {
    font-size: 24px;
  }

  span {
    font-size: 12px;
  }

  &:hover {
    color: var(--primary-color);
    transform: translateY(-2px);
  }
}

:deep(.el-backtop) {
  background: var(--box-bg);
  border: 1px solid var(--border-color);
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  
  &:hover {
    background: var(--button-hover-bg);
  }
}
</style>