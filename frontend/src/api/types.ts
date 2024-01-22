export type Owner = {
  id: number
  firstName: string
  lastName: string
  address: string
  city: string
  telephone: string
  pets: Pet[]
}

export type Pet = {
  id: number
  name: string
  birthDate: string
  type: string
  visits: Visit[]
}

export type Visit = {
  id: number
  visitDate: string
  description: string
}
