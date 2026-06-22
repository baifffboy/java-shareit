package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.CreateBookingRequest;
import ru.practicum.shareit.exception.ValidationException;

import java.util.List;

public interface BookingService {
    public BookingDto create(CreateBookingRequest createBookingRequest) throws ValidationException;

    public BookingDto patch(Long bookingId, Boolean approved);

    public BookingDto getBookingById(Long bookingId);

    public List<BookingDto> getBookingByLeaseholder(Long userId);

    public List<BookingDto> getBookingByOwner(Long userId);
}
