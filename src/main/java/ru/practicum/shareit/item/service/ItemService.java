package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CreateItemRequest;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.UpdateItemRequest;

import java.util.List;

public interface ItemService {
    ItemDto create(Long userId, CreateItemRequest createItemRequest);

    ItemDto update(Long userId, UpdateItemRequest updateItemRequest, Long itemId);

    ItemDto findById(Long id);

    List<ItemDto> findByUserId(Long userId);

    List<ItemDto> search(String text);
}