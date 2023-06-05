package com.example.personalcoach.repository;

import com.example.personalcoach.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeRepository extends JpaRepository<Type, Long> {
    Optional<Type> findFirstByName(String name);

    Optional<Type> findFirstById(Long id);
}
