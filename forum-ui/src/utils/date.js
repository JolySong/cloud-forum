import dayjs from 'dayjs'
import relativeTime from 'dayjs/plugin/relativeTime'
import 'dayjs/locale/zh-cn'

// 使用相对时间插件
dayjs.extend(relativeTime)
// 使用中文语言包
dayjs.locale('zh-cn')

/**
 * 格式化日期
 * @param {string|number|Date} date 日期
 * @param {string} format 格式化模板
 * @returns {string} 格式化后的日期字符串
 */
export function formatDate(date, format = 'YYYY-MM-DD HH:mm:ss') {
  if (!date) return ''
  return dayjs(date).format(format)
}

/**
 * 获取相对时间
 * @param {string|number|Date} date 日期
 * @returns {string} 相对时间字符串
 */
export function fromNow(date) {
  if (!date) return ''
  return dayjs(date).fromNow()
}

/**
 * 判断是否是今天
 * @param {string|number|Date} date 日期
 * @returns {boolean}
 */
export function isToday(date) {
  return dayjs(date).isSame(dayjs(), 'day')
}

/**
 * 判断是否是本周
 * @param {string|number|Date} date 日期
 * @returns {boolean}
 */
export function isThisWeek(date) {
  return dayjs(date).isSame(dayjs(), 'week')
}

/**
 * 智能格式化日期
 * 今天显示具体时间，如：14:30
 * 昨天显示"昨天 14:30"
 * 本周显示"周一 14:30"
 * 其他显示具体日期，如：2024-02-28 14:30
 * @param {string|number|Date} date 日期
 * @returns {string} 格式化后的日期字符串
 */
export function smartFormatDate(date) {
  if (!date) return ''
  
  const dateObj = dayjs(date)
  
  if (isToday(date)) {
    return dateObj.format('HH:mm')
  }
  
  if (dateObj.isSame(dayjs().subtract(1, 'day'), 'day')) {
    return `昨天 ${dateObj.format('HH:mm')}`
  }
  
  if (isThisWeek(date)) {
    return dateObj.format('ddd HH:mm')
  }
  
  return dateObj.format('YYYY-MM-DD HH:mm')
} 