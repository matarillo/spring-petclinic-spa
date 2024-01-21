package com.example.petclinic.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "TYPES")
public class Type {
    @Id
    private Integer id;

    private String name;
}
