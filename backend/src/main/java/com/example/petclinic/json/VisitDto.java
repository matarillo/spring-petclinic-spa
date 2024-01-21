package com.example.petclinic.json;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitDto {
    private Integer id;

    @NotNull
    private LocalDate visitDate;

    private String description;
}
