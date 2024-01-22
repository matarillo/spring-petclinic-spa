export type OwnerToAdd = {
  firstName: string
  lastName: string
  address: string
  city: string
  telephone: string
}

export type OwnerToEdit = OwnerToAdd & {
  id: number
}

export type Owner = OwnerToEdit & {
  pets?: Pet[]
}

export type PetToAdd = {
  name: string
  birthDate: string
  type: string
}

export type PetToEdit = PetToAdd & {
  id: number
}

export type Pet = PetToEdit & {
  visits?: Visit[]
}

export type VisitToAdd = {
  visitDate: string
  description: string
}

export type VisitToEdit = VisitToAdd & {
  id: number
}

export type Visit = VisitToEdit & {
}

export type Vet = {
  id: number
  firstName: string
  lastName: string
  specialties: string[]
}