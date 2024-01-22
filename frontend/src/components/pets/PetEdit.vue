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

const { owner, pet } = usePetStore()
const foundOwner = owner.find(ownerId.value)
const found = pet.find(ownerId.value, petId.value)

const ownerName = foundOwner == null ? '' : `${foundOwner.firstName} ${foundOwner.lastName}`

const petToEdit = ref(
  found == null
    ? null
    : {
        name: found.name,
        birthDate: new Date(found.birthDate),
        type: found.type
      }
)
const formatDate = (d: Date) =>
  [d.getFullYear(), ('0' + (d.getMonth() + 1)).slice(-2), ('0' + d.getDate()).slice(-2)].join('-')

const formattedBirthDate = computed(() => formatDate(petToEdit.value?.birthDate ?? new Date()))

const edit = async () => {
  // validation
  if (ownerId.value == null) {
    return
  }
  if (found == null) {
    return
  }
  const value = petToEdit.value
  if (value == null) {
    return
  }
  if (value.name == null || value.name.length === 0) {
    return
  }
  if (value.birthDate == null) {
    return
  }
  if (value.type == null || value.type.length === 0) {
    return
  }
  await pet.edit(ownerId.value, {
    id: found.id,
    name: value.name,
    birthDate: formatDate(value.birthDate),
    type: value.type
  })
  router.back()
}
const types = ['bird', 'cat', 'dog', 'hamster', 'lizard', 'snake']
</script>
<template>
  <v-container>
    <h2 class="text-h4">Pet</h2>
    <div class="w-50 mt-8" v-if="petToEdit != null">
      <v-text-field label="Owner" v-model="ownerName" disabled></v-text-field>
      <v-text-field label="Name" v-model="petToEdit.name"></v-text-field>
      <v-menu>
        <template v-slot:activator="{ props }">
          <v-text-field
            label="Birth Date"
            v-bind="props"
            v-model="formattedBirthDate"
            readonly
          ></v-text-field>
        </template>
        <v-date-picker v-model="petToEdit.birthDate" no-title scrollable> </v-date-picker>
      </v-menu>
      <v-select label="Type" :items="types" v-model="petToEdit.type"></v-select>
      <v-btn block color="primary" @click="edit">Update Pet</v-btn>
    </div>
  </v-container>
</template>
