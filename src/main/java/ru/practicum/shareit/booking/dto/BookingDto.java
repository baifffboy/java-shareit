package ru.practicum.shareit.booking.dto;

import lombok.Data;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.item.dto.ItemShortDto;
import ru.practicum.shareit.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
public class BookingDto {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Status status;
    private ItemShortDto item;
    private UserShortDto booker;
}
