package ru.practicum.shareit.request.model;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

public class ItemRequest {
    private Long id;
    private Item request;
    private boolean isActual;
    private User requestUser;
}
