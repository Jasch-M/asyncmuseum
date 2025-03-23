import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

// Define routes
const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/Home.vue'),
    meta: { requiresAuth: false }
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue'),
    meta: { requiresAuth: false }
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('../views/Profile.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/exhibits',
    name: 'Exhibits',
    component: () => import('../views/Exhibits.vue'),
    meta: { requiresAuth: false }
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('../views/About.vue'),
    meta: { requiresAuth: false }
  },
  {
    path: '/contact',
    name: 'Contact',
    component: () => import('../views/Contact.vue'),
    meta: { requiresAuth: false }
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('../views/NotFound.vue'),
    meta: { requiresAuth: false }
  }
]

// Create router instance
const router = createRouter({
  history: createWebHistory(),
  routes
})

// Navigation guard for authentication
router.beforeEach(async (to, from, next) => {
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth)

  // Import auth store dynamically to avoid circular dependency
  const { useAuthStore } = await import('../store/auth')
  const authStore = useAuthStore()

  // Check if user is authenticated
  const isAuthenticated = authStore.isAuthenticated

  if (requiresAuth && !isAuthenticated) {
    // Redirect to login if authentication is required but user is not authenticated
    next({ path: '/login', query: { redirect: to.fullPath } })
  } else if (to.path === '/login' && isAuthenticated) {
    // Redirect to home if user is already authenticated and tries to access login page
    next({ path: '/' })
  } else {
    next()
  }
})

export default router
