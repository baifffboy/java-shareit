package ru.practicum.shareit.request.model;

import jakarta.persistence.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

@Entity
@Table(name = "request_items")
public class ItemRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "item_id"
    )
    private Item request;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private User requestUser;
}
