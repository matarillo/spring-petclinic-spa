<script setup lang="ts">
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'
import { usePetStore } from '@/stores/petStore'

const props = defineProps<{
  id: string
}>()
const ownerId = Number(props.id)

const router = useRouter()
const { owner, pet } = usePetStore()

const foundOwner = owner.find(Number(props.id))
const petToAdd = ref({
  owner: foundOwner == null ? '' : `${foundOwner.firstName} ${foundOwner.lastName}`,
  name: '',
  birthDate: new Date(),
  type: ''
})

const formatDate = (d: Date) =>
  [d.getFullYear(), ('0' + (d.getMonth() + 1)).slice(-2), ('0' + d.getDate()).slice(-2)].join('-')

const formattedBirthDate = computed(() => formatDate(petToAdd.value.birthDate))
const add = async () => {
  // validation
  if (isNaN(ownerId)) {
    return
  }
  const value = petToAdd.value
  if (value.name == null || value.name.length === 0) {
    return
  }
  if (value.birthDate == null) {
    return
  }
  if (value.type == null || value.type.length === 0) {
    return
  }
  await pet.add(ownerId, {
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
    <h2 class="text-h4">New Pet</h2>
    <div class="w-50 mt-8">
      <v-text-field label="Owner" v-model="petToAdd.owner" disabled></v-text-field>
      <v-text-field label="Name" v-model="petToAdd.name"></v-text-field>
      <v-menu>
        <template v-slot:activator="{ props }">
          <v-text-field
            label="Birth Date"
            v-bind="props"
            v-model="formattedBirthDate"
            readonly
          ></v-text-field>
        </template>
        <v-date-picker v-model="petToAdd.birthDate" no-title scrollable> </v-date-picker>
      </v-menu>
      <v-select label="Type" :items="types" v-model="petToAdd.type"></v-select>
      <v-btn block color="primary" @click="add">Add Pet</v-btn>
    </div>
  </v-container>
</template>
