package com.example.springboot.jpa.springboot_jpa.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.springboot.jpa.springboot_jpa.entities.Person;
import java.util.List;


public interface PersonRepository extends CrudRepository<Person, Long> {
    
    List<Person> findByProgrammingLanguage(String programmingLanguage);
}
