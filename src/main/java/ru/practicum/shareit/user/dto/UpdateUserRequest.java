package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;

@Data
public class UpdateUserRequest {
    @Positive(message = "id не может быть отрицательным или равным 0")
    private Long id;
    @NotNull
    private String name;
    @Email
    @NotNull
    private String email;
    private Item demandItem;
    private Item supplyItem;
}
