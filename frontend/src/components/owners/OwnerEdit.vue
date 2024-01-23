<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { usePetStore } from '@/stores/petStore'

const props = defineProps<{
  id: string
}>()
const ownerId = Number(props.id)

const router = useRouter()
const { owner } = usePetStore()

const foundOwner = owner.find(ownerId)
const ownerToEdit = ref(
  foundOwner == null
    ? null
    : {
        id: foundOwner.id,
        firstName: foundOwner.firstName,
        lastName: foundOwner.lastName,
        address: foundOwner.address,
        city: foundOwner.city,
        telephone: foundOwner.telephone
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
