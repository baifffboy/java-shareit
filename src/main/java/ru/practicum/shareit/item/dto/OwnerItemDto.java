package ru.practicum.shareit.item.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OwnerItemDto {
    private Long id;
    private String name;
    private String description;
    private boolean available;
    private List<CommentDto> comments;
    private Long countOfRent;
    private LocalDate lastRent;
    private LocalDate nextRent;
}
