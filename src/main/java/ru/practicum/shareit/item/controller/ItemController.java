package ru.practicum.shareit.item.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CreateItemRequest;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/items")
@Slf4j
@RequiredArgsConstructor
@Validated
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto createItem(
            @RequestHeader("X-Sharer-User-Id") @Positive(message = "id не может быть отрицательным или равным 0") Long userId,
            @Valid @RequestBody CreateItemRequest createItemRequest) {
        log.info("Отправлен запрос на создание вещи пользователем с id: {}", userId);
        return itemService.create(userId, createItemRequest);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(
            @RequestHeader("X-Sharer-User-Id") @Positive(message = "id не может быть отрицательным или равным 0") Long userId,
            @PathVariable @Positive(message = "id не может быть отрицательным или равным 0") Long itemId,
            @RequestBody UpdateItemRequest updateItemRequest) {
        log.info("Отправлен запрос на обновление вещи с id: {} пользователем с id: {}", itemId, userId);
        return itemService.update(userId, updateItemRequest, itemId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(@PathVariable @Positive(message = "id не может быть отрицательным или равным 0") Long itemId) {
        log.info("Отправлен запрос на получение вещи с id: {}", itemId);
        return itemService.findById(itemId);
    }

    @GetMapping
    public List<ItemDto> getUserItems(@RequestHeader("X-Sharer-User-Id") @Positive(message = "id не может быть отрицательным или равным 0") Long userId) {
        log.info("Отправлен запрос на получение всех вещей пользователя с id: {}", userId);
        return itemService.findByUserId(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestParam String text) {
        log.info("Отправлен запрос на поиск вещей по тексту: {}", text);
        return itemService.search(text);
    }
}