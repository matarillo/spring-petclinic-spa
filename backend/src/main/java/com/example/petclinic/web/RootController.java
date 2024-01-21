package com.example.petclinic.web;

import com.example.petclinic.data.*;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class RootController {

    private final TypeRepository typeRepository;

    private final VisitRepository visitRepository;

    private final PetRepository petRepository;

    public RootController(TypeRepository typeRepository, VetRepository vetRepository, SpecialtyRepository specialtyRepository, VisitRepository visitRepository, OwnerRepository ownerRepository, PetRepository petRepository) {
        this.typeRepository = typeRepository;
        this.visitRepository = visitRepository;
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

    @GetMapping("/pets")
    public List<Pet> getAllPets() {
        return IterableUtils.toList(this.petRepository.findAll());
    }
}
