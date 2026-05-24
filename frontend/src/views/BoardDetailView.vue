<script setup>
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { boardApi } from '../api/boards'
import { commentApi } from '../api/comments'
import { useAuthStore } from '../stores/authStore'

const auth = useAuthStore()

const route = useRoute()
const router = useRouter()

const board = ref(null)
const comments = ref([])
const commentContent = ref('')
const error = ref('')



const boardId = route.params.id


const editingCommentId = ref(null)

const editingCommentContent = ref('')

function startEditComment(comment) {

  editingCommentId.value = comment.commentId

  editingCommentContent.value = comment.content

}

function cancelEditComment() {

  editingCommentId.value = null

  editingCommentContent.value = ''

}
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
  router.push(`/board/update/${boardId}`)
}

async function deleteBoard() {

  if (!confirm('정말 삭제하시겠습니까?')) {

    return

  }

  try {

    await boardApi.delete(boardId)

    router.push('/board/list')

  } catch (e) {

    error.value =

      e.response?.data?.message ||

      e.message ||

      '게시글 삭제에 실패했습니다.'

  }

}

async function deleteComment(commentId) {
  console.log('댓글 삭제 클릭 commentId:', commentId)

  if (!confirm('댓글을 삭제하시겠습니까?')) {
    return
  }

  try {
    const result = await commentApi.delete(commentId)

    console.log('댓글 삭제 성공:', result)

    await fetchDetail()
  } catch (e) {
    console.error('댓글 삭제 실패:', e)
    console.error('응답:', e.response?.data)

    error.value =
      e.response?.data?.message ||
      e.message ||
      '댓글 삭제에 실패했습니다.'
  }
}


async function updateComment(commentId) {
  if (!editingCommentContent.value.trim()) {
    error.value = '댓글 내용을 입력해주세요.'
    return
  }

  try {
    error.value = ''

    await commentApi.update(commentId, {
      content: editingCommentContent.value
    })

    editingCommentId.value = null
    editingCommentContent.value = ''

    await fetchDetail()
  } catch (e) {
    console.error('댓글 수정 실패:', e)
    console.error('응답:', e.response?.data)

    error.value =
      e.response?.data?.message ||
      e.message ||
      '댓글 수정에 실패했습니다.'
  }
}

onMounted(fetchDetail)
</script>

<template>
  <main class="detail-page">
    <section class="detail-hero">
      <div class="detail-hero-inner">
        <p class="eyebrow">게시판</p>
        <h1>게시글 상세</h1>
        <p>게시글 내용과 댓글을 확인할 수 있어요.</p>
      </div>
    </section>

    <section class="detail-container">
      <p v-if="error" class="error-message">
        {{ error }}
      </p>

      <article v-if="board" class="post-card">
        <header class="post-header">
          <h2 class="post-title">
            {{ board.title }}
          </h2>

          <div class="post-meta">
            <div class="meta-item">
              <span class="meta-label">작성자</span>
              <strong>{{ board.name || board.writer }}</strong>
            </div>

            <div class="meta-item">
              <span class="meta-label">조회수</span>
              <strong>{{ board.viewCount }}</strong>
            </div>

            <div class="meta-item">
              <span class="meta-label">작성일</span>
              <strong>{{ board.writedate }}</strong>
            </div>
          </div>
        </header>

        <div class="post-content">
          <p>
            {{ board.guecontents }}
          </p>
        </div>

        <footer class="post-actions">
          <button class="btn btn-light" @click="goList">
            목록
          </button>

          <button class="btn btn-primary" @click="goUpdate">
            수정
          </button>

          <button class="btn btn-danger" @click="deleteBoard">
            삭제
          </button>
        </footer>
      </article>

      <section v-else class="post-card loading-card">
        게시글을 불러오는 중입니다.
      </section>

      <section class="comment-card">
        <div class="comment-header">
          <div>
            <h2>댓글</h2>
            <p>{{ comments.length }}개의 댓글이 있습니다.</p>
          </div>
        </div>

        <form @submit.prevent="submitComment" class="comment-form">
          <textarea
            v-model="commentContent"
            class="comment-textarea"
            placeholder="댓글을 입력하세요"
          ></textarea>

          <button class="btn btn-primary comment-submit">
            댓글 등록
          </button>
        </form>

        <div v-if="comments.length === 0" class="empty-message">
          아직 등록된 댓글이 없습니다.
        </div>

        <ul v-else class="comment-list">
          <li
            v-for="comment in comments"
            :key="comment.commentId"
            class="comment-item"
          >
            <div class="comment-meta">
              <strong>{{ comment.name || comment.writer }}</strong>
              <span>{{ comment.writedate }}</span>
            </div>
          
            <div v-if="editingCommentId === comment.commentId" class="comment-edit-box">
              <textarea
                v-model="editingCommentContent"
                class="comment-textarea"
              ></textarea>
            
              <div class="comment-actions">
                <button
                  type="button"
                  class="btn btn-primary comment-action-btn"
                  @click="updateComment(comment.commentId)"
                >
                  수정 완료
                </button>
              
                <button
                  type="button"
                  class="btn btn-light comment-action-btn"
                  @click="cancelEditComment"
                >
                  취소
                </button>
              </div>
            </div>
          
            <template v-else>
              <p class="comment-content">
                {{ comment.content }}
              </p>
            
              <div
                v-if="auth.user && Number(auth.user.userId) === Number(comment.userId)"
                class="comment-actions"
              >
                <button
                  type="button"
                  class="btn btn-primary comment-action-btn"
                  @click="startEditComment(comment)"
                >
                  수정
                </button>
              
                <button
                  type="button"
                  class="btn btn-danger comment-action-btn"
                  @click="deleteComment(comment.commentId)"
                >
                  삭제
                </button>
              </div>
            </template>
          </li>
        </ul>
      </section>
    </section>
  </main>
</template>