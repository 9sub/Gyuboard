<script setup>
import { onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { memberApi } from '../api/member'
import { useAuthStore } from '../stores/authStore'
import ErrorMessage from '../components/ErrorMessage.vue'

const router = useRouter()

const error = ref('')
const success = ref('')
const loading = ref(true)

const auth = useAuthStore()

const form = reactive({
  writer: '',
  name: '',
  email: '',
  regidate: ''
})

async function loadMe() {
  try {
    error.value = ''
    loading.value = true

    const me = await memberApi.me()

    form.writer = me.writer || ''
    form.name = me.name || ''
    form.email = me.email || ''
    form.regidate = me.regidate || ''
  } catch (e) {
    console.error('내정보 조회 실패:', e)
    error.value =
      e.response?.data?.message ||
      e.message ||
      '내정보 조회에 실패했습니다.'
  } finally {
    loading.value = false
  }
}

async function submit() {
  if (!form.name.trim()) {
    error.value = '이름을 입력해주세요.'
    return
  }

  try {
    error.value = ''
    success.value = ''

    const updated = await memberApi.updateMe({
      name: form.name,
      email: form.email
    })

    form.name = updated.name || ''
    form.email = updated.email || ''

    auth.user = updated

    success.value = '내정보가 수정되었습니다.'
  } catch (e) {
    console.error('내정보 수정 실패:', e)

    error.value =
      e.response?.data?.message ||
      e.message ||
      '내정보 수정에 실패했습니다.'
  }
}

function formatDateTime(value) {
  if (!value) return '-'

  return String(value)
    .replace('T', ' ')
    .slice(0, 16)
}

onMounted(loadMe)
</script>

<template>
  <main class="container">
    <h1 class="page-title">내정보</h1>
    <p class="page-desc">회원 정보를 확인하고 수정할 수 있어요.</p>

    <ErrorMessage :message="error" />

    <p v-if="success" class="success-message">
      {{ success }}
    </p>

    <section v-if="loading" class="card">
      내정보를 불러오는 중입니다.
    </section>

    <section v-else class="my-page-grid">
      <form class="card my-profile-card" @submit.prevent="submit">
        <h2 class="sub-title">프로필 수정</h2>

        <div class="form-group">
          <label>아이디</label>
          <input
            v-model="form.writer"
            class="form-control"
            disabled
          />
        </div>

        <div class="form-group">
          <label>이름</label>
          <input
            v-model="form.name"
            class="form-control"
            required
          />
        </div>

        <div class="form-group">
          <label>이메일</label>
          <input
            v-model="form.email"
            type="email"
            class="form-control"
          />
        </div>

        <div class="form-group">
          <label>가입일</label>
          <input
            :value="formatDateTime(form.regidate)"
            class="form-control"
            disabled
          />
        </div>

        <button class="btn btn-primary btn-full">
          저장하기
        </button>
      </form>

      <aside class="card my-menu-card">
        <h2 class="sub-title">활동 목록</h2>

        <button
          type="button"
          class="my-menu-button"
          @click="router.push('/my/likes')"
        >
          <span>♥</span>
          좋아요한 게시글
        </button>

        <button
          type="button"
          class="my-menu-button bookmark"
          @click="router.push('/my/bookmarks')"
        >
          <span>★</span>
          북마크한 게시글
        </button>
      </aside>
    </section>
  </main>
</template>