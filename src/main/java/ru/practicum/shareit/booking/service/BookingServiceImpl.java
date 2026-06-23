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
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.user.dao.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public BookingDto create(CreateBookingRequest createBookingRequest) throws ValidationException {
        if (createBookingRequest.getStart_date().isAfter(createBookingRequest.getEnd_date()))
            throw new ValidationException("Старт аренды должен быть раньше начала");
        if (!itemRepository.existsById(createBookingRequest.getItem().getId()))
            throw new NotFoundException("Вещь с данным id не существует");
        if (!userRepository.existsById(createBookingRequest.getItem().getId()))
            throw new NotFoundException("Пользователь с данным id не существует");
        Booking booking = bookingMapper.toBookingCreate(createBookingRequest);
        Booking savedBooking = bookingRepository.save(booking);
        log.info("Успешно создано бронирование с id={}", savedBooking.getId());
        return bookingMapper.toDto(savedBooking);
    }

    public BookingDto patch(Long bookingId, Boolean approved) {
        UpdateBookingRequest updateBookingRequest = new UpdateBookingRequest();
        updateBookingRequest.setId(bookingId);
        if (approved) updateBookingRequest.setStatus(Status.APPROVED);
        else updateBookingRequest.setStatus(Status.REJECTED);
        Booking existBooking = bookingRepository.getReferenceById(bookingId);
        Booking patchBooking = bookingMapper.toBookingUpdate(updateBookingRequest, existBooking);
        Booking savedBooking = bookingRepository.save(patchBooking);
        log.info("Успешно обновлен статус бронирования с id={}, теперь статус={}", savedBooking.getId(), savedBooking.getStatus());
        return bookingMapper.toDto(savedBooking);
    }

    public BookingDto getBookingById(Long bookingId) {
        Booking booking = bookingRepository.getReferenceById(bookingId);
        log.info("Запрос на получение бронирования с id={}", booking.getId());
        return bookingMapper.toDto(booking);
    }

    public List<BookingDto> getBookingByLeaseholder(Long userId) {
        log.info("Запрос на коллецию бронирований у арендатора");
        return bookingRepository.findByBookerIdOrderByStartDateDesc(userId).stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BookingDto> getBookingByOwner(Long userId) {
        log.info("Запрос на коллецию бронирований у арендодателя (сколько вещей у него забронировали)");
        return bookingRepository.findByItemOwnerIdOrderByStartDateDesc(userId).stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }
}
