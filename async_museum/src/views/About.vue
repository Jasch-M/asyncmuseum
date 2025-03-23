<script setup lang="ts">
// Team members data (would come from API in a real app)
import { ref } from 'vue'

const teamMembers = ref([
  {
    id: 1,
    name: 'Dr. Emily Johnson',
    title: 'Museum Director',
    bio: 'Dr. Johnson has over 20 years of experience in museum management and a PhD in Paleontology from Harvard University.',
    image: 'https://randomuser.me/api/portraits/women/1.jpg'
  },
  {
    id: 2,
    name: 'Dr. Michael Chen',
    title: 'Head Curator',
    bio: 'Dr. Chen specializes in evolutionary biology and has curated exhibits at major museums worldwide.',
    image: 'https://randomuser.me/api/portraits/men/2.jpg'
  },
  {
    id: 3,
    name: 'Sarah Williams',
    title: 'Education Director',
    bio: 'Sarah has developed award-winning educational programs for museums and schools across the country.',
    image: 'https://randomuser.me/api/portraits/women/3.jpg'
  },
  {
    id: 4,
    name: 'James Rodriguez',
    title: 'Conservation Specialist',
    bio: 'James leads our conservation efforts and has worked on preservation projects in five continents.',
    image: 'https://randomuser.me/api/portraits/men/4.jpg'
  }
])

// Timeline events (would come from API in a real app)
const timelineEvents = ref([
  {
    year: '1925',
    title: 'Museum Founded',
    description: 'The Museum of Natural History was established with a donation from philanthropist Eleanor Wright.'
  },
  {
    year: '1940',
    title: 'First Major Expedition',
    description: 'The museum sponsored its first major paleontological expedition to the American Southwest.'
  },
  {
    year: '1968',
    title: 'New Building',
    description: 'The current museum building was constructed, designed by renowned architect Robert Miller.'
  },
  {
    year: '1995',
    title: 'Digital Initiative',
    description: 'The museum launched its first digital catalog, making collections accessible online.'
  },
  {
    year: '2010',
    title: 'Expansion',
    description: 'A major expansion added new wings for marine biology and human anthropology exhibits.'
  },
  {
    year: '2023',
    title: 'Today',
    description: 'The museum continues to grow with new interactive exhibits and educational programs.'
  }
])
</script>

<template>
  <div>
    <!-- Hero Section -->
    <v-parallax
      src="https://source.unsplash.com/random/1920x1080/?museum-building"
      height="300"
    >
      <div class="d-flex flex-column fill-height justify-center align-center text-white">
        <h1 class="text-h3 font-weight-bold mb-4">About Our Museum</h1>
        <h2 class="text-h6 font-weight-regular">Preserving the past, inspiring the future</h2>
      </div>
    </v-parallax>

    <!-- Mission Statement -->
    <v-container class="my-8">
      <v-row>
        <v-col cols="12" md="8" offset-md="2" class="text-center">
          <h2 class="text-h4 mb-4">Our Mission</h2>
          <p class="text-body-1">
            The Museum of Natural History is dedicated to understanding, preserving, and celebrating the natural world. 
            Through research, education, and exhibition, we aim to inspire curiosity about our planet's biodiversity, 
            geological wonders, and evolutionary history. We believe that by fostering a deeper understanding of nature, 
            we can encourage conservation and stewardship of our environment for future generations.
          </p>
        </v-col>
      </v-row>
    </v-container>

    <!-- Museum History Timeline -->
    <v-container class="my-8 bg-grey-lighten-4 py-8">
      <h2 class="text-h4 mb-6 text-center">Our History</h2>
      <v-timeline side="end">
        <v-timeline-item
          v-for="event in timelineEvents"
          :key="event.year"
          dot-color="primary"
          size="small"
        >
          <template v-slot:opposite>
            <div class="text-h5 font-weight-bold">{{ event.year }}</div>
          </template>
          <v-card>
            <v-card-title class="text-h6">{{ event.title }}</v-card-title>
            <v-card-text>{{ event.description }}</v-card-text>
          </v-card>
        </v-timeline-item>
      </v-timeline>
    </v-container>

    <!-- Leadership Team -->
    <v-container class="my-8">
      <h2 class="text-h4 mb-6 text-center">Our Team</h2>
      <v-row>
        <v-col
          v-for="member in teamMembers"
          :key="member.id"
          cols="12"
          sm="6"
          md="3"
        >
          <v-card class="mx-auto" max-width="300">
            <v-img
              :src="member.image"
              height="250"
              cover
            ></v-img>
            <v-card-title>{{ member.name }}</v-card-title>
            <v-card-subtitle>{{ member.title }}</v-card-subtitle>
            <v-card-text>{{ member.bio }}</v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>

    <!-- Values Section -->
    <v-container class="my-8">
      <h2 class="text-h4 mb-6 text-center">Our Values</h2>
      <v-row>
        <v-col cols="12" md="4">
          <v-card height="100%">
            <v-card-title class="d-flex align-center">
              <v-icon color="primary" size="large" class="mr-2">mdi-microscope</v-icon>
              Scientific Integrity
            </v-card-title>
            <v-card-text>
              We are committed to rigorous research and accurate representation of scientific knowledge in all our exhibits and educational programs.
            </v-card-text>
          </v-card>
        </v-col>
        <v-col cols="12" md="4">
          <v-card height="100%">
            <v-card-title class="d-flex align-center">
              <v-icon color="primary" size="large" class="mr-2">mdi-school</v-icon>
              Education
            </v-card-title>
            <v-card-text>
              We believe in making science accessible to all and inspiring curiosity about the natural world through engaging, interactive learning experiences.
            </v-card-text>
          </v-card>
        </v-col>
        <v-col cols="12" md="4">
          <v-card height="100%">
            <v-card-title class="d-flex align-center">
              <v-icon color="primary" size="large" class="mr-2">mdi-earth</v-icon>
              Conservation
            </v-card-title>
            <v-card-text>
              We advocate for the protection of biodiversity and natural habitats, promoting environmental stewardship through our exhibits and programs.
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>

    <!-- Support Section -->
    <v-container class="my-8 bg-primary text-center py-8">
      <v-row>
        <v-col cols="12" md="8" offset-md="2" class="text-white">
          <h2 class="text-h4 mb-4">Support Our Mission</h2>
          <p class="text-body-1 mb-4">
            Your donations help us continue our work in research, conservation, and education. 
            Consider supporting the Museum of Natural History today.
          </p>
          <v-btn
            color="white"
            variant="outlined"
            size="large"
            class="text-primary"
          >
            Donate Now
          </v-btn>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>