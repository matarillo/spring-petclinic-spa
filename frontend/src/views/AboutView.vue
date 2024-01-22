<script setup lang="ts">
import axios from 'axios'
import type { Owner } from '@/api/types'

const test = async () => {
  const res = await axios.get<Owner[]>('/api/owners')
  console.log(res.data)
}
</script>
<template>
  <v-container>
    <h2 class="text-h4">This is an error page</h2>
    <v-btn @click="test">Test</v-btn>
  </v-container>
</template>

<style scoped></style>
