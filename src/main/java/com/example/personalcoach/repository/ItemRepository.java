package com.example.personalcoach.repository;

import com.example.personalcoach.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
