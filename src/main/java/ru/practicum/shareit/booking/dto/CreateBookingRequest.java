package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateBookingRequest {
    @NotNull
    private Long itemId;
    @Future(message = "Дата момента с которого вы хотите взять вещи должна начинаться в будущем")
    private LocalDateTime start;
    @Future(message = "Дата момента по который вы хотите взять вещи должна начинаться в будущем")
    private LocalDateTime end;
    // при create status всегда WAITING
}
