import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/authStore'

import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import JoinView from '../views/JoinView.vue'
import BoardListView from '../views/BoardListView.vue'
import BoardDetailView from '../views/BoardDetailView.vue'
import BoardWriteView from '../views/BoardWriteView.vue'
import BoardUpdateView from '../views/BoardUpdateView.vue'

const routes = [
  { path: '/', name: 'home', component: HomeView },

  { path: '/member/login', name: 'login', component: LoginView },
  { path: '/member/join', name: 'join', component: JoinView },

  { path: '/board/list', name: 'board-list', component: BoardListView, meta: { requiresAuth: true } },
  { path: '/board/write', name: 'board-write', component: BoardWriteView, meta: { requiresAuth: true } },
  { path: '/board/detail/:id', name: 'board-detail', component: BoardDetailView, meta: { requiresAuth: true } },
  { path: '/board/update/:id', name: 'board-update', component: BoardUpdateView, meta: { requiresAuth: true } }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach(async to => {
  const auth = useAuthStore()

  if (!auth.loaded) {
    await auth.fetchMe()
  }

  if (to.meta.requiresAuth && !auth.isLogin) {
    return {
      name: 'login',
      query: { redirect: to.fullPath }
    }
  }
})

export default router