<script setup lang="ts">
import { useRouter } from 'vue-router'
import { usePetStore } from '@/stores/petStore'

const props = defineProps<{
  id: string
}>()
const ownerId = Number(props.id)

const router = useRouter()
const { owner } = usePetStore()

const foundOwner = owner.find(ownerId)

const edit = () => {
  router.push({
    // path: `/owners/${ownerId.value}/edit`,
    name: 'edit owner',
    params: { id: props.id }
  })
}
const addPet = () => {
  router.push({
    // path: `/owners/${ownerId.value}/pets/new`,
    name: 'add pet',
    params: { id: props.id }
  })
}
const editPet = (petId: number) => {
  router.push({
    // path: `/owners/${ownerId.value}/pets/${petId}/edit`,
    name: 'edit pet',
    params: { id: props.id, petId: `${petId}` }
  })
}
const addVisit = (petId: number) => {
  router.push({
    // path: `/owners/${ownerId.value}/pets/${petId}/visits/new`,
    name: 'add visit',
    params: { id: props.id, petId: `${petId}` }
  })
}
</script>

<template>
  <v-container>
    <h2 class="text-h4">Owner Information</h2>
    <template v-if="foundOwner != null">
      <v-card class="w-50 mt-8">
        <v-list lines="two">
          <v-list-item>
            <v-list-item-title>Name</v-list-item-title>
            <v-list-item-subtitle>{{
              `${foundOwner.firstName} ${foundOwner.lastName}`
            }}</v-list-item-subtitle>
          </v-list-item>
          <v-list-item>
            <v-list-item-title>Address</v-list-item-title>
            <v-list-item-subtitle>{{ foundOwner.address }}</v-list-item-subtitle>
          </v-list-item>
          <v-list-item>
            <v-list-item-title>City</v-list-item-title>
            <v-list-item-subtitle>{{ foundOwner.city }}</v-list-item-subtitle>
          </v-list-item>
          <v-list-item>
            <v-list-item-title>Telephone</v-list-item-title>
            <v-list-item-subtitle>{{ foundOwner.telephone }}</v-list-item-subtitle>
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
          <tr v-for="pet in foundOwner.pets ?? []" :key="pet.id">
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
                  <tr v-for="visit in pet.visits ?? []" :key="visit.id">
                    <td>{{ visit.visitDate }}</td>
                    <td>{{ visit.description }}</td>
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
    </template>
  </v-container>
</template>
<style scoped>
thead th {
  font-weight: 700 !important;
}
</style>
