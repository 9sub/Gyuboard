<script setup>
import { reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '../stores/authStore'
import ErrorMessage from '../components/ErrorMessage.vue'

const route = useRoute()
const router = useRouter()
const auth = useAuthStore()
const error = ref('')
const form = reactive({ userId: '', password: '' })

async function submit() {
  error.value = ''

  try {
    await auth.login({
      writer: form.userId,
      userId: form.userId,
      password: form.password
    })

    console.log('로그인 성공, 이동 시도')

    router.push(route.query.redirect || '/board/list')
  } catch (e) {
    console.error('로그인 실패:', e)
    error.value = e.message || '로그인에 실패했습니다.'
  }
}
</script>

<template>
  <main class="auth-wrap">
    <section class="auth-card">
      <h1 class="auth-title">로그인</h1>
      <p class="auth-subtitle">로그인 후 게시판을 이용할 수 있어요.</p>

      <ErrorMessage :message="error" />

      <form @submit.prevent="submit">
        <div class="form-group">
          <label>아이디</label>
          <input v-model="form.userId" class="form-control" required />
        </div>

        <div class="form-group">
          <label>비밀번호</label>
          <input v-model="form.password" type="password" class="form-control" required />
        </div>

        <button class="btn btn-primary btn-full">로그인</button>
      </form>

      <p class="auth-footer">
        계정이 없나요?
        <RouterLink to="/member/join">회원가입</RouterLink>
      </p>
    </section>
  </main>
</template>