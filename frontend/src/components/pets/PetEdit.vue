<script setup lang="ts">
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'
const router = useRouter()
const pet = ref({
  owner: 'Harold Davis',
  name: 'Iggy',
  birthDate: new Date(2010, 10, 30),
  type: 'lizard'
})
const formattedBirthDate = computed(() =>
  pet.value.birthDate.toLocaleDateString('ja-JP', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit'
  })
)
const edit = () => {
  router.back()
}
const types = ['bird', 'cat', 'dog', 'hamster', 'lizard', 'snake']
</script>
<template>
  <v-container>
    <h2 class="text-h4">Pet</h2>
    <div class="w-50 mt-8">
      <v-text-field label="Owner" v-model="pet.owner" disabled></v-text-field>
      <v-text-field label="Name" v-model="pet.name"></v-text-field>
      <v-menu>
        <template v-slot:activator="{ props }">
          <v-text-field
            label="Birth Date"
            v-bind="props"
            v-model="formattedBirthDate"
            readonly
          ></v-text-field>
        </template>
        <v-date-picker v-model="pet.birthDate" no-title scrollable> </v-date-picker>
      </v-menu>
      <v-select label="Type" :items="types" v-model="pet.type"></v-select>
      <v-btn block color="primary" @click="edit">Update Pet</v-btn>
    </div>
  </v-container>
</template>
