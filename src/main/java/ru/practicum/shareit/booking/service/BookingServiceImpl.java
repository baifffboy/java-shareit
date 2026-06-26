package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dao.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.CreateBookingRequest;
import ru.practicum.shareit.booking.dto.UpdateBookingRequest;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dao.ItemRepositoryInDatabase;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserRepositoryInDatabase;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final ItemRepositoryInDatabase itemRepository;
    private final UserRepositoryInDatabase userRepository;

    public BookingDto create(CreateBookingRequest createBookingRequest, Long userId) throws ValidationException {
        if (!userRepository.existsById(userId))
            throw new NotFoundException("Пользователь с данным id не существует");
        if (!itemRepository.existsById(createBookingRequest.getItemId()))
            throw new NotFoundException("Вещь с данным id не существует");
        else {
            Item item = itemRepository.findById(createBookingRequest.getItemId()).get();
            if (!item.isAvailable()) throw new ValidationException("Вещь забронирована кем-то другим");
        }
        if (createBookingRequest.getStart().isAfter(createBookingRequest.getEnd()))
            throw new ValidationException("Старт аренды должен быть раньше начала");
        if (createBookingRequest.getStart().equals(createBookingRequest.getEnd()))
            throw new ValidationException("Время начала и конца не могут совпадать");
        User user = userRepository.findById(userId).get();
        Item item = itemRepository.findById(createBookingRequest.getItemId()).get();
        Booking booking = bookingMapper.toBookingCreate(createBookingRequest, item, user);
        Booking savedBooking = bookingRepository.save(booking);
        log.info("Успешно создано бронирование с id={}", savedBooking.getId());
        return bookingMapper.toDto(savedBooking);
    }

    public BookingDto patch(Long bookingId, Boolean approved, Long userId) throws ValidationException {
        if (!userRepository.existsById(userId))
            throw new NotFoundException("Пользователь с данным id не существует");
        Booking existBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронирование с id " + bookingId + " не найдено"));
        if (!existBooking.getItem().getOwner().getId().equals(userId))
            throw new ValidationException("Пользователь не является владельцем вещи");
        UpdateBookingRequest updateBookingRequest = new UpdateBookingRequest();
        updateBookingRequest.setId(bookingId);
        if (approved) updateBookingRequest.setStatus(Status.APPROVED);
        else updateBookingRequest.setStatus(Status.REJECTED);
        Booking patchBooking = bookingMapper.toBookingUpdate(updateBookingRequest, existBooking);
        Booking savedBooking = bookingRepository.save(patchBooking);
        log.info("Успешно обновлен статус бронирования с id={}, теперь статус={}", savedBooking.getId(), savedBooking.getStatus());
        return bookingMapper.toDto(savedBooking);
    }

    public BookingDto getBookingById(Long bookingId) {
        Booking existBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронирование с id " + bookingId + " не найдено"));
        log.info("Запрос на получение бронирования с id={}", existBooking.getId());
        return bookingMapper.toDto(existBooking);
    }

    public List<BookingDto> getBookingByLeaseholder(Long userId) {
        log.info("Запрос на коллецию бронирований у арендатора");
        return bookingRepository.findByBooker_IdOrderByStartDesc(userId).stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BookingDto> getBookingByOwner(Long userId) {
        if (!userRepository.existsById(userId))
            throw new NotFoundException("Пользователь с данным id не существует");
        log.info("Запрос на коллецию бронирований у арендодателя (сколько вещей у него забронировали)");
        return bookingRepository.findByItem_Owner_IdOrderByStartDesc(userId).stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }
}
