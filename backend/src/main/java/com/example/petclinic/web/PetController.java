package com.example.petclinic.web;

import com.example.petclinic.data.OwnerRepository;
import com.example.petclinic.data.Pet;
import com.example.petclinic.data.Type;
import com.example.petclinic.data.TypeRepository;
import com.example.petclinic.json.PetDto;
import com.example.petclinic.json.VisitDto;
import jakarta.validation.Valid;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("owners/{ownerId}/pets")
public class PetController {

    private final OwnerRepository ownerRepository;
    private final TypeRepository typeRepository;

    public PetController(OwnerRepository ownerRepository, TypeRepository typeRepository) {
        this.ownerRepository = ownerRepository;
        this.typeRepository = typeRepository;
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

        var type = this.typeRepository.findByName(newPet.getType());
        return null;




    }
}
