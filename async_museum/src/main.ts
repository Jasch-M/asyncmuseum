import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'
import pinia from './store'
import vuetify from './plugins/vuetify'
import '@mdi/font/css/materialdesignicons.css'
import { useAuthStore } from './store/auth'

// Create Vue app
const app = createApp(App)

// Use plugins
app.use(router)
app.use(pinia)
app.use(vuetify)

// Initialize auth and mount app
const authStore = useAuthStore()
authStore.initAuth().then(() => {
  app.mount('#app')
})
