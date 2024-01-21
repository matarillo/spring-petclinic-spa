package com.example.petclinic.json;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class FooDto {
    private String name;

    private String value;

    private LocalDate localDate;
}
