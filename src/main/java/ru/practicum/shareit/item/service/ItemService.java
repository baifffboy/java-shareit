package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.*;

import java.util.List;

public interface ItemService {
    ItemDto create(Long userId, CreateItemRequest createItemRequest);

    ItemDto createComment(Long userId, CreateCommentRequest createItemRequest);

    ItemDto update(Long userId, UpdateItemRequest updateItemRequest, Long itemId);

    ItemDto findById(Long id);

    List<OwnerItemDto> findByUserId(Long userId);

    List<ItemDto> search(String text);
}