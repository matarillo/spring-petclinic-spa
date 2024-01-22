import axios from 'axios'
import type {
  Owner,
  OwnerToAdd,
  OwnerToEdit,
  Pet,
  PetToAdd,
  PetToEdit,
  Vet,
  Visit,
  VisitToAdd
} from '@/api/types'

const baseUrl = '/api'
const axiosClient = axios.create()

const apiClient = {
  owner: {
    async list(lastName?: string) {
      const url = `${baseUrl}/owners`
      const config = lastName == null ? {} : { params: { lastName } }
      const response = await axiosClient.get<Owner[]>(url, config)
      return response.data
    },
    async find(ownerId: number) {
      const url = `${baseUrl}/owners/${ownerId}`
      const response = await axiosClient.get<Owner>(url) // throws AxiosError when 404
      return response.data
    },
    async add(ownerToAdd: OwnerToAdd) {
      const url = `${baseUrl}/owners`
      const response = await axiosClient.post<Owner>(url, ownerToAdd)
      return response.data
    },
    async edit(ownerToEdit: OwnerToEdit) {
      const url = `${baseUrl}/owners/${ownerToEdit.id}`
      const response = await axiosClient.put<Owner>(url, ownerToEdit)
      return response.data
    }
  },
  pet: {
    async list(ownerId: number) {
      const url = `${baseUrl}/owners/${ownerId}/pets`
      const response = await axiosClient.get<Pet[]>(url)
      return response.data
    },
    async add(ownerId: number, petToAdd: PetToAdd) {
      const url = `${baseUrl}/owners/${ownerId}/pets`
      const response = await axiosClient.post<Pet>(url, petToAdd)
      return response.data
    },
    async edit(ownerId: number, petToEdit: PetToEdit) {
      const url = `${baseUrl}/owners/${ownerId}/pets/${petToEdit.id}`
      const response = await axiosClient.put<Pet>(url, petToEdit)
      return response.data
    }
  },
  visit: {
    async add(ownerId: number, petId: number, visitToAdd: VisitToAdd) {
      const url = `${baseUrl}/owners/${ownerId}/pets/${petId}/visits`
      const response = await axiosClient.post<Visit>(url, visitToAdd)
      return response.data
    }
  },
  vet: {
    async list() {
      const url = `${baseUrl}/vets`
      const response = await axiosClient.get<Vet[]>(url)
      return response.data
    }
  }
}
export default apiClient
