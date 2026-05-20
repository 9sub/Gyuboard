import axios from 'axios'

export const http = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '',
  withCredentials: true,
  headers: { 'Content-Type': 'application/json' }
})

http.interceptors.response.use(
  response => response,
  error => {
    const status = error.response?.status
    const message = error.response?.data?.message || error.response?.data || error.message || '요청 처리 중 오류가 발생했습니다.'
    return Promise.reject({ status, message, raw: error })
  }
)
