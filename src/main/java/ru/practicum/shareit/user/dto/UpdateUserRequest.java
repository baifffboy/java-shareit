package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;
import ru.practicum.shareit.item.dto.ItemShortDto;

@Data
public class UpdateUserRequest {
    private String name;
    @Email
    private String email;
    private ItemShortDto demandItem;
    private ItemShortDto supplyItem;
}
