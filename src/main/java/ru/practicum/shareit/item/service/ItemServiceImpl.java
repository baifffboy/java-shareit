package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.dto.CreateItemRequest;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public ItemDto create(Long userId, CreateItemRequest createItemRequest) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден"));
        Item savedItem = itemRepository.save(ItemMapper.mapToItem(createItemRequest, owner));
        log.info("Создана вещь с id: {} для пользователя с id: {}", savedItem.getId(), userId);
        return ItemMapper.mapToItemDto(savedItem);
    }

    @Override
    public ItemDto update(Long userId, UpdateItemRequest updateItemRequest, Long itemId) {
        Item existingItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь с id " + itemId + " не найдена"));
        if (!existingItem.getOwner().getId().equals(userId)) {
            throw new NotFoundException("Редактировать вещь может только её владелец");
        }
        Item updatedItem = itemRepository.update(ItemMapper.mapToItem(existingItem, updateItemRequest));
        log.info("Обновлена вещь с id: {}", updatedItem.getId());
        return ItemMapper.mapToItemDto(updatedItem);
    }

    @Override
    public ItemDto findById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Вещь с id " + id + " не найдена"));
        return ItemMapper.mapToItemDto(item);
    }

    @Override
    public List<ItemDto> findByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с id " + userId + " не найден");
        }
        return itemRepository.findByOwnerId(userId).stream()
                .map(ItemMapper::mapToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> search(String text) {
        if (text == null || text.isBlank()) {
            return List.of();
        }
        String lowerCaseText = text.toLowerCase();
        return itemRepository.findAll().stream()
                .filter(Item::isAvailable)
                .filter(item -> item.getName().toLowerCase().contains(lowerCaseText) ||
                        item.getDescription().toLowerCase().contains(lowerCaseText))
                .map(ItemMapper::mapToItemDto)
                .collect(Collectors.toList());
    }
}