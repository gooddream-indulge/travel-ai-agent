import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/Home.vue'),
    meta: {
      title: 'Gooddream的AI超级智能体应用平台 - 首页',
      description: 'Gooddream的AI超级智能体应用平台提供AI旅小智和AI超级旅小智服务，满足您的各种AI对话需求'
    }
  },
  {
    path: '/travel-master',
    name: 'TravelMaster',
    component: () => import('../views/TravelMaster.vue'),
    meta: {
      title: 'AI旅小智 - GoodDream的AI超级智能体应用平台',
      description: 'Gooddream的AI超级智能体应用平台提供AI旅小智和AI超级旅小智服务，满足您的各种AI对话需求'
    }
  },
  {
    path: '/super-agent',
    name: 'SuperAgent',
    component: () => import('../views/SuperAgent.vue'),
    meta: {
      title: 'AI超级旅小智 - GoodDream的AI超级智能体应用平台',
      description: 'Gooddream的AI超级智能体应用平台提供AI旅小智和AI超级旅小智服务，满足您的各种AI对话需求'
    }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 全局导航守卫，设置文档标题
router.beforeEach((to, from, next) => {
  // 设置页面标题
  if (to.meta.title) {
    document.title = to.meta.title
  }
  next()
})

export default router 