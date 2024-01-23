<script setup lang="ts">
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'
import { usePetStore } from '@/stores/petStore'

const props = defineProps<{
  id: string
  petId: string
}>()
const ownerId = Number(props.id)
const petId = Number(props.petId)

const router = useRouter()
const { owner, pet } = usePetStore()

const foundOwner = owner.find(ownerId)
const foundPet = pet.find(ownerId, petId)

const ownerName = foundOwner == null ? '' : `${foundOwner.firstName} ${foundOwner.lastName}`

const petToEdit = ref(
  foundPet == null
    ? null
    : {
        name: foundPet.name,
        birthDate: new Date(foundPet.birthDate),
        type: foundPet.type
      }
)
const formatDate = (d: Date) =>
  [d.getFullYear(), ('0' + (d.getMonth() + 1)).slice(-2), ('0' + d.getDate()).slice(-2)].join('-')

const formattedBirthDate = computed(() => formatDate(petToEdit.value?.birthDate ?? new Date()))

const edit = async () => {
  // validation
  if (isNaN(ownerId)) {
    return
  }
  if (foundPet == null) {
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
  await pet.edit(ownerId, {
    id: foundPet.id,
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
