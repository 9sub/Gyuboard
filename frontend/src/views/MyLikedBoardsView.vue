<script setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { memberApi } from '../api/member'
import ErrorMessage from '../components/ErrorMessage.vue'

const router = useRouter()

const boards = ref([])
const error = ref('')
const loading = ref(true)

async function load() {
  try {
    error.value = ''
    loading.value = true

    boards.value = await memberApi.likedBoards()
  } catch (e) {
    console.error('좋아요 목록 조회 실패:', e)
    error.value =
      e.response?.data?.message ||
      e.message ||
      '좋아요 목록 조회에 실패했습니다.'
  } finally {
    loading.value = false
  }
}

function detail(id) {
  router.push(`/board/detail/${id}`)
}

function formatDate(value) {
  if (!value) return ''
  return String(value).replace('T', ' ').slice(0, 16)
}

onMounted(load)
</script>

<template>
  <main class="container">
    <h1 class="page-title">좋아요한 게시글</h1>
    <p class="page-desc">내가 좋아요를 누른 게시글 목록입니다.</p>

    <ErrorMessage :message="error" />

    <section class="card table-card">
      <table class="board-table">
        <thead>
          <tr>
            <th>번호</th>
            <th>작성자</th>
            <th>제목</th>
            <th>작성일</th>
            <th>조회수</th>
            <th>좋아요</th>
          </tr>
        </thead>

        <tbody>
          <tr v-if="loading">
            <td colspan="6" class="empty-text">
              불러오는 중입니다.
            </td>
          </tr>

          <tr
            v-for="(board, index) in boards"
            :key="board.id"
            @click="detail(board.id)"
          >
            <td>{{ index + 1 }}</td>
            <td>{{ board.name || board.writer }}</td>

            <td class="title-cell">
              <span class="board-title-text">{{ board.title }}</span>
              <span
                v-if="board.commentCount > 0"
                class="comment-count"
              >
                {{ board.commentCount }}
              </span>
            </td>

            <td>{{ formatDate(board.writedate) }}</td>
            <td>{{ board.viewCount ?? 0 }}</td>
            <td>{{ board.likeCount ?? 0 }}</td>
          </tr>

          <tr v-if="!loading && boards.length === 0">
            <td colspan="6" class="empty-text">
              좋아요한 게시글이 없습니다.
            </td>
          </tr>
        </tbody>
      </table>
    </section>

    <div class="action-row">
      <button class="btn btn-light" @click="router.push('/my')">
        내정보로
      </button>
      <button class="btn btn-primary" @click="router.push('/board/list')">
        게시글 목록
      </button>
    </div>
  </main>
</template>