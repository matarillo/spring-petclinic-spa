package com.example.petclinic.json;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class PetDto {
    private Integer id;

    @NotEmpty
    private String name;

    @NotNull
    private LocalDate birthDate;

    @NotEmpty
    private String type;

    private List<VisitDto> visits;
}
