package com.example.personalcoach.repository;

import com.example.personalcoach.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findFirstByName(String name);
    Optional<Brand> findFirstById(Long id);

}
