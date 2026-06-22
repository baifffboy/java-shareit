package ru.practicum.shareit.item.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository("inMemory")
@Slf4j
public class ItemRepositoryImplInMemory implements ItemRepository {

    private final Map<Long, Item> items = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Item save(Item item) {
        if (item.getId() == null) {
            item.setId(idGenerator.getAndIncrement());
        }
        if (item.getReviews() == null) {
            item.setReviews(new ArrayList<>());
        }
        items.put(item.getId(), item);
        log.debug("Сохранена вещь: {}", item);
        return item;
    }

    @Override
    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(items.get(id));
    }

    @Override
    public List<Item> findAll() {
        return new ArrayList<>(items.values());
    }

    @Override
    public List<Item> findByOwnerId(Long ownerId) {
        return items.values().stream()
                .filter(item -> item.getOwner() != null && item.getOwner().getId().equals(ownerId))
                .collect(Collectors.toList());
    }

    @Override
    public Item update(Item item) {
        items.put(item.getId(), item);
        log.debug("Обновлена вещь: {}", item);
        return item;
    }

    @Override
    public void delete(Long id) {
        items.remove(id);
        log.debug("Удалена вещь с id: {}", id);
    }

    @Override
    public boolean existsById(Long id) {
        return items.containsKey(id);
    }
}