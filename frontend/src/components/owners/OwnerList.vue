<script setup lang="ts">
import { ref, computed } from 'vue'
import { RouterLink, useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()
const lastName = ref('')
const lastNameQuery = computed(() => route.query[`lastName`])
const find = () => {
  router.push({
    path: '/owners',
    query: {
      lastName: lastName.value
    }
  })
}
const add = () => {
  router.push('/owners/new')
}
const headers = [
  { title: 'Name', key: 'name' },
  { title: 'Address', key: 'address' },
  { title: 'City', key: 'city' },
  { title: 'Telephone', key: 'telephone' },
  { title: 'Pets', key: 'pets' }
]
const owners = [
  {
    id: 4,
    name: 'Harold Davis',
    address: '563 Friendly St.',
    city: 'Windsor',
    telephone: '6085553198',
    pets: ['ddd', 'Iggy', 'rr']
  },
  {
    id: 7,
    name: 'Jeff Black',
    address: '1450 Oak Blvd.',
    city: 'Monona',
    telephone: '6085555387',
    pets: ['Lucky']
  },
  {
    id: 11,
    name: 'Anna Belle',
    address: 'Jonestown',
    city: '40 Roake Street',
    telephone: '111',
    pets: ['Birdie']
  }
]
type Owner = (typeof owners)[number]
</script>

<template>
  <v-container v-if="lastNameQuery == null">
    <h2 class="text-h4">Find Owners</h2>
    <div class="w-50 mt-8">
      <v-text-field label="Last name" v-model="lastName">
        <template v-slot:append-inner>
          <v-btn size="small" color="primary" @click="find">Find Owner</v-btn>
        </template>
      </v-text-field>
      <v-btn color="primary" @click="add">Add Owner</v-btn>
    </div>
  </v-container>
  <v-container v-if="lastNameQuery != null">
    <h2 class="text-h4">Owners</h2>
    <v-data-table class="mt-8 owners" :headers="headers" :items="owners">
      <template v-slot:[`item.name`]="{ item }">
        <RouterLink :to="`/owners/${item.id}`">{{ item.name }}</RouterLink>
      </template>
      <template v-slot:[`item.pets`]="{ item }">
        {{ (item as Owner).pets.join(' ') }}
      </template>
    </v-data-table>
  </v-container>
</template>
<style scoped>
.owners >>> thead th {
  font-weight: 700 !important;
}
</style>
