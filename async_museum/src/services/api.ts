/**
 * API Service
 * 
 * This service provides methods for interacting with the backend API.
 * It makes HTTP requests to the Ktor backend.
 */

import axios from 'axios'

// API base URL
const API_BASE_URL = 'http://localhost:8080/api'

// Create axios instance
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
})

// Types
export interface Exhibit {
  id: number
  title: string
  description: string
  image: string
  category: string
  featured: boolean
  details?: string
}

export interface Event {
  id: number
  title: string
  date: string
  description: string
  location?: string
  price?: string
}

export interface VisitorInfo {
  hours: string
  admission: {
    adult: number
    child: number
    senior: number
  }
  location: string
  contact: {
    phone: string
    email: string
  }
}

export interface ApiResponse<T> {
  success: boolean
  message?: string
  data?: T
}

// API Service
export const apiService = {
  // Exhibits
  getExhibits: async (): Promise<Exhibit[]> => {
    try {
      const response = await api.get<ApiResponse<Exhibit[]>>('/exhibits')
      return response.data.data || []
    } catch (error) {
      console.error('Error fetching exhibits:', error)
      return []
    }
  },

  getExhibit: async (id: number): Promise<Exhibit | undefined> => {
    try {
      const response = await api.get<ApiResponse<Exhibit>>(`/exhibits/${id}`)
      return response.data.data
    } catch (error) {
      console.error(`Error fetching exhibit ${id}:`, error)
      return undefined
    }
  },

  // Events
  getEvents: async (): Promise<Event[]> => {
    try {
      const response = await api.get<ApiResponse<Event[]>>('/events')
      return response.data.data || []
    } catch (error) {
      console.error('Error fetching events:', error)
      return []
    }
  },

  getEvent: async (id: number): Promise<Event | undefined> => {
    try {
      const response = await api.get<ApiResponse<Event>>(`/events/${id}`)
      return response.data.data
    } catch (error) {
      console.error(`Error fetching event ${id}:`, error)
      return undefined
    }
  },

  // Visitor Information
  getVisitorInfo: async (): Promise<VisitorInfo | undefined> => {
    try {
      const response = await api.get<ApiResponse<VisitorInfo>>('/visitor-info')
      return response.data.data
    } catch (error) {
      console.error('Error fetching visitor info:', error)
      return undefined
    }
  },

  // Contact Form
  submitContactForm: async (formData: { name: string, email: string, subject?: string, message: string }): Promise<{ success: boolean, message: string }> => {
    try {
      const response = await api.post<ApiResponse<any>>('/contact', formData)
      return {
        success: response.data.success,
        message: response.data.message || 'Your message has been sent successfully. We\'ll get back to you soon!'
      }
    } catch (error) {
      console.error('Error submitting contact form:', error)
      return {
        success: false,
        message: 'Failed to send your message. Please try again later.'
      }
    }
  },

  // Authentication
  login: async (authData: { token: string, email: string, displayName?: string, providerId: string, providerUserId: string }): Promise<{ success: boolean, token?: string, user?: any }> => {
    try {
      const response = await api.post<ApiResponse<{ token: string, user: any }>>('/auth/login', authData)
      if (response.data.success && response.data.data) {
        // Store token in localStorage
        localStorage.setItem('auth_token', response.data.data.token)
        // Set token in axios headers for future requests
        api.defaults.headers.common['Authorization'] = `Bearer ${response.data.data.token}`

        return {
          success: true,
          token: response.data.data.token,
          user: response.data.data.user
        }
      }
      return { success: false }
    } catch (error) {
      console.error('Error during login:', error)
      return { success: false }
    }
  },

  logout: async (): Promise<boolean> => {
    try {
      await api.post('/auth/logout')
      // Remove token from localStorage and axios headers
      localStorage.removeItem('auth_token')
      delete api.defaults.headers.common['Authorization']
      return true
    } catch (error) {
      console.error('Error during logout:', error)
      return false
    }
  },

  // Initialize auth from localStorage
  initAuth: () => {
    const token = localStorage.getItem('auth_token')
    if (token) {
      api.defaults.headers.common['Authorization'] = `Bearer ${token}`
    }
  }
}

export default apiService
