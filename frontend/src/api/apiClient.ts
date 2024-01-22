import axios from 'axios'
import type { Owner, OwnerToAdd, OwnerToEdit } from '@/api/types'

const baseUrl = '/api'
const axiosClient = axios.create()

const apiClient = {
  async listOwners(lastName?: string) {
    const url = `${baseUrl}/owners`
    const config = lastName == null ? {} : { params: { lastName } }
    const response = await axiosClient.get<Owner[]>(url, config)
    return response.data
  },
  async findOwner(ownerId: number) {
    const url = `${baseUrl}/owners/${ownerId}`
    const response = await axiosClient.get<Owner>(url)
    // TODO: handle 404 not found
    return response.data
  },
  async addOwner(ownerToAdd: OwnerToAdd) {
    const url = `${baseUrl}/owners`
    const response = await axiosClient.post<Owner>(url, ownerToAdd)
    return response.data
  },
  async editOwner(ownerToEdit: OwnerToEdit) {
    const url = `${baseUrl}/owners/${ownerToEdit.id}`
    const response = await axiosClient.put<Owner>(url, ownerToEdit)
    return response.data
  }
}
export default apiClient
