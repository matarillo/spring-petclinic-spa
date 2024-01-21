<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
const router = useRouter()
const owner = ref({
  firstName: 'Harold',
  lastName: 'Davis',
  address: '563 Friendly St.',
  city: 'Windsor',
  telephone: '6085553198'
})
const edit = () => {
  router.back()
}
</script>
<template>
  <v-container>
    <h2 class="text-h4">Owner</h2>
    <div class="w-50 mt-8">
      <v-text-field label="First name" v-model="owner.firstName"></v-text-field>
      <v-text-field label="Last name" v-model="owner.lastName"></v-text-field>
      <v-text-field label="Address" v-model="owner.address"></v-text-field>
      <v-text-field label="City" v-model="owner.city"></v-text-field>
      <v-text-field label="Telephone" v-model="owner.telephone"></v-text-field>
      <v-btn block color="primary" @click="edit">Update Owner</v-btn>
    </div>
  </v-container>
</template>
