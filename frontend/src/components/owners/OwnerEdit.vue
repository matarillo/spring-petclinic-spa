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

const { owner } = usePetStore()
const found = owner.find(ownerId.value)
const ownerToEdit = ref(
  found == null
    ? null
    : {
        id: found.id,
        firstName: found.firstName,
        lastName: found.lastName,
        address: found.address,
        city: found.city,
        telephone: found.telephone
      }
)

const edit = async () => {
  const value = ownerToEdit.value
  if (value == null) {
    return
  }
  await owner.edit(value)
  router.back()
}
</script>
<template>
  <v-container>
    <h2 class="text-h4">Owner</h2>
    <div class="w-50 mt-8" v-if="ownerToEdit != null">
      <v-text-field label="First name" v-model="ownerToEdit.firstName"></v-text-field>
      <v-text-field label="Last name" v-model="ownerToEdit.lastName"></v-text-field>
      <v-text-field label="Address" v-model="ownerToEdit.address"></v-text-field>
      <v-text-field label="City" v-model="ownerToEdit.city"></v-text-field>
      <v-text-field label="Telephone" v-model="ownerToEdit.telephone"></v-text-field>
      <v-btn block color="primary" @click="edit">Update Owner</v-btn>
    </div>
  </v-container>
</template>
