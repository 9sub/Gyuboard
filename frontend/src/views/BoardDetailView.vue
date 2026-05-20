<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '../stores/authStore'
import { boardApi } from '../api/boards'
import ErrorMessage from '../components/ErrorMessage.vue'

const route = useRoute()
const router = useRouter()
const auth = useAuthStore()
const error = ref('')
const board = ref(null)
const comments = ref([])
const commentForm = reactive({ content: '' })
const id = route.params.id

const canEdit = computed(() => {
  if (!auth.user || !board.value) return false
  return auth.user.userId === board.value.userId || auth.user.writer === board.value.writer
})
function fmt(value) { return value ? String(value).replace('T', ' ').slice(0, 16) : '' }
async function load() {
  error.value = ''
  try {
    const data = await boardApi.detail(id)
    board.value = data.board || data
    comments.value = data.comments || []
  } catch (e) { error.value = e.message }
}
async function removeBoard() {
  if (!confirm('게시글을 삭제할까요?')) return
  try {
    await boardApi.remove(id)
    router.push('/board/list')
  } catch (e) { error.value = e.message }
}
async function writeComment() {
  error.value = ''
  try {
    await boardApi.createComment(id, commentForm)
    commentForm.content = ''
    await load()
  } catch (e) { error.value = e.message }
}
async function removeComment(commentId) {
  if (!confirm('댓글을 삭제할까요?')) return
  try {
    await boardApi.removeComment(commentId)
    await load()
  } catch (e) { error.value = e.message }
}
onMounted(load)
</script>
<template>
  <main class="container">
    <h1 class="page-title">게시글 상세</h1>
    <p class="page-desc">게시글 내용과 댓글을 확인할 수 있어요.</p>
    <ErrorMessage :message="error" />
    <section v-if="board" class="card">
      <div class="detail-grid">
        <div class="detail-row"><div class="detail-label">번호</div><div class="detail-value">{{ board.id }}</div></div>
        <div class="detail-row"><div class="detail-label">작성자</div><div class="detail-value">{{ board.writer || board.userId }}</div></div>
        <div class="detail-row"><div class="detail-label">제목</div><div class="detail-value">{{ board.title }}</div></div>
        <div class="detail-row"><div class="detail-label">내용</div><div class="detail-value content-value">{{ board.guecontents }}</div></div>
        <div class="detail-row"><div class="detail-label">작성일</div><div class="detail-value">{{ fmt(board.writedate) }}</div></div>
        <div class="detail-row"><div class="detail-label">조회수</div><div class="detail-value">{{ board.viewCount ?? 0 }}</div></div>
      </div>
      <div class="action-row">
        <RouterLink to="/board/list" class="btn btn-secondary">목록</RouterLink>
        <RouterLink v-if="canEdit" :to="`/board/update/${board.id}`" class="btn btn-primary">수정</RouterLink>
        <button v-if="canEdit" type="button" class="btn btn-danger" @click="removeBoard">삭제</button>
      </div>
    </section>
    <section class="card comment-box">
      <h2 class="sub-title">댓글</h2>
      <form class="comment-form" @submit.prevent="writeComment">
        <textarea v-model="commentForm.content" class="form-control" placeholder="댓글을 입력하세요" required />
        <button class="btn btn-primary">댓글 등록</button>
      </form>
      <div v-if="comments.length" class="comment-list">
        <div v-for="comment in comments" :key="comment.id" class="comment-item">
          <div class="comment-meta"><strong>{{ comment.writer }}</strong><span>{{ fmt(comment.createdAt || comment.writedate) }}</span></div>
          <p>{{ comment.content }}</p>
          <button v-if="auth.user?.writer === comment.writer || auth.user?.userId === comment.userId" class="mini-danger" @click="removeComment(comment.id)">삭제</button>
        </div>
      </div>
      <p v-else class="empty-text">댓글이 없습니다.</p>
    </section>
  </main>
</template>
