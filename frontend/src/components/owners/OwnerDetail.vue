<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
const route = useRoute()
const router = useRouter()
const ownerId = computed(() => route.params[`id`])

const edit = () => {
  router.push(`/owners/${ownerId.value}/edit`)
}
const addPet = () => {
  router.push(`/owners/${ownerId.value}/pets/new`)
}
const editPet = (petId: number) => {
  router.push(`/owners/${ownerId.value}/pets/${petId}/edit`)
}
const addVisit = (petId: number) => {
  router.push(`/owners/${ownerId.value}/pets/${petId}/visits/new`)
}
const owner = {
  id: 4,
  name: 'Harold Davis',
  address: '563 Friendly St.',
  city: 'Windsor',
  telephone: '6085553198',
  pets: [
    {
      id: 1,
      name: 'Max',
      birthDate: '2012-09-04',
      type: 'cat',
      visits: [
        { date: '2013-01-03', descrition: 'neutered' },
        { date: '2013-01-02', descrition: 'rabies shot' }
      ]
    },
    {
      id: 2,
      name: 'Samantha',
      birthDate: '2012-09-04',
      type: 'cat',
      visits: [
        { date: '2013-01-04', descrition: 'spayed' },
        { date: '2013-01-01', descrition: 'rabies shot' }
      ]
    }
  ]
}
</script>

<template>
  <v-container>
    <h2 class="text-h4">Owner Information</h2>
    <v-card class="w-50 mt-8">
      <v-list lines="two">
        <v-list-item>
          <v-list-item-title>Name</v-list-item-title>
          <v-list-item-subtitle>{{ owner.name }}</v-list-item-subtitle>
        </v-list-item>
        <v-list-item>
          <v-list-item-title>Address</v-list-item-title>
          <v-list-item-subtitle>{{ owner.address }}</v-list-item-subtitle>
        </v-list-item>
        <v-list-item>
          <v-list-item-title>City</v-list-item-title>
          <v-list-item-subtitle>{{ owner.city }}</v-list-item-subtitle>
        </v-list-item>
        <v-list-item>
          <v-list-item-title>Telephone</v-list-item-title>
          <v-list-item-subtitle>{{ owner.telephone }}</v-list-item-subtitle>
        </v-list-item>
      </v-list>
    </v-card>
    <div class="w-50 mt-8">
      <v-btn color="primary" @click="edit">Edit Owner</v-btn>
      <v-btn color="primary" class="ml-4" @click="addPet">Add New Pet</v-btn>
    </div>
    <h2 class="text-h4 mt-8">Pets and Visits</h2>
    <v-table class="mt-8">
      <tbody>
        <tr v-for="pet in owner.pets" :key="pet.id">
          <td>
            <v-card class="my-4">
              <v-list lines="two">
                <v-list-item>
                  <v-list-item-title>Name</v-list-item-title>
                  <v-list-item-subtitle>{{ pet.name }}</v-list-item-subtitle>
                </v-list-item>
                <v-list-item>
                  <v-list-item-title>Birth Date</v-list-item-title>
                  <v-list-item-subtitle>{{ pet.birthDate }}</v-list-item-subtitle>
                </v-list-item>
                <v-list-item>
                  <v-list-item-title>Type</v-list-item-title>
                  <v-list-item-subtitle>{{ pet.type }}</v-list-item-subtitle>
                </v-list-item>
              </v-list>
            </v-card>
          </td>
          <td>
            <v-table>
              <thead>
                <tr>
                  <th>Visit Date</th>
                  <th>Description</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="visit in pet.visits" :key="visit.date">
                  <td>{{ visit.date }}</td>
                  <td>{{ visit.descrition }}</td>
                </tr>
              </tbody>
            </v-table>
            <div class="my-4">
              <v-btn variant="flat" size="small" color="primary" @click="editPet(pet.id)"
                >Edit Pet</v-btn
              >
              <v-btn
                variant="flat"
                size="small"
                color="primary"
                class="ml-4"
                @click="addVisit(pet.id)"
                >Add Visit</v-btn
              >
            </div>
          </td>
        </tr>
      </tbody>
    </v-table>
  </v-container>
</template>
<style scoped>
thead th {
  font-weight: 700 !important;
}
</style>
