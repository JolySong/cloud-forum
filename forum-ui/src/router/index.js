import { createRouter, createWebHistory } from 'vue-router'
import MainLayout from '@/layouts/MainLayout.vue'
import { useUserStore } from '@/store/modules/user'

const routes = [
  {
    path: '/',
    component: MainLayout,
    redirect: '/home',
    children: [
      {
        path: '',
        name: 'Home',
        redirect: '/home'
      },
      {
        path: 'home',
        name: 'Home',
        component: () => import('@/views/home/index.vue')
      },
    ]
  },
  {
    path: '/topic',
    component: MainLayout,
    children: [
      {
        path: '', // 默认子路由
        name: 'TopicHome',
        component: () => import('@/views/topic/index.vue')
      },
      {
        path: 'detail/:id',
        name: 'TopicDetail',
        component: () => import('@/views/topic/detail.vue')
      },
      {
        path: 'add',
        name: 'TopicAdd',
        component: () => import('@/views/topic/add.vue')
      },
      {
        path: 'edit/:id',
        name: 'TopicEdit',
        component: () => import('@/views/topic/edit.vue')
      }
    ]
  },
  {
    path: '/user',
    component: MainLayout,
    children: [
      {
        path: '', // 默认子路由
        name: 'User',
        component: () => import('@/views/user/index.vue')
      },
    ]
  },
  {
    path: '/search',
    component: MainLayout,
    children: [
      {
        path: '', // 默认子路由
        name: 'Search',
        component: () => import('@/views/search/index.vue'),
        meta: {
          hideSearch: true
        }
      },
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/auth/login.vue')
  },
  {
    path: '/scanner',
    name: 'Scanner',
    component: () => import('@/views/scanner.vue')
  },
  {
    path: '/message',
    component: MainLayout,
    children: [
      {
        path: '',
        name: 'Message',
        component: () => import('@/views/message/index.vue'),
        meta: {
          requiresAuth: true
        }
      }
    ]
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/views/error/404.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const store = useUserStore()
  
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!store.isLogin) {
      next({
        path: '/login',
        query: { redirect: to.fullPath }
      })
    } else {
      next()
    }
  } else {
    next()
  }
})

export default router 