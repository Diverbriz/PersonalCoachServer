package com.example.personalcoach.repository;

import com.example.personalcoach.model.Basket;
import com.example.personalcoach.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {

    Optional<Basket> findByUserId(User user);
}
