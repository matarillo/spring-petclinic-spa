<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { usePetStore } from '@/stores/petStore'

const ownerToAdd = ref({
  firstName: '',
  lastName: '',
  address: '',
  city: '',
  telephone: ''
})

const router = useRouter()
const { owner } = usePetStore()

const add = async () => {
  // validation
  const value = ownerToAdd.value
  if (value.firstName == null || value.firstName.length === 0) {
    return
  }
  if (value.lastName == null || value.lastName.length === 0) {
    return
  }
  if (value.address == null || value.address.length === 0) {
    return
  }
  if (value.city == null || value.city.length === 0) {
    return
  }
  if (value.telephone == null || value.telephone.length === 0) {
    return
  }
  await owner.add(value)
  router.back()
}
</script>
<template>
  <v-container>
    <h2 class="text-h4">New Owner</h2>
    <div class="w-50 mt-8">
      <v-text-field label="First name" v-model="ownerToAdd.firstName"></v-text-field>
      <v-text-field label="Last name" v-model="ownerToAdd.lastName"></v-text-field>
      <v-text-field label="Address" v-model="ownerToAdd.address"></v-text-field>
      <v-text-field label="City" v-model="ownerToAdd.city"></v-text-field>
      <v-text-field label="Telephone" v-model="ownerToAdd.telephone"></v-text-field>
      <v-btn block color="primary" @click="add">Add Owner</v-btn>
    </div>
  </v-container>
</template>
