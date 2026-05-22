<script setup>
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { boardApi } from '../api/boards'
import { commentApi } from '../api/comments'

const route = useRoute()
const router = useRouter()

const board = ref(null)
const comments = ref([])
const commentContent = ref('')
const error = ref('')

const boardId = route.params.id

async function fetchDetail() {
  try {
    error.value = ''

    console.log('상세 route.params:', route.params)
    console.log('상세 boardId:', boardId)

    const response = await boardApi.detail(boardId)

    console.log('상세 API 응답:', response)
    console.log('상세 API 응답 JSON:', JSON.stringify(response, null, 2))
    console.log('응답 keys:', Object.keys(response))

    board.value =
      response.board ||
      response.boardDto ||
      response.boarddto ||
      response.data ||
      null

    comments.value = response.comments || []

    console.log('board.value:', board.value)
    console.log('comments.value:', comments.value)

    if (!board.value) {
      error.value = '게시글 데이터가 응답에 없습니다.'
    }
  } catch (e) {
    console.error('상세 조회 실패:', e)
    console.error('상세 조회 실패 응답:', e.response?.data)

    error.value =
      e.response?.data?.message ||
      e.message ||
      '게시글 상세 조회에 실패했습니다.'
  }
}

async function submitComment() {
  if (!commentContent.value.trim()) return

  try {
    await commentApi.write(boardId, {
      content: commentContent.value
    })

    commentContent.value = ''
    await fetchDetail()
  } catch (e) {
    console.error(e)
    error.value =
      e.response?.data?.message ||
      e.message ||
      '댓글 등록에 실패했습니다.'
  }
}

function goList() {
  router.push('/board/list')
}

function goUpdate() {
  router.push(`/boards/update/${boardId}`)
}

onMounted(fetchDetail)
</script>

<template>
  <main class="page-wrap">
    <section class="page-header">
      <h1>게시글 상세</h1>
      <p>게시글 내용과 댓글을 확인할 수 있어요.</p>
    </section>

    <p v-if="error" class="error-message">
      {{ error }}
    </p>

    <section v-if="board" class="card detail-card">
      <h2>{{ board.title }}</h2>

      <div class="detail-meta">
        <span>작성자: {{ board.writer }}</span>
        <span>조회수: {{ board.viewCount }}</span>
        <span>작성일: {{ board.writedate }}</span>
      </div>

      <div class="detail-content">
        {{ board.guecontents }}
      </div>

      <div class="detail-actions">
        <button class="btn" @click="goList">목록</button>
        <button class="btn btn-primary" @click="goUpdate">수정</button>
      </div>
    </section>

    <section v-else class="card">
      게시글을 불러오는 중입니다.
    </section>

    <section class="card comment-card">
      <h2>댓글</h2>

      <form @submit.prevent="submitComment" class="comment-form">
        <textarea
          v-model="commentContent"
          class="form-control"
          placeholder="댓글을 입력하세요"
        ></textarea>

        <button class="btn btn-primary btn-full">
          댓글 등록
        </button>
      </form>

      <div v-if="comments.length === 0" class="empty-message">
        댓글이 없습니다.
      </div>

      <ul v-else class="comment-list">
        <li
          v-for="comment in comments"
          :key="comment.commentId"
          class="comment-item"
        >
          <div class="comment-meta">
            <strong>{{ comment.writer }}</strong>
            <span>{{ comment.writedate }}</span>
          </div>

          <p>{{ comment.content }}</p>
        </li>
      </ul>
    </section>
  </main>
</template>