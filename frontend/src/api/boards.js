import { http } from './http'

export const boardApi = {
  list: params => http.get('/api/boards', { params }).then(res => res.data),
  detail: id => http.get(`/api/boards/${id}`).then(res => res.data),
  create: payload => http.post('/api/boards', payload).then(res => res.data),
  update: (id, payload) => http.put(`/api/boards/${id}`, payload).then(res => res.data),
  delete: id => http.delete(`/api/boards/${id}`).then(res => res.data),
  createComment: (boardId, payload) => http.post(`/api/boards/${boardId}/comments`, payload).then(res => res.data),
  removeComment: commentId => http.delete(`/api/comments/${commentId}`).then(res => res.data),
  like: id =>
    http.post(`/api/boards/${id}/like`).then(res => res.data)
}
        