<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from './store/auth'

const drawer = ref(false)
const authStore = useAuthStore()
const router = useRouter()

const isAuthenticated = computed(() => authStore.isAuthenticated)
const currentUser = computed(() => authStore.currentUser)

const toggleDrawer = () => {
  drawer.value = !drawer.value
}

const logout = async () => {
  try {
    await authStore.logout()
    router.push('/login')
  } catch (error) {
    console.error('Logout failed:', error)
  }
}

const navItems = [
  { title: 'Home', icon: 'mdi-home', path: '/' },
  { title: 'Exhibits', icon: 'mdi-image-multiple', path: '/exhibits' },
  { title: 'About', icon: 'mdi-information', path: '/about' },
  { title: 'Contact', icon: 'mdi-email', path: '/contact' }
]
</script>

<template>
  <v-app>
    <!-- Navigation Drawer -->
    <v-navigation-drawer v-model="drawer" temporary>
      <v-list>
        <v-list-item
          prepend-avatar="https://randomuser.me/api/portraits/men/78.jpg"
          :title="currentUser?.displayName || 'Guest'"
          :subtitle="currentUser?.email || ''"
        ></v-list-item>

        <v-divider></v-divider>

        <v-list-item
          v-for="item in navItems"
          :key="item.title"
          :to="item.path"
          :prepend-icon="item.icon"
          :title="item.title"
        ></v-list-item>
      </v-list>
    </v-navigation-drawer>

    <!-- App Bar -->
    <v-app-bar color="primary" density="compact">
      <template v-slot:prepend>
        <v-app-bar-nav-icon @click="toggleDrawer"></v-app-bar-nav-icon>
      </template>

      <v-app-bar-title>Museum of Natural History</v-app-bar-title>

      <template v-slot:append>
        <v-btn v-if="isAuthenticated" icon @click="logout">
          <v-icon>mdi-logout</v-icon>
        </v-btn>
        <v-btn v-else :to="'/login'" icon>
          <v-icon>mdi-login</v-icon>
        </v-btn>
      </template>
    </v-app-bar>

    <!-- Main Content -->
    <v-main>
      <v-container fluid>
        <router-view />
      </v-container>
    </v-main>

    <!-- Footer -->
    <v-footer app class="bg-primary text-center d-flex flex-column">
      <div>
        <v-btn
          v-for="icon in ['mdi-facebook', 'mdi-twitter', 'mdi-instagram']"
          :key="icon"
          class="mx-2"
          icon
          variant="text"
        >
          <v-icon>{{ icon }}</v-icon>
        </v-btn>
      </div>
      <div class="pt-2">
        Museum of Natural History &copy; {{ new Date().getFullYear() }}
      </div>
    </v-footer>
  </v-app>
</template>

<style>
/* Global styles */
</style>
