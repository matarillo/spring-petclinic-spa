package com.example.petclinic.json;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class VetDto {
    private Integer id;

    private String firstName;

    private String lastName;

    private List<String> specialties;
}
