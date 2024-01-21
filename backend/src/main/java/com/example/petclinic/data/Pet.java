package com.example.petclinic.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Table(name = "PETS")
public class Pet {
    @Id
    private Integer id;

    private String name;

    private LocalDate birthDate;

    private Integer typeId;

    private Integer ownerId;

    @MappedCollection(idColumn = "PET_ID")
    private Set<Visit> visits = new HashSet<>();

}
