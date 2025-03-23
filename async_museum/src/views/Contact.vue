<script setup lang="ts">
import { ref } from 'vue'

// Form data
const form = ref({
  name: '',
  email: '',
  subject: '',
  message: ''
})

// Form validation
const nameRules = [
  (v: string) => !!v || 'Name is required',
  (v: string) => v.length <= 50 || 'Name must be less than 50 characters'
]

const emailRules = [
  (v: string) => !!v || 'Email is required',
  (v: string) => /.+@.+\..+/.test(v) || 'Email must be valid'
]

const subjectRules = [
  (v: string) => !!v || 'Subject is required',
  (v: string) => v.length <= 100 || 'Subject must be less than 100 characters'
]

const messageRules = [
  (v: string) => !!v || 'Message is required',
  (v: string) => v.length >= 10 || 'Message must be at least 10 characters',
  (v: string) => v.length <= 500 || 'Message must be less than 500 characters'
]

// Form submission
const formValid = ref(false)
const loading = ref(false)
const submitSuccess = ref(false)
const submitError = ref<string | null>(null)

const submitForm = async () => {
  loading.value = true
  submitSuccess.value = false
  submitError.value = null
  
  try {
    // In a real app, this would be an API call
    await new Promise(resolve => setTimeout(resolve, 1000))
    
    // Simulate successful submission
    submitSuccess.value = true
    form.value = {
      name: '',
      email: '',
      subject: '',
      message: ''
    }
  } catch (error: any) {
    submitError.value = error.message || 'An error occurred. Please try again.'
  } finally {
    loading.value = false
  }
}

// FAQ data
const faqs = ref([
  {
    question: 'What are the museum hours?',
    answer: 'The museum is open daily from 9:00 AM to 5:00 PM. We are closed on major holidays.'
  },
  {
    question: 'How much does admission cost?',
    answer: 'General admission is $15 for adults, $8 for children (ages 3-12), and $10 for seniors (65+). Members and children under 3 enter for free.'
  },
  {
    question: 'Is photography allowed in the museum?',
    answer: 'Photography for personal use is permitted in most areas of the museum. Flash photography, tripods, and commercial photography require special permission.'
  },
  {
    question: 'Are there guided tours available?',
    answer: 'Yes, we offer guided tours daily at 10:00 AM, 1:00 PM, and 3:00 PM. Tours are included with admission and last approximately 60 minutes.'
  },
  {
    question: 'Is the museum accessible for visitors with disabilities?',
    answer: 'Yes, our museum is fully accessible. We offer wheelchair rentals, accessible restrooms, and accommodations for visitors with various needs.'
  }
])
</script>

<template>
  <div>
    <!-- Hero Section -->
    <v-parallax
      src="https://source.unsplash.com/random/1920x1080/?museum-interior"
      height="300"
    >
      <div class="d-flex flex-column fill-height justify-center align-center text-white">
        <h1 class="text-h3 font-weight-bold mb-4">Contact Us</h1>
        <h2 class="text-h6 font-weight-regular">We'd love to hear from you</h2>
      </div>
    </v-parallax>

    <v-container class="my-8">
      <v-row>
        <!-- Contact Information -->
        <v-col cols="12" md="5">
          <h2 class="text-h4 mb-6">Get in Touch</h2>
          
          <v-list>
            <v-list-item>
              <template v-slot:prepend>
                <v-icon color="primary">mdi-map-marker</v-icon>
              </template>
              <v-list-item-title>Address</v-list-item-title>
              <v-list-item-subtitle>123 Museum Street, City, Country</v-list-item-subtitle>
            </v-list-item>
            
            <v-list-item>
              <template v-slot:prepend>
                <v-icon color="primary">mdi-phone</v-icon>
              </template>
              <v-list-item-title>Phone</v-list-item-title>
              <v-list-item-subtitle>(123) 456-7890</v-list-item-subtitle>
            </v-list-item>
            
            <v-list-item>
              <template v-slot:prepend>
                <v-icon color="primary">mdi-email</v-icon>
              </template>
              <v-list-item-title>Email</v-list-item-title>
              <v-list-item-subtitle>info@museumofnaturalhistory.com</v-list-item-subtitle>
            </v-list-item>
            
            <v-list-item>
              <template v-slot:prepend>
                <v-icon color="primary">mdi-clock</v-icon>
              </template>
              <v-list-item-title>Hours</v-list-item-title>
              <v-list-item-subtitle>Monday - Sunday: 9:00 AM - 5:00 PM</v-list-item-subtitle>
            </v-list-item>
          </v-list>
          
          <div class="mt-6">
            <h3 class="text-h6 mb-3">Follow Us</h3>
            <div class="d-flex">
              <v-btn icon class="mr-2" color="primary">
                <v-icon>mdi-facebook</v-icon>
              </v-btn>
              <v-btn icon class="mr-2" color="primary">
                <v-icon>mdi-twitter</v-icon>
              </v-btn>
              <v-btn icon class="mr-2" color="primary">
                <v-icon>mdi-instagram</v-icon>
              </v-btn>
              <v-btn icon color="primary">
                <v-icon>mdi-youtube</v-icon>
              </v-btn>
            </div>
          </div>
        </v-col>
        
        <!-- Contact Form -->
        <v-col cols="12" md="7">
          <v-card>
            <v-card-title>Send Us a Message</v-card-title>
            <v-card-text>
              <v-form
                ref="form"
                v-model="formValid"
                @submit.prevent="submitForm"
              >
                <v-alert
                  v-if="submitSuccess"
                  type="success"
                  class="mb-4"
                >
                  Your message has been sent successfully. We'll get back to you soon!
                </v-alert>
                
                <v-alert
                  v-if="submitError"
                  type="error"
                  class="mb-4"
                >
                  {{ submitError }}
                </v-alert>
                
                <v-text-field
                  v-model="form.name"
                  label="Name"
                  :rules="nameRules"
                  required
                ></v-text-field>
                
                <v-text-field
                  v-model="form.email"
                  label="Email"
                  :rules="emailRules"
                  required
                  type="email"
                ></v-text-field>
                
                <v-text-field
                  v-model="form.subject"
                  label="Subject"
                  :rules="subjectRules"
                  required
                ></v-text-field>
                
                <v-textarea
                  v-model="form.message"
                  label="Message"
                  :rules="messageRules"
                  required
                  rows="4"
                ></v-textarea>
                
                <v-btn
                  color="primary"
                  type="submit"
                  block
                  :loading="loading"
                  :disabled="!formValid"
                >
                  Send Message
                </v-btn>
              </v-form>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>

    <!-- Map Section -->
    <v-container class="my-8">
      <h2 class="text-h4 mb-6 text-center">Find Us</h2>
      <v-card>
        <v-card-text class="pa-0">
          <!-- In a real app, this would be an actual map component -->
          <div class="map-placeholder" style="height: 400px; background-color: #e0e0e0; display: flex; align-items: center; justify-content: center;">
            <div class="text-center">
              <v-icon size="64" color="primary">mdi-map</v-icon>
              <p class="mt-2">Interactive Map Would Be Here</p>
            </div>
          </div>
        </v-card-text>
      </v-card>
    </v-container>

    <!-- FAQ Section -->
    <v-container class="my-8 bg-grey-lighten-4 py-8">
      <h2 class="text-h4 mb-6 text-center">Frequently Asked Questions</h2>
      <v-row>
        <v-col cols="12" md="8" offset-md="2">
          <v-expansion-panels>
            <v-expansion-panel
              v-for="(faq, index) in faqs"
              :key="index"
            >
              <v-expansion-panel-title>{{ faq.question }}</v-expansion-panel-title>
              <v-expansion-panel-text>{{ faq.answer }}</v-expansion-panel-text>
            </v-expansion-panel>
          </v-expansion-panels>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>