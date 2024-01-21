package com.example.petclinic.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;

@Data
@Table(name = "OWNERS")
public class Owner {
    @Id
    private Integer id;

    private String firstName;

    private String lastName;

    private String address;

    private String city;

    private String telephone;

    @MappedCollection(idColumn = "OWNER_ID")
    private Set<Pet> pets = new HashSet<>();
}
