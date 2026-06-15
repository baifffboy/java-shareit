package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class UpdateBookingRequest {
    @Positive(message = "id не может быть отрицательным или равным 0")
    private Long id;
    @Future(message = "Дата момента с которого вы хотите взять вещи должна начинаться в будущем")
    private LocalDateTime from;
    @Future(message = "Дата момента по который вы хотите взять вещи должна начинаться в будущем")
    private LocalDateTime to;
    // передавать isApproval - в RequestParam(required = false) с последующей проверкой на его существование
}
