package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "available")
    private boolean available;

    @OneToMany
    @JoinTable(
            name = "comments",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(
            name = "owner_id"
    )
    private User owner;

    @Column(name = "count_of_rent")
    private Long countOfRent;

    @Column(name = "last_rent")
    private LocalDate lastRent;

    @Column(name = "next_rent")
    private LocalDate nextRent;
    // класс вещи - id, навзание вещи, описание, доступна?,
    // отзыв - можно оставить помле того кк вещь отдали обратно
    // хозяин вещи, количество аренд
}
