package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;

@Data
public class UpdateUserRequest {
    private String name;
    @Email
    private String email;
    private Item demandItem;
    private Item supplyItem;
}
