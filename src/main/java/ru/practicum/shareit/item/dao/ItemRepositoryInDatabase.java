package ru.practicum.shareit.item.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Repository("itemDatabaseRepository")
@Primary
public interface ItemRepositoryInDatabase extends JpaRepository<Item, Long> {
    List<Item> findByOwnerId(Long userId);
}
