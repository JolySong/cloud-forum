// src/api/index.js

// 认证相关接口
import * as authApi from './auth'
import * as userApi from './user'
import * as topicApi from './topic'
import * as commentApi from './comment'
import * as categoryApi from './category'
import * as tagApi from './tag'
import * as bannerApi from './system'
import * as searchApi from './search'
import * as hotApi from './home'
import * as fileApi from './file'
import * as messageApi from './message'

// 导出所有API函数
export const {
  login,
  logout,
  checkLoginStatus,
  getSysConfig,
  checkUsernameAndEmail,
  sendResetPasswordEmail,
  resetPassword
} = authApi

export const {
  getUserInfo,
  getUserStat,
  updateUserInfo,
  updateUserPassword,
  getUserTopics,
  getUserFavorites,
  getUserComments,
  getUserLikes,
  uploadAvatar
} = userApi

export const {
  getTopics,
  getTopicDetail,
  createTopic,
  updateTopic,
  deleteTopic,
  likeTopic,
  unlikeTopic,
  favoriteTopic,
  unfavoriteTopic,
  getAuthorInfo,
  followAuthor,
  unfollowAuthor,
  getActiveUsers,
  getRecommendTopics,
  getAuthorTopics
} = topicApi

export const {
  getTopicComments,
  createComment,
  deleteComment,
  likeComment,
  unlikeComment,
  replyComment,
  topComment,
  untopComment
} = commentApi

export const {
  getCategories,
  getCategory,
  createCategory,
  updateCategory,
  deleteCategory,
  sortCategory
} = categoryApi

export const {
  getTags,
  createTag,
  updateTag,
  deleteTag
} = tagApi

export const {
  getBanners
} = bannerApi

export const {
  searchTopics
} = searchApi

export const {
  getHotCategories,
  getHotTags,
  getHotTopics,
  getHotComments,
  getForumStats
} = hotApi

export const {
  uploadFile,
  getFileUrl
} = fileApi

export const {
  getMessages,
  readMessage,
  readAllMessages,
  getUnreadCount,
  invalidateMessage
} = messageApi

// 导出API模块映射(用于扫描器)
export const apiModules = {
  auth: {
    name: '认证管理',
    module: authApi
  },
  user: {
    name: '用户管理',
    module: userApi
  },
  topic: {
    name: '话题管理',
    module: topicApi
  },
  comment: {
    name: '评论管理',
    module: commentApi
  },
  category: {
    name: '分类管理',
    module: categoryApi
  },
  tag: {
    name: '标签管理',
    module: tagApi
  },
  banner: {
    name: '轮播图管理',
    module: bannerApi
  },
  search: {
    name: '搜索服务',
    module: searchApi
  },
  hot: {
    name: '热门服务',
    module: hotApi
  },
  file: {
    name: '文件管理',
    module: fileApi
  },
  message: {
    name: '消息管理',
    module: messageApi
  }
}

// API分组配置
export const apiGroups = {
  auth: {
    name: '认证管理',
    key: 'auth',
    sort: 1
  },
  user: {
    name: '用户管理',
    key: 'user',
    sort: 2
  },
  topic: {
    name: '话题管理',
    key: 'topic',
    sort: 3
  },
  comment: {
    name: '评论管理',
    key: 'comment',
    sort: 4
  },
  category: {
    name: '分类管理',
    key: 'category',
    sort: 5
  },
  tag: {
    name: '标签管理',
    key: 'tag',
    sort: 6
  },
  banner: {
    name: '轮播图管理',
    key: 'banner',
    sort: 7
  },
  search: {
    name: '搜索服务',
    key: 'search',
    sort: 8
  },
  hot: {
    name: '热门服务',
    key: 'hot',
    sort: 9
  },
  file: {
    name: '文件管理',
    key: 'file',
    sort: 10
  },
  message: {
    name: '消息管理',
    key: 'message',
    sort: 11
  }
}
