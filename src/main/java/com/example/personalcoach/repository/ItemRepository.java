package com.example.personalcoach.repository;

import com.example.personalcoach.model.Brand;
import com.example.personalcoach.model.Item;
import com.example.personalcoach.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<List<Item>> findAllByBrandIdAndTypeId(Brand brand, Type type);

    Optional<List<Item>> findAllByBrandId(Brand brand);

    Optional<List<Item>> findAllByTypeId(Type type);
    Optional<List<Item>> findItemsByName(String name);

//    Optional<List<Item>> findAllByOrderByName(String name);

    Optional<List<Item>> findAllByNameAndBrandIdAndTypeId(String name, Brand brand, Type type);
    Optional<Item> findFirstByName(String name);

}
