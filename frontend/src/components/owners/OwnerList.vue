<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { RouterLink, useRoute, useRouter } from 'vue-router'
import { usePetStore } from '@/stores/petStore'
import type { Owner } from '@/api/types'

const route = useRoute()
const router = useRouter()
const lastName = ref('')
const lastNameQuery = computed(() => {
  const value = route.query[`lastName`]
  return Array.isArray(value) ? value.join(',') : value
})

const { owners, owner } = usePetStore()
const updateStore = async () => {
  const value = lastNameQuery.value
  if (value === void 0) {
    // no query; initialize
    owner.clear()
  } else if (value == null) {
    // empty query; search all
    await owner.list()
  } else {
    await owner.list(value)
  }
}
const find = async () => {
  await router.push({
    path: '/owners',
    query: {
      lastName: lastName.value
    }
  })
  await updateStore()
}
const add = async () => {
  await router.push('/owners/new')
}

const headers = [
  { title: 'Name', key: 'name' },
  { title: 'Address', key: 'address' },
  { title: 'City', key: 'city' },
  { title: 'Telephone', key: 'telephone' },
  { title: 'Pets', key: 'pets' }
]
onMounted(async () => {
  await updateStore()
})
</script>

<template>
  <v-container v-if="lastNameQuery == null">
    <h2 class="text-h4">Find Owners</h2>
    <div class="w-50 mt-8">
      <v-text-field label="Last name" v-model="lastName">
        <template v-slot:append-inner>
          <v-btn size="small" color="primary" @click="find">Find Owner</v-btn>
        </template>
      </v-text-field>
      <v-btn color="primary" @click="add">Add Owner</v-btn>
    </div>
  </v-container>
  <v-container v-if="lastNameQuery != null">
    <h2 class="text-h4">Owners</h2>
    <v-data-table class="mt-8 owners" :headers="headers" :items="owners">
      <template v-slot:[`item.name`]="{ item }: { item: Owner }">
        <RouterLink :to="`/owners/${item.id}`">
          {{ `${item.firstName} ${item.lastName}` }}
        </RouterLink>
      </template>
      <template v-slot:[`item.pets`]="{ item }: { item: Owner }">
        {{ (item.pets || []).map((p) => p.name).join(' ') }}
      </template>
    </v-data-table>
  </v-container>
</template>
<style scoped>
.owners :deep(thead th) {
  font-weight: 700 !important;
}
</style>
