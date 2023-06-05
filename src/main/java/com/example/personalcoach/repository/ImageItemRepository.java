package com.example.personalcoach.repository;

import com.example.personalcoach.model.ImageItem;
import com.example.personalcoach.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageItemRepository extends JpaRepository<ImageItem, Long> {

    Optional<List<ImageItem>> findAllByItemId(Item itemId);
}
