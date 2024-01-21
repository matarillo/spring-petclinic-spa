package com.example.petclinic.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;

@Data
@Table(name = "VETS")
public class Vet {
    @Id
    private Integer id;

    private String firstName;

    private String lastName;

    @MappedCollection(idColumn = "VET_ID")
    private Set<SpecialtyRef> specialties = new HashSet<>();
}
