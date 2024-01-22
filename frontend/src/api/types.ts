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

export type Pet = {
  id?: number
  name: string
  birthDate: string
  type: string
  visits?: Visit[]
}

export type Visit = {
  id?: number
  visitDate: string
  description: string
}
