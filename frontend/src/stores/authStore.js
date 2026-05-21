import { defineStore } from 'pinia'
import { authApi } from '../api/auth'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null,
    loaded: false
  }),

  getters: {
    isLogin: state => !!state.user,
    displayName: state => state.user?.name || state.user?.writer || state.user?.userId || ''
  },

  actions: {
    async fetchMe() {
      try {
        this.user = await authApi.me()
      } catch {
        this.user = null
      } finally {
        this.loaded = true
      }
    },

    async login(payload) {
      const user = await authApi.login(payload)

      console.log('로그인 응답:', user)

      this.user = user
      this.loaded = true

      return user
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
      }
    }
  }
})