package com.example.petclinic.web;

import com.example.petclinic.data.SpecialtyRepository;
import com.example.petclinic.data.VetRepository;
import com.example.petclinic.json.CustomConverter;
import com.example.petclinic.json.VetDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/vets")
public class VetController {

    private final VetRepository vetRepository;
    private final SpecialtyRepository specialtyRepository;
    private final CustomConverter converter = new CustomConverter();

    public VetController(VetRepository vetRepository, SpecialtyRepository specialtyRepository) {
        this.vetRepository = vetRepository;
        this.specialtyRepository = specialtyRepository;
    }

    @GetMapping
    public ResponseEntity<List<VetDto>> list() {
        var specialties = this.specialtyRepository.findAll();
        var vets =  this.vetRepository.findAll();
        var result = StreamSupport.stream(vets.spliterator(), false)
                .map(converter.mapToVetDto(specialties))
                .toList();
        return ResponseEntity.ok(result);
    }
}
