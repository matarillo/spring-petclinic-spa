package com.example.petclinic.json;

import com.example.petclinic.data.Owner;
import com.example.petclinic.data.Pet;
import com.example.petclinic.data.Type;
import com.example.petclinic.data.Visit;
import org.apache.commons.collections4.IterableUtils;

public class Converter {
    private final Iterable<Type> types;

    public Converter(Iterable<Type> types) {
        this.types = types;
    }

    private String fromTypeId(Integer typeId) {
        Type type = IterableUtils.find(types, t -> t.getId().equals(typeId));
        return (type == null) ? null : type.getName();
    }

    public OwnerDto from(Owner owner) {
        OwnerDto dto = new OwnerDto();
        dto.setId(owner.getId());
        dto.setFirstName(owner.getFirstName());
        dto.setLastName(owner.getLastName());
        dto.setAddress(owner.getAddress());
        dto.setCity(owner.getCity());
        dto.setTelephone(owner.getTelephone());
        dto.setPets(owner.getPets().stream().map(this::from).toList());
        return dto;
    }

    public PetDto from(Pet pet) {
        PetDto dto = new PetDto();
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setBirthDate(pet.getBirthDate());
        dto.setType(fromTypeId(pet.getTypeId()));
        dto.setVisits(pet.getVisits().stream().map(this::from).toList());
        return dto;
    }

    public VisitDto from(Visit visit) {
        VisitDto dto = new VisitDto();
        dto.setId(visit.getId());
        dto.setVisitDate(visit.getVisitDate());
        dto.setDescription(visit.getDescription());
        return dto;
    }
}
