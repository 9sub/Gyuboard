<script setup>
import { onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '../stores/authStore'
import { boardApi } from '../api/boards'
import ErrorMessage from '../components/ErrorMessage.vue'

const route = useRoute()
const router = useRouter()
const auth = useAuthStore()
const error = ref('')
const id = route.params.id
const form = reactive({ title: '', guecontents: '' })
const board = ref(null)

function canEdit(target) {
  return auth.user?.userId === target.userId || auth.user?.writer === target.writer
}
async function load() {
  error.value = ''
  try {
    const data = await boardApi.detail(id)
    board.value = data.board || data
    if (!canEdit(board.value)) {
      router.replace(`/board/detail/${id}`)
      return
    }
    form.title = board.value.title || ''
    form.guecontents = board.value.guecontents || ''
  } catch (e) { error.value = e.message }
}
async function submit() {
  error.value = ''
  try {
    await boardApi.update(id, form)
    router.push(`/board/detail/${id}`)
  } catch (e) { error.value = e.message }
}
onMounted(load)
</script>
<template>
  <main class="container">
    <h1 class="page-title">게시글 수정</h1>
    <p class="page-desc">작성한 내용을 다시 다듬어보세요.</p>
    <ErrorMessage :message="error" />
    <section v-if="board" class="card">
      <form @submit.prevent="submit">
        <div class="form-group"><label>작성자</label><input :value="board.writer || board.userId" class="form-control" readonly /></div>
        <div class="form-group"><label>제목</label><input v-model="form.title" class="form-control" required /></div>
        <div class="form-group"><label>내용</label><textarea v-model="form.guecontents" class="form-control" required /></div>
        <div class="action-row"><button class="btn btn-primary">수정 완료</button><RouterLink :to="`/board/detail/${id}`" class="btn btn-secondary">취소</RouterLink></div>
      </form>
    </section>
  </main>
</template>
