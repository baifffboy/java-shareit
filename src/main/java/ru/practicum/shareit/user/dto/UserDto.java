package ru.practicum.shareit.user.dto;

import lombok.Data;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private List<Item> demandItem;
    private List<Item> supplyItem;
}
