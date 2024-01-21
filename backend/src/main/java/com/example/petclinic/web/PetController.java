package com.example.petclinic.web;

import com.example.petclinic.data.*;
import com.example.petclinic.json.CustomConverter;
import com.example.petclinic.json.PetDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("owners/{ownerId}/pets")
public class PetController {

    private final OwnerRepository ownerRepository;
    private final TypeRepository typeRepository;
    private final PetRepository petRepository;
    private final CustomConverter converter = new CustomConverter();

    public PetController(OwnerRepository ownerRepository, TypeRepository typeRepository, PetRepository petRepository) {
        this.ownerRepository = ownerRepository;
        this.typeRepository = typeRepository;
        this.petRepository = petRepository;
    }

    @GetMapping
    public ResponseEntity<List<PetDto>> list(@PathVariable Integer ownerId) {
        var owner = this.ownerRepository.findById(ownerId);
        if (owner.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var types = this.typeRepository.findAll();
        var pets = owner.get().getPets();
        var result = pets.stream()
                .map(converter.mapToPetDto(types))
                .toList();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<PetDto> add(@PathVariable Integer ownerId, @RequestBody @Valid PetDto newPet) {
        var owner = this.ownerRepository.findById(ownerId);
        if (owner.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var typeName = newPet.getType();
        var type = this.typeRepository.findByName(typeName);
        if (type.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var types = single(type.get());

        Pet inserting = new Pet();
        converter.fillFromPetDto(newPet, inserting, types);
        inserting.setOwnerId(ownerId);
        Pet inserted = this.petRepository.save(inserting);
        PetDto result = converter.mapToPetDto(types).apply(inserted);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{petId}")
    public ResponseEntity<PetDto> edit(@PathVariable Integer ownerId, @PathVariable Integer petId, @RequestBody @Valid PetDto pet) {
        var currentPetOrNull = this.petRepository.findById(petId);
        if (currentPetOrNull.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var currentPet = currentPetOrNull.get();
        if (!(currentPet.getOwnerId().equals(ownerId))) {
            return ResponseEntity.notFound().build();
        }
        if (pet.getId() != null && !(pet.getId().equals(petId))) {
            return ResponseEntity.badRequest().build();
        }

        var typeName = pet.getType();
        var type = this.typeRepository.findByName(typeName);
        if (type.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var types = single(type.get());

        converter.fillFromPetDto(pet, currentPet, types);
        Pet updated = this.petRepository.save(currentPet);
        updated.setVisits(currentPet.getVisits());
        PetDto result = converter.mapToPetDto(types).apply(updated);
        return ResponseEntity.ok(result);
    }

    private List<Type> single(Type type) {
        List<Type> list = new ArrayList<>();
        list.add(type);
        return list;
    }
}
