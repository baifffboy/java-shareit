package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.Positive;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

public class UpdateRequest {
    @Positive(message = "id не может быть отрицательным или равным 0")
    private Long id;
    private Item request;
    private boolean status;
    private User requestUser;
}
