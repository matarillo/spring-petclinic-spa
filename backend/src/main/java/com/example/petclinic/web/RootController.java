package com.example.petclinic.web;

import com.example.petclinic.data.*;
import org.springframework.web.bind.annotation.*;

import org.apache.commons.collections4.IterableUtils;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class RootController {

    private final TypeRepository typeRepository;

    private final VisitRepository visitRepository;

    private final OwnerRepository ownerRepository;

    private final PetRepository petRepository;

    public RootController(TypeRepository typeRepository, VetRepository vetRepository, SpecialtyRepository specialtyRepository, VisitRepository visitRepository, OwnerRepository ownerRepository, PetRepository petRepository) {
        this.typeRepository = typeRepository;
        this.visitRepository = visitRepository;
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
    }

    @GetMapping("/types")
    public List<Type> getAllTypes() {
        return IterableUtils.toList(this.typeRepository.findAll());
    }

    @GetMapping("/visits")
    public List<Visit> getAllVisits() {
        return IterableUtils.toList(this.visitRepository.findAll());
    }

    @GetMapping("/owners")
    public List<Owner> getAllOwnersByLastName(@RequestParam Optional<String> lastName) {
        var owners = lastName.isPresent()
                ? this.ownerRepository.findAllByLastName(lastName.get())
                : this.ownerRepository.findAll();
        return IterableUtils.toList(owners);
    }

    @GetMapping("/pets")
    public List<Pet> getAllPets() {
        return IterableUtils.toList(this.petRepository.findAll());
    }
}
