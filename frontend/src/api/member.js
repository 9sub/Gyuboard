import { http } from './http'

export const memberApi = {
  me: () =>
    http.get('/api/member/me').then(res => res.data),

  updateMe: payload =>
    http.put('/api/member/me', payload).then(res => res.data),

  likedBoards: () =>
    http.get('/api/member/me/likes').then(res => res.data),

  bookmarkedBoards: () =>
    http.get('/api/member/me/bookmarks').then(res => res.data)
}