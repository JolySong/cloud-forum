// 主题配置
export const themes = {
  light: {
    '--color-bg-primary': '#ffffff',
    '--color-bg-secondary': '#f5f7fa',
    '--color-text-primary': '#303133',
    '--color-text-secondary': '#606266',
    '--color-text-muted': '#909399',
    '--color-border': '#ebeef5',
    '--color-hover': '#f5f7fa',
    '--color-shadow': 'rgba(0, 0, 0, 0.1)',
    '--color-card-bg': '#ffffff',
    '--color-success': '#67c23a',
    '--color-warning': '#e6a23c',
    '--color-danger': '#f56c6c',
    '--color-info': '#909399',
    '--color-primary-light': '#ecf5ff',
    '--color-hover-light': '#f5f7fa'
  },
  dark: {
    '--color-bg-primary': '#1a1a1a',
    '--color-bg-secondary': '#141414',
    '--color-text-primary': '#e5eaf3',
    '--color-text-secondary': '#a3a6ad',
    '--color-text-muted': '#6c6e72',
    '--color-border': '#363637',
    '--color-hover': '#2c2c2c',
    '--color-shadow': 'rgba(0, 0, 0, 0.3)',
    '--color-card-bg': '#1d1d1d',
    '--color-success': '#85ce61',
    '--color-warning': '#ebb563',
    '--color-danger': '#f78989',
    '--color-info': '#a6a9ad',
    '--color-primary-light': '#1d1e1f',
    '--color-hover-light': '#2c2c2c'
  }
}

// 设置主题
export function setTheme(theme) {
  const root = document.documentElement
  const themeConfig = themes[theme]
  
  for (const [key, value] of Object.entries(themeConfig)) {
    root.style.setProperty(key, value)
  }
  
  localStorage.setItem('theme', theme)
}

// 获取当前主题
export function getTheme() {
  return localStorage.getItem('theme') || 'light'
}

// 初始化主题
export function initTheme() {
  const savedTheme = getTheme()
  setTheme(savedTheme)
  return savedTheme
} 