<template>
  <div class="layout-container">
    <!-- 头部导航 -->
    <header-nav />
    
    <!-- 主要内容区域 -->
    <main class="main-content">
      <router-view />
    </main>
    
    <!-- 底部信息 -->
    <footer-info />
  </div>
</template>

<script setup>
import HeaderNav from '@/components/layout/HeaderNav.vue'
import FooterInfo from '@/components/layout/FooterInfo.vue'
</script>

<style scoped>
.layout-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.main-content {
  flex: 1;
  background-color: var(--bg-color);
}
</style>