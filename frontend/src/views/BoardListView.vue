<script setup>
import { onMounted, reactive, ref, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { boardApi } from '../api/boards'
import ErrorMessage from '../components/ErrorMessage.vue'
import Pagination from '../components/Pagination.vue'

const router = useRouter()
const route = useRoute()
const error = ref('')
const boards = ref([])
const pageDto = ref(null)
const search = reactive({
  page: Number(route.query.page || 1),
  type: route.query.type || 'all',
  keyword: route.query.keyword || '',
  sort: route.query.sort || 'latest'
})

async function load() {
  error.value = ''
  try {
    const data = await boardApi.list(search)
    boards.value = Array.isArray(data) ? data : data.list || []
    pageDto.value = Array.isArray(data) ? null : data.pageDto
  } catch (e) { error.value = e.message }
}
function apply(nextPage = 1) {
  search.page = nextPage
  router.push({ path: '/board/list', query: { ...search } })
}
function detail(id) { router.push(`/board/detail/${id}`) }
function fmt(value) { return value ? String(value).slice(0, 10) : '' }

watch(() => route.query, q => {
  search.page = Number(q.page || 1)
  search.type = q.type || 'all'
  search.keyword = q.keyword || ''
  search.sort = q.sort || 'latest'
  load()
})

function displayNumber(index) {

  if (!pageDto.value) {

    return index + 1

  }

  return pageDto.value.totalCount - ((pageDto.value.page - 1) * pageDto.value.size) - index

}

onMounted(load)
</script>
<template>
  <main class="container">
    <h1 class="page-title">게시글 목록</h1>
    <p class="page-desc">검색 조건을 선택해서 원하는 게시글을 찾아보세요.</p>
    <ErrorMessage :message="error" />
    <form class="search-form" @submit.prevent="apply(1)">
      <select v-model="search.type" class="form-control search-select">
        <option value="all">제목+내용</option><option value="title">제목</option><option value="writer">작성자</option><option value="content">내용</option>
      </select>
      <input v-model="search.keyword" class="form-control search-input" placeholder="검색어를 입력하세요" />
      <div class="search-btn-group"><button class="btn btn-primary">검색</button><button type="button" class="btn btn-secondary" @click="search.keyword=''; search.type='all'; apply(1)">초기화</button></div>
    </form>
    <div class="board-toolbar">
      <select v-model="search.sort" class="sort-select" @change="apply(1)"><option value="latest">최신순</option><option value="oldest">오래된순</option><option value="view">조회수순</option></select>
    </div>
    <section class="card table-card">
      <table class="board-table">
        <thead><tr><th>번호</th><th>작성자</th><th>제목</th><th>내용</th><th>작성일</th><th>조회수</th></tr></thead>
        <tbody>
          <tr v-for="(board, index) in boards" :key="board.id" @click="detail(board.id)">
            <td>{{ displayNumber(index) }}</td><td>{{ board.writer || board.userId }}</td><td class="title-cell">{{ board.title }}</td><td>{{ board.guecontents }}</td><td>{{ fmt(board.writedate) }}</td><td>{{ board.viewCount ?? 0 }}</td>
          </tr>
          <tr v-if="!boards.length"><td colspan="6" class="empty-text">게시글이 없습니다.</td></tr>
        </tbody>
      </table>
    </section>
    <Pagination :page-dto="pageDto" @change="apply" />
  </main>
</template>
