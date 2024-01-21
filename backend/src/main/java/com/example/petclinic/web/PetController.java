package com.example.petclinic.web;

import com.example.petclinic.data.*;
import com.example.petclinic.json.PetDto;
import com.example.petclinic.json.VisitDto;
import jakarta.validation.Valid;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("owners/{ownerId}/pets")
public class PetController {

    private final OwnerRepository ownerRepository;
    private final TypeRepository typeRepository;
    private final PetRepository petRepository;

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

        var iterable = this.typeRepository.findAll();
        Function<Integer, Type> toType = id -> IterableUtils.find(iterable, t -> t.getId().equals(id));

        Function<Pet, PetDto> toDto = pet -> {
            var typeName = toType.apply(pet.getTypeId()).getName();
            var visits = pet.getVisits().stream()
                    .map(x -> new VisitDto(x.getId(), x.getVisitDate(), x.getDescription()))
                    .toList();
            return new PetDto(pet.getId(), pet.getName(), pet.getBirthDate(), typeName, visits);
        };
        var result = owner.get().getPets().stream().map(toDto).toList();
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

        Pet inserting = new Pet();
        inserting.setName(newPet.getName());
        inserting.setBirthDate(newPet.getBirthDate());
        inserting.setTypeId(type.get().getId());
        inserting.setOwnerId(ownerId);
        Pet inserted = this.petRepository.save(inserting);
        PetDto result = new PetDto(inserted.getId(), inserted.getName(), inserted.getBirthDate(), typeName, new ArrayList<>());
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
        var typeOrNull = this.typeRepository.findByName(typeName);
        if (typeOrNull.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var type = typeOrNull.get();

        // id is unchanged
        currentPet.setName(pet.getName());
        currentPet.setBirthDate(pet.getBirthDate());
        currentPet.setTypeId(type.getId());
        // ownerId is unchanged
        // visits are unchanged
        Pet updated = this.petRepository.save(currentPet);

        PetDto result = new PetDto();
        result.setId(updated.getId());
        result.setName(updated.getName());
        result.setBirthDate(updated.getBirthDate());
        result.setType(typeName);
        var visits = currentPet.getVisits().stream()
                .map(v -> new VisitDto(v.getId(), v.getVisitDate(), v.getDescription()))
                .toList();
        result.setVisits(visits);
        return ResponseEntity.ok(result);
    }
}
