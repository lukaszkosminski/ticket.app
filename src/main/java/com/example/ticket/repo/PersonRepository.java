package com.example.ticket.repo;

import com.example.ticket.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByIsActiveTrue();
}
