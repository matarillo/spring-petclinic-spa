<script setup lang="ts">
import { RouterView } from 'vue-router'
</script>

<template>
  <v-app>
    <v-app-bar title="Spring PetClinic" color="green-darken-3">
      <v-tabs bg-color="green-darken-3" align-tabs="title">
        <v-tab value="home" to="/"><v-icon icon="mdi-home"></v-icon>Home</v-tab>
        <v-tab value="owners" to="/owners"><v-icon icon="mdi-magnify"></v-icon>Find Owners</v-tab>
        <v-tab value="veterinarians" to="/vets"
          ><v-icon icon="mdi-view-list"></v-icon>Veterinarians</v-tab
        >
        <v-tab value="error" to="/oups">Error</v-tab>
      </v-tabs>
    </v-app-bar>

    <v-main>
      <RouterView />
    </v-main>
  </v-app>
</template>

<style scoped></style>
