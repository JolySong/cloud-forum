src/views/
├── auth/                  # 认证相关页面
│   ├── Login.vue         # 登录页
│   ├── Register.vue      # 注册页
│   └── ResetPwd.vue      # 重置密码页
│
├── home/                  # 首页相关
│   ├── components/       # 首页组件
│   │   ├── TopicList.vue    # 话题列表组件
│   │   ├── CategoryNav.vue  # 分类导航组件
│   │   └── HotTopics.vue    # 热门话题组件
│   └── index.vue         # 首页
│
├── topic/                 # 话题相关页面
│   ├── components/       # 话题组件
│   │   ├── TopicContent.vue # 话题内容组件
│   │   ├── CommentList.vue  # 评论列表组件
│   │   └── RelatedTopics.vue # 相关话题组件
│   ├── create.vue        # 创建话题页
│   ├── edit.vue          # 编辑话题页
│   └── detail.vue        # 话题详情页
│
├── user/                  # 用户相关页面
│   ├── components/       # 用户组件
│   │   ├── UserInfo.vue     # 用户信息组件
│   │   ├── UserTopics.vue   # 用户话题列表
│   │   └── UserStats.vue    # 用户统计信息
│   ├── profile.vue       # 个人主页
│   └── settings.vue      # 设置页面
│
└── error/                 # 错误页面
    ├── 403.vue           # 无权限页面
    ├── 404.vue           # 页面不存在
    └── 500.vue           # 服务器错误页面