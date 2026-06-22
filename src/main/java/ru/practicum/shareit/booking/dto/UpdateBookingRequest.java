package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Positive;
import lombok.*;
import ru.practicum.shareit.booking.model.Status;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UpdateBookingRequest {
    @Positive(message = "id не может быть отрицательным или равным 0")
    private Long id;
    private Status status;
}
