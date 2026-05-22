<script setup>
import { onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { boardApi } from '../api/boards'

const route = useRoute()
const router = useRouter()

const boardId = route.params.id
const error = ref('')
const loading = ref(true)

const form = reactive({
  title: '',
  guecontents: ''
})

async function fetchBoard() {
  try {
    loading.value = true
    error.value = ''

    const response = await boardApi.detail(boardId)

    console.log('수정 화면 상세 응답:', response)

    const board = response.board || response.boardDto || response.boarddto

    if (!board) {
      error.value = '게시글 정보를 찾을 수 없습니다.'
      return
    }

    form.title = board.title
    form.guecontents = board.guecontents
  } catch (e) {
    console.error(e)
    error.value =
      e.response?.data?.message ||
      e.message ||
      '게시글 정보를 불러오지 못했습니다.'
  } finally {
    loading.value = false
  }
}

async function submitUpdate() {
  if (!form.title.trim()) {
    error.value = '제목을 입력해주세요.'
    return
  }

  if (!form.guecontents.trim()) {
    error.value = '내용을 입력해주세요.'
    return
  }

  try {
    error.value = ''

    await boardApi.update(boardId, {
      title: form.title,
      guecontents: form.guecontents
    })

    router.push(`/board/list`)
  } catch (e) {
    console.error(e)
    error.value =
      e.response?.data?.message ||
      e.message ||
      '게시글 수정에 실패했습니다.'
  }
}

function goBack() {
  router.push(`/boards/detail/${boardId}`)
}

onMounted(fetchBoard)
</script>

<template>
  <main class="write-page">
    <section class="write-hero">
      <div class="write-hero-inner">
        <p class="eyebrow">게시판</p>
        <h1>게시글 수정</h1>
        <p>제목과 내용을 수정할 수 있어요.</p>
      </div>
    </section>

    <section class="write-container">
      <p v-if="error" class="error-message">
        {{ error }}
      </p>

      <section v-if="loading" class="write-card">
        게시글 정보를 불러오는 중입니다.
      </section>

      <form v-else class="write-card" @submit.prevent="submitUpdate">
        <div class="form-group">
          <label>제목</label>
          <input
            v-model="form.title"
            class="form-control"
            type="text"
            placeholder="제목을 입력하세요"
          />
        </div>

        <div class="form-group">
          <label>내용</label>
          <textarea
            v-model="form.guecontents"
            class="form-textarea"
            placeholder="내용을 입력하세요"
          ></textarea>
        </div>

        <div class="form-actions">
          <button type="button" class="btn btn-light" @click="goBack">
            취소
          </button>

          <button type="submit" class="btn btn-primary">
            수정 완료
          </button>
        </div>
      </form>
    </section>
  </main>
</template>