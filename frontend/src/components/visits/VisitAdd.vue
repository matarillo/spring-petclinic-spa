<script setup lang="ts">
import { computed, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { usePetStore } from '@/stores/petStore'
const route = useRoute()
const router = useRouter()
const ownerId = computed(() => {
  const value = route.params[`id`]
  const strValue = Array.isArray(value) ? value.join(',') : value
  return Number(strValue)
})
const petId = computed(() => {
  const value = route.params[`petId`]
  const strValue = Array.isArray(value) ? value.join(',') : value
  return Number(strValue)
})

const { owner, pet, visit } = usePetStore()
const foundOwner = owner.find(ownerId.value)
const found = pet.find(ownerId.value, petId.value)

const ownerName = foundOwner == null ? '' : `${foundOwner.firstName} ${foundOwner.lastName}`

const visitToAdd = ref({
  visitDate: new Date(),
  description: ''
})

const formatDate = (d: Date) =>
  [d.getFullYear(), ('0' + (d.getMonth() + 1)).slice(-2), ('0' + d.getDate()).slice(-2)].join('-')

const formattedVisitDate = computed(() => formatDate(visitToAdd.value.visitDate))

const add = async () => {
  // validation
  if (ownerId.value == null) {
    return
  }
  if (found == null) {
    return
  }
  const value = visitToAdd.value
  if (value.visitDate == null) {
    return
  }
  if (value.description == null) {
    return
  }
  visit.add(ownerId.value, found.id, {
    visitDate: formatDate(visitToAdd.value.visitDate),
    description: visitToAdd.value.description
  })
  router.back()
}
</script>
<template>
  <v-container>
    <h2 class="text-h4">New Visit</h2>
    <v-card class="w-50 mt-8" v-if="found != null">
      <v-list lines="two">
        <v-list-item>
          <v-list-item-title>Owner</v-list-item-title>
          <v-list-item-subtitle>{{ ownerName }}</v-list-item-subtitle>
        </v-list-item>
        <v-list-item>
          <v-list-item-title>Name</v-list-item-title>
          <v-list-item-subtitle>{{ found.name }}</v-list-item-subtitle>
        </v-list-item>
        <v-list-item>
          <v-list-item-title>Birth Date</v-list-item-title>
          <v-list-item-subtitle>{{ found.birthDate }}</v-list-item-subtitle>
        </v-list-item>
        <v-list-item>
          <v-list-item-title>Type</v-list-item-title>
          <v-list-item-subtitle>{{ found.type }}</v-list-item-subtitle>
        </v-list-item>
      </v-list>
    </v-card>
    <div class="w-50 mt-8">
      <v-menu>
        <template v-slot:activator="{ props }">
          <v-text-field
            label="Date"
            v-bind="props"
            v-model="formattedVisitDate"
            readonly
          ></v-text-field>
        </template>
        <v-date-picker v-model="visitToAdd.visitDate" no-title scrollable> </v-date-picker>
      </v-menu>
      <v-text-field label="Description" v-model="visitToAdd.description"></v-text-field>
      <v-btn block color="primary" @click="add">Add Visit</v-btn>
    </div>
    <h3 class="text-h5 mt-8">Previous Visits</h3>
    <v-table class="w-75" v-if="found != null">
      <thead>
        <tr>
          <th>Visit Date</th>
          <th>Description</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="visit in found.visits || []" :key="visit.id">
          <td>{{ visit.visitDate }}</td>
          <td>{{ visit.description }}</td>
        </tr>
      </tbody>
    </v-table>
  </v-container>
</template>
