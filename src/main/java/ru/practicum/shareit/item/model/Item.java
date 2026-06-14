package ru.practicum.shareit.item.model;

import lombok.Data;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

@Data
public class Item {
    private Long id;
    private String name;
    private String description;
    private boolean isAvailable;
    private List<String> reviews = new ArrayList<>();
    private User owner;
    private Long countOfRent;
    // класс вещи - id, навзание вещи, описание, доступна?,
    // отзыв - можно оставить помле того кк вещь отдали обратно
    // хозяин вещи, количество аренд
}
