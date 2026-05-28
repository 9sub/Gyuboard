<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { boardApi } from '../api/boards'
import ErrorMessage from '../components/ErrorMessage.vue'

const router = useRouter()

const error = ref('')
const imageFile = ref(null)
const previewUrl = ref('')

const form = reactive({
  title: '',
  guecontents: ''
})

function onImageChange(event) {
  const file = event.target.files[0]

  if (!file) {
    imageFile.value = null
    previewUrl.value = ''
    return
  }

  if (!file.type.startsWith('image/')) {
    error.value = '이미지 파일만 업로드할 수 있습니다.'
    event.target.value = ''
    return
  }

  imageFile.value = file
  previewUrl.value = URL.createObjectURL(file)
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
      [JSON.stringify({
        title: form.title,
        guecontents: form.guecontents
      })],
      { type: 'application/json' }
    )

    formData.append('board', boardBlob)

    if (imageFile.value) {
      formData.append('image', imageFile.value)
    }

    await boardApi.create(formData)

    router.push('/board/list')
  } catch (e) {
    console.error('게시글 작성 실패:', e)
    console.error('응답:', e.response?.data)

    error.value =
      e.response?.data?.message ||
      e.message ||
      '게시글 작성에 실패했습니다.'
  }
}
</script>

<template>
  <main class="write-page">
    <section class="write-hero">
      <div class="write-hero-inner">
        <p class="eyebrow">게시판</p>
        <h1>게시글 작성</h1>
        <p>사진과 함께 게시글을 작성할 수 있어요.</p>
      </div>
    </section>

    <section class="write-container">
      <ErrorMessage :message="error" />

      <form class="write-card" @submit.prevent="submit">
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
          <label>사진</label>
          <input
            type="file"
            accept="image/*"
            class="form-control"
            @change="onImageChange"
          />
        </div>

        <div v-if="previewUrl" class="image-preview-box">
          <img :src="previewUrl" alt="미리보기" />
        </div>

        <div class="form-actions">
          <button
            type="button"
            class="btn btn-light"
            @click="router.push('/board/list')"
          >
            취소
          </button>

          <button class="btn btn-primary">
            등록
          </button>
        </div>
      </form>
    </section>
  </main>
</template>