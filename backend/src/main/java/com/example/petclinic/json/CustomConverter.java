package com.example.petclinic.json;

import com.example.petclinic.data.*;
import org.apache.commons.collections4.IterableUtils;

import java.util.function.Function;

public class CustomConverter {
    // Owner

    /**
     * Map
     */
    public Function<Owner, OwnerDto> mapToOwnerDto(Iterable<Type> typesOrNull) {
        return owner -> {
            OwnerDto dto = new OwnerDto();
            dto.setId(owner.getId());
            dto.setFirstName(owner.getFirstName());
            dto.setLastName(owner.getLastName());
            dto.setAddress(owner.getAddress());
            dto.setCity(owner.getCity());
            dto.setTelephone(owner.getTelephone());
            if (owner.getPets() != null && typesOrNull != null) {
                dto.setPets(owner.getPets().stream().map(mapToPetDto(typesOrNull)).toList());
            }
            return dto;
        };
    }

    /**
     * Fill except ID
     */
    public void fillFromOwnerDto(OwnerDto from, Owner to) {
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setAddress(from.getAddress());
        to.setCity(from.getAddress());
        to.setTelephone(from.getTelephone());
    }

    // Pet

    /**
     * Map
     */
    public Function<Pet, PetDto> mapToPetDto(Iterable<Type> types) {
        return pet -> {
            PetDto dto = new PetDto();
            dto.setId(pet.getId());
            dto.setName(pet.getName());
            dto.setBirthDate(pet.getBirthDate());
            dto.setType(getTypeName(types, pet.getTypeId()));
            if (pet.getVisits() != null) {
                dto.setVisits(pet.getVisits().stream().map(this::mapToVisitDto).toList());
            }
            return dto;
        };
    }

    /**
     * Fill except ID
     */
    public void fillFromPetDto(PetDto from, Pet to, Iterable<Type> types) {
        to.setName(from.getName());
        to.setBirthDate(from.getBirthDate());
        to.setTypeId(getTypeId(types, from.getType()));
    }

    // Type

    public Integer getTypeId(Iterable<Type> types, String name) {
        Type type = IterableUtils.find(types, x -> x.getName().equals(name));
        return type.getId();
    }

    public String getTypeName(Iterable<Type> types, Integer id) {
        Type type = IterableUtils.find(types, x -> x.getId().equals(id));
        return type.getName();
    }

    // Visit

    /**
     * Map
     */
    public VisitDto mapToVisitDto(Visit visit) {
        VisitDto dto = new VisitDto();
        dto.setId(visit.getId());
        dto.setVisitDate(visit.getVisitDate());
        dto.setDescription(visit.getDescription());
        return dto;
    }

    /**
     * Fill except ID
     */
    public void fillFromVisitDto(VisitDto from, Visit to) {
        to.setVisitDate(from.getVisitDate());
        to.setDescription(from.getDescription());
    }

    // Vet

    /**
     * Map
     */
    public Function<Vet, VetDto> mapToVetDto(Iterable<Specialty> specialties) {
        return vet -> {
            VetDto dto = new VetDto();
            dto.setId(vet.getId());
            dto.setFirstName(vet.getFirstName());
            dto.setLastName(vet.getLastName());
            dto.setSpecialties(vet.getSpecialties().stream().map(mapToString(specialties)).toList());
            return dto;
        };
    }

    // SpecialtyRef

    /**
     * Map
     */
    public Function<SpecialtyRef, String> mapToString(Iterable<Specialty> specialties) {
        return specialtyRef -> this.getSpecialtyName(specialties, specialtyRef.getSpecialtyId());
    }

    // Specialty

    public Integer getSpecialtyId(Iterable<Specialty> specialties, String name) {
        Specialty specialty = IterableUtils.find(specialties, x -> x.getName().equals(name));
        return specialty.getId();
    }

    public String getSpecialtyName(Iterable<Specialty> specialties, Integer id) {
        Specialty specialty = IterableUtils.find(specialties, x -> x.getId().equals(id));
        return specialty.getName();
    }
}

