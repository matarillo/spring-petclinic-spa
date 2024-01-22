import { ref } from 'vue'
import { defineStore } from 'pinia'
import type {
  Owner,
  OwnerToAdd,
  OwnerToEdit,
  PetToAdd,
  PetToEdit,
  Vet,
  VisitToAdd
} from '@/api/types'
import apiClient from '@/api/apiClient'

export const usePetStore = defineStore('petStore', () => {
  /*
    ref()s become state properties
    computed()s become getters
    function()s become actions
   */
  const owners = ref([] as Owner[])
  const owner = {
    clear() {
      const prevLength = owners.value.length
      owners.value.splice(0, prevLength)
    },
    async list(lastName?: string) {
      const result = await apiClient.owner.list(lastName)
      const prevLength = owners.value.length
      owners.value.splice(0, prevLength, ...result)
    },
    find(ownerId: number) {
      return owners.value.find((o) => o.id === ownerId) ?? null
    },
    async add(ownerToAdd: OwnerToAdd) {
      await apiClient.owner.add(ownerToAdd)
    },
    async edit(ownerToEdit: OwnerToEdit) {
      const edited = await apiClient.owner.edit(ownerToEdit)
      const index = owners.value.findIndex((o) => o.id === ownerToEdit.id)
      if (index >= 0) {
        owners.value.splice(index, 1, edited)
      }
    }
  }
  const pet = {
    find(ownerId: number, petId: number) {
      const pets = owner.find(ownerId)?.pets
      if (!Array.isArray(pets)) {
        return null
      }
      return pets.find((p) => p.id == petId) ?? null
    },
    async add(ownerId: number, petToAdd: PetToAdd) {
      const added = await apiClient.pet.add(ownerId, petToAdd)
      added.visits = []
      const pets = owner.find(ownerId)?.pets
      if (!Array.isArray(pets)) {
        return
      }
      pets.push(added)
    },
    async edit(ownerId: number, petToEdit: PetToEdit) {
      const edited = await apiClient.pet.edit(ownerId, petToEdit)
      const pets = owner.find(ownerId)?.pets
      if (!Array.isArray(pets)) {
        return
      }
      const index = pets.findIndex((p) => p.id === petToEdit.id)
      if (index >= 0) {
        pets.splice(index, 1, edited)
      }
    }
  }
  const visit = {
    async add(ownerId: number, petId: number, visitToAdd: VisitToAdd) {
      const added = await apiClient.visit.add(ownerId, petId, visitToAdd)
      const visits = pet.find(ownerId, petId)?.visits
      if (!Array.isArray(visits)) {
        return
      }
      visits.push(added)
    }
  }
  const vets = ref([] as Vet[])
  const vet = {
    async list() {
      const result = await apiClient.vet.list()
      const prevLength = vets.value.length
      vets.value.splice(0, prevLength, ...result)
    }
  }
  return { owners, owner, pet, visit, vets, vet }
})
