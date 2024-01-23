<script setup lang="ts">
import apiClient from '@/api/apiClient'

const test = async () => {
  const data = await apiClient.owner.find(1)
  console.log(data)
}
</script>
<template>
  <v-container>
    <h2 class="text-h4">This is an error page</h2>
    <v-btn @click="test">Test</v-btn>
  </v-container>
</template>

<style scoped></style>
