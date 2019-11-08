package com.example.dogs;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.*;

@Repository
public interface BreedRepository extends CrudRepository<Breed, Long>{

}
