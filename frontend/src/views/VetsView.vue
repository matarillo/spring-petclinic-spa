<script setup lang="ts">
import { usePetStore } from '@/stores/petStore'
import type { Vet } from '@/api/types'
import { onMounted } from 'vue'

const { vets, vet } = usePetStore()

const headers = [
  { title: 'Name', key: 'name' },
  { title: 'Specialties', key: 'specialties' }
]
const formatName = (item: Vet) => {
  return `${item.firstName} ${item.lastName}`
}
const formatSpecialities = (item: Vet) => {
  const names = item.specialties.join(' ')
  return names.length === 0 ? 'none' : names
}
onMounted(async () => {
  await vet.list()
})
</script>

<template>
  <v-container>
    <h2 class="text-h4">Veterinarians</h2>
    <v-data-table class="mt-8" :headers="headers" :items="vets">
      <template v-slot:[`item.name`]="{ item }">
        {{ formatName(item) }}
      </template>
      <template v-slot:[`item.specialties`]="{ item }">
        {{ formatSpecialities(item) }}
      </template>
    </v-data-table>
  </v-container>
</template>
