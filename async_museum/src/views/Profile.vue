<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useAuthStore } from '../store/auth'
import { useRouter } from 'vue-router'

const authStore = useAuthStore()
const router = useRouter()
const loading = ref(false)

const user = computed(() => authStore.currentUser)

// Placeholder for user preferences (would be fetched from backend in a real app)
const preferences = ref({
  notifications: true,
  newsletter: false,
  exhibitAlerts: true
})

// Placeholder for user's visited exhibits (would be fetched from backend in a real app)
const visitedExhibits = ref([
  { id: 1, name: 'Dinosaur Fossils', date: '2023-03-15' },
  { id: 2, name: 'Ocean Life', date: '2023-02-28' }
])

// Placeholder for user's upcoming events (would be fetched from backend in a real app)
const upcomingEvents = ref([
  { id: 1, name: 'Night at the Museum', date: '2023-04-15' }
])

const logout = async () => {
  loading.value = true
  try {
    await authStore.logout()
    router.push('/login')
  } catch (error) {
    console.error('Logout failed:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  // Check if user is authenticated
  if (!authStore.isAuthenticated) {
    router.push('/login')
  }
})
</script>

<template>
  <v-container v-if="user">
    <v-row>
      <v-col cols="12">
        <h1 class="text-h3 mb-6">My Profile</h1>
      </v-col>
    </v-row>

    <v-row>
      <!-- User Info Card -->
      <v-col cols="12" md="4">
        <v-card class="mb-4">
          <v-card-title>User Information</v-card-title>
          <v-card-text>
            <v-list>
              <v-list-item>
                <template v-slot:prepend>
                  <v-avatar color="primary">
                    <v-img v-if="user.photoURL" :src="user.photoURL" alt="User Avatar"></v-img>
                    <span v-else class="text-h6 white--text">{{ user.displayName?.charAt(0) || user.email?.charAt(0) || 'U' }}</span>
                  </v-avatar>
                </template>
                <v-list-item-title>{{ user.displayName || 'Museum Member' }}</v-list-item-title>
                <v-list-item-subtitle>{{ user.email }}</v-list-item-subtitle>
              </v-list-item>

              <v-divider class="my-3"></v-divider>

              <v-list-item>
                <template v-slot:prepend>
                  <v-icon color="primary">mdi-account</v-icon>
                </template>
                <v-list-item-title>Member since</v-list-item-title>
                <v-list-item-subtitle>{{ new Date(user.metadata.creationTime).toLocaleDateString() }}</v-list-item-subtitle>
              </v-list-item>

              <v-list-item>
                <template v-slot:prepend>
                  <v-icon color="primary">mdi-login</v-icon>
                </template>
                <v-list-item-title>Last login</v-list-item-title>
                <v-list-item-subtitle>{{ new Date(user.metadata.lastSignInTime).toLocaleDateString() }}</v-list-item-subtitle>
              </v-list-item>
            </v-list>

            <v-btn
              color="error"
              block
              class="mt-4"
              :loading="loading"
              @click="logout"
            >
              Logout
            </v-btn>
          </v-card-text>
        </v-card>
      </v-col>

      <!-- Preferences Card -->
      <v-col cols="12" md="8">
        <v-card class="mb-4">
          <v-card-title>Preferences</v-card-title>
          <v-card-text>
            <v-list>
              <v-list-item>
                <v-checkbox
                  v-model="preferences.notifications"
                  label="Receive notifications"
                  hint="Get notified about important museum updates"
                ></v-checkbox>
              </v-list-item>
              
              <v-list-item>
                <v-checkbox
                  v-model="preferences.newsletter"
                  label="Subscribe to newsletter"
                  hint="Receive our monthly newsletter with museum news"
                ></v-checkbox>
              </v-list-item>
              
              <v-list-item>
                <v-checkbox
                  v-model="preferences.exhibitAlerts"
                  label="Exhibit alerts"
                  hint="Get notified when new exhibits are added"
                ></v-checkbox>
              </v-list-item>
            </v-list>
            
            <v-btn
              color="primary"
              block
              class="mt-4"
            >
              Save Preferences
            </v-btn>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <v-row>
      <!-- Visited Exhibits -->
      <v-col cols="12" md="6">
        <v-card>
          <v-card-title>Visited Exhibits</v-card-title>
          <v-card-text>
            <v-list v-if="visitedExhibits.length > 0">
              <v-list-item
                v-for="exhibit in visitedExhibits"
                :key="exhibit.id"
              >
                <template v-slot:prepend>
                  <v-icon color="primary">mdi-image-multiple</v-icon>
                </template>
                <v-list-item-title>{{ exhibit.name }}</v-list-item-title>
                <v-list-item-subtitle>Visited on {{ new Date(exhibit.date).toLocaleDateString() }}</v-list-item-subtitle>
              </v-list-item>
            </v-list>
            <p v-else class="text-center pa-4">You haven't visited any exhibits yet.</p>
          </v-card-text>
        </v-card>
      </v-col>

      <!-- Upcoming Events -->
      <v-col cols="12" md="6">
        <v-card>
          <v-card-title>Your Upcoming Events</v-card-title>
          <v-card-text>
            <v-list v-if="upcomingEvents.length > 0">
              <v-list-item
                v-for="event in upcomingEvents"
                :key="event.id"
              >
                <template v-slot:prepend>
                  <v-icon color="primary">mdi-calendar</v-icon>
                </template>
                <v-list-item-title>{{ event.name }}</v-list-item-title>
                <v-list-item-subtitle>{{ new Date(event.date).toLocaleDateString() }}</v-list-item-subtitle>
              </v-list-item>
            </v-list>
            <p v-else class="text-center pa-4">You don't have any upcoming events.</p>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>