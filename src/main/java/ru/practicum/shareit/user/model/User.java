package ru.practicum.shareit.user.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany
    @JoinTable(
            name = "demand_items",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> demandItem = new ArrayList<>(); // вещи которые пользователь имеет во временном пользовании

    @OneToMany
    @JoinTable(
            name = "supply_items",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> supplyItem = new ArrayList<>(); // вещи которые пользователь может предложить
    // id, никнейм юзера, какие вещи он имеет в распоряжении (взял в аренду),
    // и какие может предложить
}
