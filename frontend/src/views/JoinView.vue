<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/authStore'
import ErrorMessage from '../components/ErrorMessage.vue'

const router = useRouter()
const auth = useAuthStore()
const error = ref('')

const form = reactive({
  writer: '',
  password: '',
  name: '',
  email: ''
})

async function submit() {
  error.value = ''

  try {
    await auth.join({
      writer: form.writer,
      password: form.password,
      name: form.name,
      email: form.email
    })

    router.push('/member/login')
  } catch (e) {
    error.value =
      e.response?.data?.message ||
      e.message ||
      '회원가입에 실패했습니다.'
  }
}
</script>

<template>
  <main class="auth-wrap">
    <section class="auth-card">
      <h1 class="auth-title">회원가입</h1>
      <p class="auth-subtitle">간단한 정보만 입력하면 바로 사용할 수 있어요.</p>

      <ErrorMessage :message="error" />

      <form @submit.prevent="submit">
        <div class="form-group">
          <label>아이디</label>
          <input v-model="form.writer" class="form-control" required />
        </div>

        <div class="form-group">
          <label>비밀번호</label>
          <input v-model="form.password" type="password" class="form-control" required />
        </div>

        <div class="form-group">
          <label>이름</label>
          <input v-model="form.name" class="form-control" required />
        </div>

        <div class="form-group">
          <label>이메일</label>
          <input v-model="form.email" type="email" class="form-control" />
        </div>

        <button class="btn btn-primary btn-full">가입하기</button>
      </form>

      <p class="auth-footer">
        이미 계정이 있나요?
        <RouterLink to="/member/login">로그인</RouterLink>
      </p>
    </section>
  </main>
</template>