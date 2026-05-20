import { http } from './http'

export const authApi = {
  me: () => http.get('/api/me').then(res => res.data),
  login: payload => http.post('/api/member/login', payload).then(res => res.data),
  join: payload => http.post('/api/member/join', payload).then(res => res.data),
  logout: () => http.post('/api/member/logout').then(res => res.data)
}
