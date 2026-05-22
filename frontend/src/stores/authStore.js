import { defineStore } from 'pinia'
import { authApi } from '../api/auth'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null,
    loaded: false
  }),

  getters: {
    isLogin: state => !!state.user,
    displayName: state =>
      state.user?.name || state.user?.writer || state.user?.userId || ''
  },

  actions: {
    async fetchMe() {
      try {
        const user = await authApi.me()
        this.user = user
      } catch {
        this.user = null
        localStorage.removeItem('accessToken')
      } finally {
        this.loaded = true
      }
    },

    async login(payload) {
      const response = await authApi.login(payload)

      console.log('로그인 응답:', response)

      localStorage.setItem('accessToken', response.token)

      this.user = response.user
      this.loaded = true

      return response
    },

    async join(payload) {
      await authApi.join(payload)
    },

    async logout() {
      try {
        await authApi.logout()
      } finally {
        this.user = null
        this.loaded = true
        localStorage.removeItem('accessToken')
      }
    }
  }
})