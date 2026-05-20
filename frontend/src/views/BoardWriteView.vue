<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/authStore'
import { boardApi } from '../api/boards'
import ErrorMessage from '../components/ErrorMessage.vue'

const router = useRouter()
const auth = useAuthStore()
const error = ref('')
const form = reactive({ title: '', guecontents: '' })

async function submit() {
  error.value = ''
  try {
    await boardApi.create(form)
    router.push('/board/list')
  } catch (e) { error.value = e.message }
}
</script>
<template>
  <main class="container">
    <h1 class="page-title">새 글 작성</h1>
    <p class="page-desc">공유하고 싶은 내용을 자유롭게 작성해보세요.</p>
    <ErrorMessage :message="error" />
    <section class="card">
      <form @submit.prevent="submit">
        <div class="form-group"><label>작성자</label><input :value="auth.displayName" class="form-control" readonly /></div>
        <div class="form-group"><label>제목</label><input v-model="form.title" class="form-control" required /></div>
        <div class="form-group"><label>내용</label><textarea v-model="form.guecontents" class="form-control" required /></div>
        <div class="action-row"><button class="btn btn-primary">등록</button><RouterLink to="/board/list" class="btn btn-secondary">목록으로</RouterLink></div>
      </form>
    </section>
  </main>
</template>
