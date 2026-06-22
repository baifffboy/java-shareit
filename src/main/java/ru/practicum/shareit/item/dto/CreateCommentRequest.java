package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.practicum.shareit.item.model.Item;

public class CreateCommentRequest {

    @NotNull
    private Item item;

    @NotBlank
    private String comment;
}
