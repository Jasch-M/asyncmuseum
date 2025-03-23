import { defineStore } from 'pinia'
import { 
  auth, 
  googleProvider, 
  facebookProvider 
} from '../plugins/firebase'
import { 
  signInWithPopup, 
  signOut, 
  onAuthStateChanged, 
  User, 
  AuthProvider 
} from 'firebase/auth'

interface AuthState {
  user: User | null
  loading: boolean
  error: string | null
}

export const useAuthStore = defineStore('auth', {
  state: (): AuthState => ({
    user: null,
    loading: false,
    error: null
  }),

  getters: {
    isAuthenticated: (state) => !!state.user,
    currentUser: (state) => state.user
  },

  actions: {
    async loginWithGoogle() {
      return this.loginWithProvider(googleProvider)
    },

    async loginWithFacebook() {
      return this.loginWithProvider(facebookProvider)
    },


    async loginWithProvider(provider: AuthProvider) {
      this.loading = true
      this.error = null

      try {
        const result = await signInWithPopup(auth, provider)
        this.user = result.user
        return result.user
      } catch (error: any) {
        this.error = error.message || 'Failed to login'
        throw error
      } finally {
        this.loading = false
      }
    },

    async logout() {
      this.loading = true
      this.error = null

      try {
        await signOut(auth)
        this.user = null
      } catch (error: any) {
        this.error = error.message || 'Failed to logout'
        throw error
      } finally {
        this.loading = false
      }
    },

    async initAuth() {
      return new Promise<User | null>((resolve) => {
        const unsubscribe = onAuthStateChanged(auth, (user) => {
          this.user = user
          resolve(user)
          unsubscribe()
        })
      })
    }
  }
})
