<script setup lang="ts">
const vets = [
  {
    id: 1,
    firstName: 'James',
    lastName: 'Carter',
    specialties: []
  },
  {
    id: 3,
    firstName: 'Linda',
    lastName: 'Douglas',
    specialties: [
      { id: 3, name: 'dentistry' },
      { id: 2, name: 'surgery' }
    ]
  },
  {
    id: 6,
    firstName: 'Sharon',
    lastName: 'Jenkins',
    specialties: []
  },
  {
    id: 2,
    firstName: 'Helen',
    lastName: 'Leary',
    specialties: [{ id: 1, name: 'radiology' }]
  },
  {
    id: 4,
    firstName: 'Rafael',
    lastName: 'Ortega',
    specialties: [{ id: 2, name: 'surgery' }]
  },
  {
    id: 5,
    firstName: 'Henry',
    lastName: 'Stevens',
    specialties: [{ id: 1, name: 'radiology' }]
  }
]
const headers = [
  { title: 'Name', key: 'name' },
  { title: 'Specialties', key: 'specialties' }
]
type Vet = (typeof vets)[number]
const formatName = (item: Vet) => {
  return `${item.firstName} ${item.lastName}`
}
const formatSpecialities = (item: Vet) => {
  const names = item.specialties.map((x) => x.name).join(' ')
  return names.length === 0 ? 'none' : names
}
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
