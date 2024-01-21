package com.example.petclinic.json;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class VisitDto {
    private Integer id;

    @NotNull
    private LocalDate visitDate;

    private String description;
}
