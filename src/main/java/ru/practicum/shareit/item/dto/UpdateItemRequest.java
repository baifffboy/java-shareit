package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateItemRequest {
    @Positive(message = "id не может быть отрицательным или равным 0")
    private Long id;
    private String name;
    private String description;
    private Boolean isAvailable;
    private String review;
}