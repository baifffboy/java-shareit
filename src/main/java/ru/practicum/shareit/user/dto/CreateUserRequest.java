package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ru.practicum.shareit.item.dto.ItemShortDto;

@Data
public class CreateUserRequest {
    @NotBlank(message = "Поле name не может быть пустым")
    private String name;
    @Email
    @NotBlank
    private String email;
    private ItemShortDto demandItem;
    private ItemShortDto supplyItem;
}
