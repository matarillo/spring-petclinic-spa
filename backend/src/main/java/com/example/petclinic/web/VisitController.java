package com.example.petclinic.web;

import com.example.petclinic.data.OwnerRepository;
import com.example.petclinic.data.Visit;
import com.example.petclinic.data.VisitRepository;
import com.example.petclinic.json.VisitDto;
import jakarta.validation.Valid;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("owners/{ownerId}/pets/{petId}/visits")
public class VisitController {

    private final OwnerRepository ownerRepository;
    private final VisitRepository visitRepository;

    public VisitController(OwnerRepository ownerRepository, VisitRepository visitRepository) {
        this.ownerRepository = ownerRepository;
        this.visitRepository = visitRepository;
    }

    @PostMapping
    public ResponseEntity<VisitDto> add(@PathVariable Integer ownerId, @PathVariable Integer petId, @RequestBody @Valid VisitDto newVisit) {
        var owner = this.ownerRepository.findById(ownerId);
        if (owner.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var petOrNull = IterableUtils.find(owner.get().getPets(), p -> p.getId().equals(petId));
        if (petOrNull == null) {
            return ResponseEntity.notFound().build();
        }
        Visit inserting = new Visit();
        inserting.setPetId(petId);
        inserting.setVisitDate(newVisit.getVisitDate());
        inserting.setDescription(newVisit.getDescription());
        var inserted = this.visitRepository.save(inserting);
        var result = new VisitDto(inserted.getId(), inserted.getVisitDate(), inserted.getDescription());
        return ResponseEntity.ok(result);
    }
}
