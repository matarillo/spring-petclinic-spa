package com.example.petclinic.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@Table(name = "VET_SPECIALTIES")
public class SpecialtyRef {
    private Integer specialtyId;
}
