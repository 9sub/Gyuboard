<script setup>
import { RouterLink, RouterView, useRouter } from 'vue-router'
import { useAuthStore } from './stores/authStore'

const router = useRouter()
const auth = useAuthStore()

async function logout() {
  await auth.logout()
  router.push('/member/login')
}
</script>

<template>
  <header class="site-header">
    <nav class="navbar">
      <RouterLink to="/" class="logo">GUE Board</RouterLink>
      <div class="nav-menu">
        <RouterLink to="/" class="nav-link">홈</RouterLink>
        <template v-if="auth.isLogin">
          <RouterLink to="/board/list" class="nav-link">게시글 목록</RouterLink>
          <RouterLink to="/board/write" class="nav-button">글쓰기</RouterLink>
          <span class="nav-user">{{ auth.displayName }}님</span>
          <button type="button" class="nav-link button-link" @click="logout">로그아웃</button>
        </template>
        <template v-else>
          <RouterLink to="/member/login" class="nav-link">로그인</RouterLink>
          <RouterLink to="/member/join" class="nav-button">회원가입</RouterLink>
        </template>
      </div>
    </nav>
  </header>
  <RouterView />
</template>
