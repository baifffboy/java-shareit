package ru.practicum.shareit.user.dto;

import lombok.Data;
import ru.practicum.shareit.item.dto.ItemShortDto;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private List<ItemShortDto> demandItem;
    private List<ItemShortDto> supplyItem;
}
