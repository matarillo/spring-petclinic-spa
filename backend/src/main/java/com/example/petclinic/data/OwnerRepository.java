package com.example.petclinic.data;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends CrudRepository<Owner, Integer> {

    @Query("""
SELECT ID, FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE
FROM OWNERS
WHERE LAST_NAME LIKE CONCAT(:lastName,'%')
""")
    public Iterable<Owner> findAllByLastName(String lastName);
}
