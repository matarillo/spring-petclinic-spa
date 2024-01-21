<script setup lang="ts">
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'
const pet = ref({
  id: 2,
  name: 'Samantha',
  birthDate: '2012-09-04',
  type: 'cat',
  visits: [
    { date: '2013-01-04', descrition: 'spayed' },
    { date: '2013-01-01', descrition: 'rabies shot' }
  ],
  owner: 'Harold Davis'
})
const newVisit = ref({
  date: new Date(),
  descrition: ''
})
const formattedVisitDate = computed(() =>
  newVisit.value.date.toLocaleDateString('ja-JP', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit'
  })
)
const router = useRouter()
const add = () => {
  router.back()
}
</script>
<template>
  <v-container>
    <h2 class="text-h4">New Visit</h2>
    <v-card class="w-50 mt-8">
      <v-list lines="two">
        <v-list-item>
          <v-list-item-title>Owner</v-list-item-title>
          <v-list-item-subtitle>{{ pet.owner }}</v-list-item-subtitle>
        </v-list-item>
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
        <v-date-picker v-model="newVisit.date" no-title scrollable> </v-date-picker>
      </v-menu>
      <v-text-field label="Description" v-model="newVisit.descrition"></v-text-field>
      <v-btn block color="primary" @click="add">Add Visit</v-btn>
    </div>
    <h3 class="text-h5 mt-8">Previous Visits</h3>
    <v-table class="w-75">
      <thead>
        <tr>
          <th>Visit Date</th>
          <th>Description</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="visit in pet.visits" :key="visit.date">
          <td>{{ visit.date }}</td>
          <td>{{ visit.descrition }}</td>
        </tr>
      </tbody>
    </v-table>
  </v-container>
</template>
