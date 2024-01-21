package com.example.petclinic.web;

import com.example.petclinic.data.*;
import com.example.petclinic.json.Converter;
import com.example.petclinic.json.OwnerDto;
import com.example.petclinic.json.PetDto;
import com.example.petclinic.json.VisitDto;
import jakarta.validation.Valid;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerRepository ownerRepository;
    private final TypeRepository typeRepository;

    public OwnerController(OwnerRepository ownerRepository, TypeRepository typeRepository) {
        this.ownerRepository = ownerRepository;
        this.typeRepository = typeRepository;
    }

    @GetMapping
    public ResponseEntity<List<OwnerDto>> list(@RequestParam Optional<String> lastName) {
        var iterable = lastName.isPresent()
                ? this.ownerRepository.findAllByLastName(lastName.get())
                : this.ownerRepository.findAll();
        var owners = IterableUtils.toList(iterable);
        var converter = new Converter(this.typeRepository.findAll());
        var result = owners.stream().map(converter::from).toList();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<OwnerDto> add(@RequestBody @Valid OwnerDto dto) {
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        Owner inserting = new Owner();
        fromDto(dto, inserting);
        Owner inserted = this.ownerRepository.save(inserting);
        OwnerDto result = new OwnerDto();
        toDto(inserted, result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<OwnerDto> find(@RequestParam Integer ownerId) {
        var optional = this.ownerRepository.findById(ownerId);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Owner found = optional.get();
        var converter = new Converter(this.typeRepository.findAll());
        var result = converter.from(found);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{ownerId}")
    public ResponseEntity<OwnerDto> edit(@RequestParam Integer ownerId, @RequestBody @Valid OwnerDto dto) {
        var optional = this.ownerRepository.findById(ownerId);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Owner updating = optional.get();
        if (dto.getId() != null && !dto.getId().equals(ownerId)) {
            return ResponseEntity.badRequest().build();
        }
        fromDto(dto, updating);
        var updated = this.ownerRepository.save(updating);
        var converter = new Converter(this.typeRepository.findAll());
        var result = converter.from(updating);
        return ResponseEntity.ok(result);
    }

    private void fromDto(OwnerDto source, Owner destination) {
        destination.setFirstName(source.getFirstName());
        destination.setLastName(source.getLastName());
        destination.setAddress(source.getAddress());
        destination.setCity(source.getCity());
        destination.setTelephone(source.getTelephone());
    }

    private void toDto(Owner source, OwnerDto destination) {
        destination.setId(source.getId());
        destination.setFirstName(source.getFirstName());
        destination.setLastName(source.getLastName());
        destination.setAddress(source.getAddress());
        destination.setCity(source.getCity());
        destination.setTelephone(source.getTelephone());
    }
}
