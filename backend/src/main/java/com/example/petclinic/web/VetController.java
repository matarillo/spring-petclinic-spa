package com.example.petclinic.web;

import com.example.petclinic.data.SpecialtyRef;
import com.example.petclinic.data.SpecialtyRepository;
import com.example.petclinic.data.VetRepository;
import com.example.petclinic.json.VetDto;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

@RestController
@RequestMapping("/vets")
public class VetController {

    private final VetRepository vetRepository;
    private final SpecialtyRepository specialtyRepository;

    public VetController(VetRepository vetRepository, SpecialtyRepository specialtyRepository) {
        this.vetRepository = vetRepository;
        this.specialtyRepository = specialtyRepository;
    }

    @GetMapping
    public List<VetDto> list() {
        var iterable = this.specialtyRepository.findAll();
        Function<Integer, String> toName = id -> IterableUtils.find(iterable, s -> s.getId().equals(id)).getName();
        var vets =  this.vetRepository.findAll();
        var result = new ArrayList<VetDto>();
        for (var vet : vets) {
            Stream<Integer> ids = vet.getSpecialties().stream().map(SpecialtyRef::getSpecialtyId);
            var specialties = ids.map(toName).toList();
            result.add(new VetDto(vet.getId(), vet.getFirstName(), vet.getLastName(), specialties));
        }
        return result;
    }
}
