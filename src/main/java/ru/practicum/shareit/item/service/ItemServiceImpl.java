package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dao.BookingRepository;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dao.CommentRepository;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemMapper itemMapper;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    public ItemDto create(Long userId, CreateItemRequest createItemRequest) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден"));
        Item item = itemMapper.toEntity(createItemRequest, owner);
        Item savedItem = itemRepository.save(item);
        log.info("Создана вещь с id: {} для пользователя с id: {}", savedItem.getId(), userId);
        return itemMapper.toDto(savedItem);
    }

    @Override
    public ItemDto createComment(Long userId, Long itemId, CreateCommentRequest createCommentRequest) throws ValidationException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден"));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь с id " + itemId + " не найдена"));
        bookingRepository.findAllByItemIdAndStatusAndEndDateBeforeOrderByEndDateDesc(
                        itemId,
                        Status.APPROVED,
                        LocalDateTime.now()
                ).stream()
                .filter(b -> b.getBooker().getId().equals(userId))
                .findAny()
                .orElseThrow(() -> new ValidationException(
                        "Пользователь не арендовал эту вещь или бронирование не завершено"
                ));

        Comment savedComment = commentRepository.save(itemMapper.toEntity(createCommentRequest, item));
        item.getComments().add(savedComment.getComment());
        return itemMapper.toDto(item);
    }

    @Override
    public ItemDto update(Long userId, UpdateItemRequest updateItemRequest, Long itemId) {
        Item existingItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь с id " + itemId + " не найдена"));
        if (!existingItem.getOwner().getId().equals(userId)) {
            throw new NotFoundException("Редактировать вещь может только её владелец");
        }
        itemMapper.updateEntity(existingItem, updateItemRequest);
        Item updatedItem = itemRepository.update(existingItem);
        log.info("Обновлена вещь с id: {}", updatedItem.getId());
        return itemMapper.toDto(updatedItem);
    }

    @Override
    public ItemDto findById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Вещь с id " + id + " не найдена"));
        return itemMapper.toDto(item);
    }

    @Override
    public List<OwnerItemDto> findByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с id " + userId + " не найден");
        }
        return itemRepository.findByOwnerId(userId).stream()
                .map(item -> {
                            bookingRepository.findFirstByItemIdAndStatusAndEndDateBeforeOrderByEndDateDesc(
                                    item.getId(),
                                    Status.APPROVED,
                                    LocalDateTime.now()
                            ).ifPresent(lastBooking -> item.setLastRent(lastBooking.getEnd_date().toLocalDate()));

                            bookingRepository.findFirstByItemIdAndStatusAndStartDateAfterOrderByStartDateAsc(
                                    item.getId(),
                                    Status.APPROVED,
                                    LocalDateTime.now()
                            ).ifPresent(nextBooking -> item.setNextRent(nextBooking.getStart_date().toLocalDate()));

                            return itemMapper.toDtoOwner(item);
                        }
                )
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
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }
}