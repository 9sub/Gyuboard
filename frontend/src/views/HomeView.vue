<script setup>
import { useAuthStore } from '../stores/authStore'
const auth = useAuthStore()
</script>
<template>
  <main class="hero">
    <section class="hero-card">
      <h1>깔끔하게 기록하고<br />가볍게 소통하세요.</h1>
      <p>Spring REST API와 Vue SPA로 동작하는 심플 게시판입니다.</p>
      <div class="action-row center">
        <template v-if="auth.isLogin">
          <RouterLink to="/board/list" class="btn btn-primary">게시글 보러가기</RouterLink>
          <RouterLink to="/board/write" class="btn btn-secondary">글쓰기</RouterLink>
        </template>
        <template v-else>
          <RouterLink to="/member/login" class="btn btn-primary">로그인하고 시작하기</RouterLink>
          <RouterLink to="/member/join" class="btn btn-secondary">회원가입</RouterLink>
        </template>
      </div>
    </section>
  </main>
</template>
