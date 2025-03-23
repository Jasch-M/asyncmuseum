<script setup lang="ts">
import { ref } from 'vue'

// Featured exhibits data (would come from API in a real app)
const featuredExhibits = ref([
  {
    id: 1,
    title: 'Dinosaur Fossils',
    description: 'Explore the world of dinosaurs through our extensive fossil collection.',
    image: 'https://source.unsplash.com/random/800x600/?dinosaur'
  },
  {
    id: 2,
    title: 'Ocean Life',
    description: 'Discover the mysteries of the deep sea and marine ecosystems.',
    image: 'https://source.unsplash.com/random/800x600/?ocean'
  },
  {
    id: 3,
    title: 'Human Evolution',
    description: 'Trace the journey of human evolution through artifacts and interactive displays.',
    image: 'https://source.unsplash.com/random/800x600/?evolution'
  }
])

// Upcoming events (would come from API in a real app)
const upcomingEvents = ref([
  {
    id: 1,
    title: 'Night at the Museum',
    date: '2023-04-15',
    description: 'Experience the museum after hours with special guided tours and activities.'
  },
  {
    id: 2,
    title: 'Fossil Discovery Workshop',
    date: '2023-04-22',
    description: 'Learn how paleontologists discover and excavate fossils.'
  },
  {
    id: 3,
    title: 'Conservation Talk Series',
    date: '2023-04-29',
    description: 'Join our experts for talks on wildlife conservation and biodiversity.'
  }
])
</script>

<template>
  <div>
    <!-- Hero Section -->
    <v-parallax
      src="https://source.unsplash.com/random/1920x1080/?museum"
      height="400"
    >
      <div class="d-flex flex-column fill-height justify-center align-center text-white">
        <h1 class="text-h2 font-weight-bold mb-4">Museum of Natural History</h1>
        <h2 class="text-h5 font-weight-regular">Explore the wonders of our natural world</h2>
      </div>
    </v-parallax>

    <!-- Welcome Section -->
    <v-container class="my-8">
      <v-row>
        <v-col cols="12" md="8" offset-md="2" class="text-center">
          <h2 class="text-h4 mb-4">Welcome to Our Museum</h2>
          <p class="text-body-1">
            The Museum of Natural History is dedicated to understanding, preserving, and celebrating the natural world. 
            Our exhibits showcase the diversity of life on Earth, from prehistoric times to the present day.
          </p>
          <v-btn
            color="primary"
            class="mt-4"
            to="/exhibits"
          >
            Explore Exhibits
          </v-btn>
        </v-col>
      </v-row>
    </v-container>

    <!-- Featured Exhibits -->
    <v-container class="my-8">
      <h2 class="text-h4 mb-6 text-center">Featured Exhibits</h2>
      <v-row>
        <v-col
          v-for="exhibit in featuredExhibits"
          :key="exhibit.id"
          cols="12"
          md="4"
        >
          <v-card height="100%">
            <v-img
              :src="exhibit.image"
              height="200"
              cover
            ></v-img>
            <v-card-title>{{ exhibit.title }}</v-card-title>
            <v-card-text>{{ exhibit.description }}</v-card-text>
            <v-card-actions>
              <v-btn
                color="primary"
                variant="text"
                :to="`/exhibits/${exhibit.id}`"
              >
                Learn More
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>

    <!-- Upcoming Events -->
    <v-container class="my-8 bg-grey-lighten-4 py-8">
      <h2 class="text-h4 mb-6 text-center">Upcoming Events</h2>
      <v-row>
        <v-col
          v-for="event in upcomingEvents"
          :key="event.id"
          cols="12"
          md="4"
        >
          <v-card height="100%">
            <v-card-title>{{ event.title }}</v-card-title>
            <v-card-subtitle>{{ new Date(event.date).toLocaleDateString() }}</v-card-subtitle>
            <v-card-text>{{ event.description }}</v-card-text>
            <v-card-actions>
              <v-btn
                color="primary"
                variant="text"
              >
                Register
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>

    <!-- Visit Info -->
    <v-container class="my-8">
      <v-row>
        <v-col cols="12" md="6">
          <h2 class="text-h4 mb-4">Plan Your Visit</h2>
          <v-list>
            <v-list-item>
              <template v-slot:prepend>
                <v-icon color="primary">mdi-clock</v-icon>
              </template>
              <v-list-item-title>Opening Hours</v-list-item-title>
              <v-list-item-subtitle>Monday - Sunday: 9:00 AM - 5:00 PM</v-list-item-subtitle>
            </v-list-item>
            <v-list-item>
              <template v-slot:prepend>
                <v-icon color="primary">mdi-ticket</v-icon>
              </template>
              <v-list-item-title>Admission</v-list-item-title>
              <v-list-item-subtitle>Adults: $15 | Children: $8 | Seniors: $10</v-list-item-subtitle>
            </v-list-item>
            <v-list-item>
              <template v-slot:prepend>
                <v-icon color="primary">mdi-map-marker</v-icon>
              </template>
              <v-list-item-title>Location</v-list-item-title>
              <v-list-item-subtitle>123 Museum Street, City, Country</v-list-item-subtitle>
            </v-list-item>
          </v-list>
        </v-col>
        <v-col cols="12" md="6">
          <v-img
            src="https://source.unsplash.com/random/600x400/?museum-interior"
            height="300"
            cover
            class="rounded-lg"
          ></v-img>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>