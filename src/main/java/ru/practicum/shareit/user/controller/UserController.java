package ru.practicum.shareit.user.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.CreateUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        log.info("Отправлен запрос на создание пользователя с name: {}", createUserRequest.getName());
        return userService.create(createUserRequest);
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable @Positive(message = "id не может быть отрицательным или равным 0") Long id) {
        log.info("Отправлен запрос на получение пользователя с id: {}", id);
        return userService.findById(id);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        log.info("Отправлен запрос на получение всех пользователей");
        return userService.findAll();
    }

    @PatchMapping("/{id}")
    public UserDto updateUser(@PathVariable @Positive(message = "id не может быть отрицательным или равным 0") Long id, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        log.info("Отправлен запрос на обновление пользователя с id: {}", id);
        return userService.update(updateUserRequest, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable @Positive(message = "id не может быть отрицательным или равным 0") Long id) {
        log.info("Отправлен запрос на удаление пользователя с id: {}", id);
        userService.delete(id);
    }
}