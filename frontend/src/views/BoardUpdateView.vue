<script setup>
import { onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { boardApi } from '../api/boards'
import ErrorMessage from '../components/ErrorMessage.vue'

const route = useRoute()
const router = useRouter()

const boardId = route.params.id

const error = ref('')
const loading = ref(true)

const imageFile = ref(null)
const previewUrl = ref('')
const originalImageUrl = ref('')

const form = reactive({
  title: '',
  guecontents: ''
})

function imageSrc(path) {
  if (!path) return ''

  if (path.startsWith('http')) {
    return path
  }

  return path
}

async function loadBoard() {
  try {
    error.value = ''
    loading.value = true

    const response = await boardApi.detail(boardId)

    const board =
      response.board ||
      response.boardDto ||
      response.boarddto ||
      response.data ||
      null

    if (!board) {
      error.value = '게시글 정보를 불러오지 못했습니다.'
      return
    }

    form.title = board.title || ''
    form.guecontents = board.guecontents || ''
    originalImageUrl.value = board.imageUrl || ''
    previewUrl.value = board.imageUrl || ''
  } catch (e) {
    console.error('수정 게시글 조회 실패:', e)
    console.error('응답:', e.response?.data)

    error.value =
      e.response?.data?.message ||
      e.message ||
      '게시글 정보를 불러오지 못했습니다.'
  } finally {
    loading.value = false
  }
}

function onImageChange(event) {
  const file = event.target.files[0]

  if (!file) {
    imageFile.value = null
    previewUrl.value = originalImageUrl.value
    return
  }

  if (!file.type.startsWith('image/')) {
    error.value = '이미지 파일만 업로드할 수 있습니다.'
    event.target.value = ''
    return
  }

  const maxSize = 10 * 1024 * 1024

  if (file.size > maxSize) {
    error.value = '이미지는 10MB 이하만 업로드할 수 있습니다.'
    event.target.value = ''
    imageFile.value = null
    previewUrl.value = originalImageUrl.value
    return
  }

  imageFile.value = file
  previewUrl.value = URL.createObjectURL(file)
}

function cancelImageChange(event) {
  imageFile.value = null
  previewUrl.value = originalImageUrl.value

  if (event?.target) {
    event.target.value = ''
  }
}

async function submit() {
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

    const formData = new FormData()

    const boardBlob = new Blob(
      [
        JSON.stringify({
          title: form.title,
          guecontents: form.guecontents
        })
      ],
      { type: 'application/json' }
    )

    formData.append('board', boardBlob)

    if (imageFile.value) {
      formData.append('image', imageFile.value)
    }

    await boardApi.update(boardId, formData)

    router.push(`/board/detail/${boardId}`)
  } catch (e) {
    console.error('게시글 수정 실패:', e)
    console.error('응답:', e.response?.data)

    error.value =
      e.response?.data?.message ||
      e.message ||
      '게시글 수정에 실패했습니다.'
  }
}

onMounted(loadBoard)
</script>

<template>
  <main class="write-page">
    <section class="write-hero">
      <div class="write-hero-inner">
        <p class="eyebrow">게시판</p>
        <h1>게시글 수정</h1>
        <p>게시글 내용과 사진을 수정할 수 있어요.</p>
      </div>
    </section>

    <section class="write-container">
      <ErrorMessage :message="error" />

      <section v-if="loading" class="write-card">
        게시글 정보를 불러오는 중입니다.
      </section>

      <form v-else class="write-card" @submit.prevent="submit">
        <div class="form-group">
          <label>제목</label>
          <input
            v-model="form.title"
            class="form-control"
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

        <div class="form-group">
          <label>사진 변경</label>
          <input
            type="file"
            accept="image/*"
            class="form-control"
            @change="onImageChange"
          />
        </div>

        <div v-if="previewUrl" class="image-preview-box">
          <img :src="imageSrc(previewUrl)" alt="이미지 미리보기" />
        </div>

        <div
          v-if="imageFile"
          class="action-row"
        >
          <button
            type="button"
            class="btn btn-light"
            @click="cancelImageChange"
          >
            이미지 변경 취소
          </button>
        </div>

        <div class="form-actions">
          <button
            type="button"
            class="btn btn-light"
            @click="router.push(`/board/detail/${boardId}`)"
          >
            취소
          </button>

          <button class="btn btn-primary">
            수정 완료
          </button>
        </div>
      </form>
    </section>
  </main>
</template>