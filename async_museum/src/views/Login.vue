<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useAuthStore } from '../store/auth'

const authStore = useAuthStore()
const router = useRouter()
const route = useRoute()
const loading = ref(false)
const error = ref<string | null>(null)

// Get redirect path from query parameters or default to home
const redirectPath = computed(() => {
  return (route.query.redirect as string) || '/'
})

const loginWithGoogle = async () => {
  await loginWithProvider(() => authStore.loginWithGoogle())
}

const loginWithFacebook = async () => {
  await loginWithProvider(() => authStore.loginWithFacebook())
}


const loginWithProvider = async (loginFn: () => Promise<any>) => {
  loading.value = true
  error.value = null

  try {
    await loginFn()
    // Redirect to the original requested page or home
    router.push(redirectPath.value)
  } catch (err: any) {
    error.value = err.message || 'An error occurred during login'
    console.error('Login error:', err)
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="6" lg="4">
        <v-card class="elevation-12">
          <v-toolbar color="primary" dark flat>
            <v-toolbar-title>Login to Museum of Natural History</v-toolbar-title>
          </v-toolbar>

          <v-card-text>
            <div class="text-center mb-4">
              <p>Sign in with your social account to access exclusive content and features.</p>
            </div>

            <v-alert
              v-if="error"
              type="error"
              class="mb-4"
              closable
              @click:close="error = null"
            >
              {{ error }}
            </v-alert>

            <v-btn
              block
              color="#4285F4"
              class="mb-3 white--text"
              prepend-icon="mdi-google"
              :loading="loading"
              @click="loginWithGoogle"
            >
              Continue with Google
            </v-btn>

            <v-btn
              block
              color="#3b5998"
              class="mb-3 white--text"
              prepend-icon="mdi-facebook"
              :loading="loading"
              @click="loginWithFacebook"
            >
              Continue with Facebook
            </v-btn>

          </v-card-text>

          <v-card-actions class="justify-center pb-4">
            <p class="text-caption text-center">
              By signing in, you agree to our 
              <a href="#" class="text-decoration-none">Terms of Service</a> and 
              <a href="#" class="text-decoration-none">Privacy Policy</a>
            </p>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
