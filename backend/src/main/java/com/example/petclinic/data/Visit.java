package com.example.petclinic.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table(name = "VISITS")
public class Visit {
    @Id
    private Integer id;

    private Integer petId;

    private LocalDate visitDate;

    private String description;
}
