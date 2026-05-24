import { http } from './http'

export const commentApi = {
  write: (boardId, payload) =>
    http.post(`/api/boards/${boardId}/comments`, payload).then(res => res.data),

  update: (commentId, payload) =>

    http.put(`/api/comments/${commentId}`, payload).then(res => res.data),
  
  delete: commentId =>
    http.delete(`/api/comments/${commentId}`).then(res => res.data)
}