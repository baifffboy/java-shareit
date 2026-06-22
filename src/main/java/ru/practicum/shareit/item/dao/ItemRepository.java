package ru.practicum.shareit.item.dao;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Item save(Item item);

    Optional<Item> findById(Long id);

    List<Item> findAll();

    List<Item> findByOwnerId(Long ownerId);

    Item update(Item item);

    void delete(Long id);

    boolean existsById(Long id);
}