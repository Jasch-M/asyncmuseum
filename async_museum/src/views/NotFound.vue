<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="6" class="text-center">
        <v-icon size="100" color="primary" class="mb-4">mdi-alert-circle-outline</v-icon>
        <h1 class="text-h2 mb-4">404</h1>
        <h2 class="text-h4 mb-6">Page Not Found</h2>
        <p class="text-body-1 mb-8">
          The page you are looking for might have been removed, had its name changed, 
          or is temporarily unavailable.
        </p>
        <v-btn
          color="primary"
          size="large"
          to="/"
        >
          Return to Home
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
// No additional logic needed for this component
</script>