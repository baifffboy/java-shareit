package ru.practicum.shareit.item.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

@Repository("inDatabase")
@Primary
public interface ItemRepositoryInDatabase extends JpaRepository<Item, Long>, ItemRepository {

}
