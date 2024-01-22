package com.example.petclinic.web;

import com.example.petclinic.data.Owner;
import com.example.petclinic.data.OwnerRepository;
import com.example.petclinic.data.TypeRepository;
import com.example.petclinic.json.CustomConverter;
import com.example.petclinic.json.OwnerDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerRepository ownerRepository;
    private final TypeRepository typeRepository;
    private final CustomConverter converter = new CustomConverter();

    public OwnerController(OwnerRepository ownerRepository, TypeRepository typeRepository) {
        this.ownerRepository = ownerRepository;
        this.typeRepository = typeRepository;
    }

    @GetMapping
    public ResponseEntity<List<OwnerDto>> list(@RequestParam Optional<String> lastName) {
        var owners = lastName.isPresent()
                ? this.ownerRepository.findAllByLastName(lastName.get())
                : this.ownerRepository.findAll();
        var types = this.typeRepository.findAll();
        var result = StreamSupport.stream(owners.spliterator(), false)
                .map(converter.mapToOwnerDto(types))
                .toList();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<OwnerDto> add(@RequestBody @Valid OwnerDto dto) {
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        Owner inserting = new Owner();
        converter.fillFromOwnerDto(dto, inserting);
        Owner inserted = this.ownerRepository.save(inserting);
        var result = converter.mapToOwnerDto(null).apply(inserted);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<OwnerDto> find(@PathVariable Integer ownerId) {
        var optional = this.ownerRepository.findById(ownerId);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Owner found = optional.get();
        var types = this.typeRepository.findAll();
        var result = converter.mapToOwnerDto(types).apply(found);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{ownerId}")
    public ResponseEntity<OwnerDto> edit(@PathVariable Integer ownerId, @RequestBody @Valid OwnerDto dto) {
        var optional = this.ownerRepository.findById(ownerId);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Owner updating = optional.get();
        if (dto.getId() != null && !dto.getId().equals(ownerId)) {
            return ResponseEntity.badRequest().build();
        }
        converter.fillFromOwnerDto(dto, updating);
        var updated = this.ownerRepository.save(updating);
        var types = this.typeRepository.findAll();
        var result = converter.mapToOwnerDto(types).apply(updated);
        return ResponseEntity.ok(result);
    }
}
