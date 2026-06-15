package ru.practicum.shareit.request.dto;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

public class RequestDto {
    private Long id;
    private Item request;
    private boolean isActual;
    private User requestUser;
}
