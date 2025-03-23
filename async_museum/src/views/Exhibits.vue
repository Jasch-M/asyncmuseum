<script setup lang="ts">
import { ref, computed } from 'vue'
import { useAuthStore } from '../store/auth'

const authStore = useAuthStore()
const isAuthenticated = computed(() => authStore.isAuthenticated)

// Search and filter
const searchQuery = ref('')
const selectedCategory = ref('all')

// Exhibit data (would come from API in a real app)
const exhibits = ref([
  {
    id: 1,
    title: 'Dinosaur Fossils',
    description: 'Explore the world of dinosaurs through our extensive fossil collection, featuring specimens from the Jurassic and Cretaceous periods.',
    image: 'https://source.unsplash.com/random/800x600/?dinosaur',
    category: 'prehistoric',
    featured: true
  },
  {
    id: 2,
    title: 'Ocean Life',
    description: 'Discover the mysteries of the deep sea and marine ecosystems, from coral reefs to the deepest ocean trenches.',
    image: 'https://source.unsplash.com/random/800x600/?ocean',
    category: 'marine',
    featured: true
  },
  {
    id: 3,
    title: 'Human Evolution',
    description: 'Trace the journey of human evolution through artifacts and interactive displays that show our species\' development over millions of years.',
    image: 'https://source.unsplash.com/random/800x600/?evolution',
    category: 'anthropology',
    featured: true
  },
  {
    id: 4,
    title: 'Gems and Minerals',
    description: 'Marvel at our collection of precious gems and minerals from around the world, including rare specimens and dazzling crystals.',
    image: 'https://source.unsplash.com/random/800x600/?minerals',
    category: 'geology',
    featured: false
  },
  {
    id: 5,
    title: 'African Wildlife',
    description: 'Experience the diversity of African wildlife through our exhibits featuring taxidermy specimens and habitat recreations.',
    image: 'https://source.unsplash.com/random/800x600/?safari',
    category: 'zoology',
    featured: false
  },
  {
    id: 6,
    title: 'Rainforest Ecosystems',
    description: 'Immerse yourself in the lush world of rainforests and learn about the incredible biodiversity these ecosystems support.',
    image: 'https://source.unsplash.com/random/800x600/?rainforest',
    category: 'botany',
    featured: false
  }
])

// Categories for filtering
const categories = [
  { value: 'all', text: 'All Categories' },
  { value: 'prehistoric', text: 'Prehistoric' },
  { value: 'marine', text: 'Marine Life' },
  { value: 'anthropology', text: 'Anthropology' },
  { value: 'geology', text: 'Geology' },
  { value: 'zoology', text: 'Zoology' },
  { value: 'botany', text: 'Botany' }
]

// Filtered exhibits based on search and category
const filteredExhibits = computed(() => {
  return exhibits.value.filter(exhibit => {
    const matchesSearch = exhibit.title.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
                         exhibit.description.toLowerCase().includes(searchQuery.value.toLowerCase())
    const matchesCategory = selectedCategory.value === 'all' || exhibit.category === selectedCategory.value
    
    return matchesSearch && matchesCategory
  })
})
</script>

<template>
  <div>
    <!-- Hero Section -->
    <v-parallax
      src="https://source.unsplash.com/random/1920x1080/?museum-exhibit"
      height="300"
    >
      <div class="d-flex flex-column fill-height justify-center align-center text-white">
        <h1 class="text-h3 font-weight-bold mb-4">Our Exhibits</h1>
        <h2 class="text-h6 font-weight-regular">Discover the wonders of natural history</h2>
      </div>
    </v-parallax>

    <!-- Search and Filter -->
    <v-container class="my-8">
      <v-row>
        <v-col cols="12" md="6">
          <v-text-field
            v-model="searchQuery"
            label="Search Exhibits"
            prepend-inner-icon="mdi-magnify"
            variant="outlined"
            hide-details
          ></v-text-field>
        </v-col>
        <v-col cols="12" md="6">
          <v-select
            v-model="selectedCategory"
            :items="categories"
            label="Filter by Category"
            variant="outlined"
            hide-details
          ></v-select>
        </v-col>
      </v-row>
    </v-container>

    <!-- Exhibits Grid -->
    <v-container>
      <v-row v-if="filteredExhibits.length > 0">
        <v-col
          v-for="exhibit in filteredExhibits"
          :key="exhibit.id"
          cols="12"
          sm="6"
          md="4"
          class="mb-4"
        >
          <v-card height="100%">
            <v-img
              :src="exhibit.image"
              height="200"
              cover
            >
              <template v-slot:placeholder>
                <v-row class="fill-height ma-0" align="center" justify="center">
                  <v-progress-circular indeterminate color="primary"></v-progress-circular>
                </v-row>
              </template>
            </v-img>
            
            <v-card-title>
              {{ exhibit.title }}
              <v-chip
                v-if="exhibit.featured"
                color="primary"
                size="small"
                class="ml-2"
              >
                Featured
              </v-chip>
            </v-card-title>
            
            <v-card-text>
              <p>{{ exhibit.description }}</p>
            </v-card-text>
            
            <v-card-actions>
              <v-btn
                color="primary"
                variant="text"
                :to="`/exhibits/${exhibit.id}`"
              >
                Learn More
              </v-btn>
              
              <v-spacer></v-spacer>
              
              <v-btn
                v-if="isAuthenticated"
                icon
                variant="text"
                color="primary"
              >
                <v-icon>mdi-bookmark-outline</v-icon>
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
      
      <v-row v-else>
        <v-col cols="12" class="text-center py-8">
          <v-icon size="64" color="grey">mdi-alert-circle-outline</v-icon>
          <h3 class="text-h5 mt-4">No exhibits found</h3>
          <p class="text-body-1">Try adjusting your search or filter criteria</p>
        </v-col>
      </v-row>
    </v-container>

    <!-- Membership Promotion for Non-Authenticated Users -->
    <v-container v-if="!isAuthenticated" class="my-8 bg-grey-lighten-4 py-8">
      <v-row>
        <v-col cols="12" md="8" offset-md="2" class="text-center">
          <h2 class="text-h4 mb-4">Become a Member</h2>
          <p class="text-body-1 mb-4">
            Sign up for a membership to access exclusive exhibits, special events, and personalized recommendations.
          </p>
          <v-btn
            color="primary"
            size="large"
            to="/login"
          >
            Sign Up Now
          </v-btn>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>