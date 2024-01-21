package com.example.petclinic.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "SPECIALTIES")
public class Specialty {
    @Id
    private Integer id;

    private String name;
}
