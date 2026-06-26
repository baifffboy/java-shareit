package ru.practicum.shareit.booking.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.CreateBookingRequest;
import ru.practicum.shareit.booking.model.State;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.exception.ValidationException;

import java.util.List;

@RestController
@Slf4j
@Validated
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingServiceImpl bookingService;

    @PostMapping
    public ResponseEntity<BookingDto> create(
            @Valid @RequestBody CreateBookingRequest createBookingRequest,
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) throws ValidationException {
        log.info("Создан запрос на бронирование - по умолчанию статус WAITING");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookingService.create(createBookingRequest, userId));
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<BookingDto> patch(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable("bookingId") @Positive(message = "Id ник может быть отрицательным или равным 0") Long bookingId,
            @RequestParam("approved") @NotNull Boolean approved
    ) throws ValidationException {
        log.info("Значение status у id={} изменено на {}", bookingId, approved);
        return ResponseEntity
                .ok()
                .body(bookingService.patch(bookingId, approved, userId));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDto> getBookingById(
            @PathVariable("bookingId") @Positive(message = "Id ник может быть отрицательным или равным 0") Long bookingId
    ) {
        log.info("Возврат бронирования с id={}", bookingId);
        return ResponseEntity
                .ok()
                .body(bookingService.getBookingById(bookingId));
    }

    // ты арендуешь вещь
    @GetMapping
    public ResponseEntity<List<BookingDto>> getBookingByLeaseholder(
            @RequestParam(required = false, defaultValue = "ALL") State state,
            @RequestHeader("X-Sharer-User-Id") @Positive(message = "Id ник может быть отрицательным или равным 0") Long userId
    ) {
        log.info("Получение списка всех бронирований текущего пользователя c id={}", userId);
        return ResponseEntity
                .ok()
                .body(bookingService.getBookingByLeaseholder(userId));
    }

    // ты владелец вещи
    @GetMapping("/owner")
    public ResponseEntity<List<BookingDto>> getBookingByOwner(
            @RequestParam(required = false, defaultValue = "ALL") State state,
            @RequestHeader("X-Sharer-User-Id") @Positive(message = "Id ник может быть отрицательным или равным 0") Long userId
    ) {
        log.info("Получение списка всех бронирований у арендодателя(owner) c id={}", userId);
        return ResponseEntity
                .ok()
                .body(bookingService.getBookingByOwner(userId));
    }
}
