package com.example.petclinic.data;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TypeRepository extends CrudRepository<Type, Integer> {

    public Optional<Type> findByName(String name);
}
