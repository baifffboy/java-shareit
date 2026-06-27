package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.dto.CreateUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepositoryInDatabase;
    private final UserMapper userMapper;

    @Override
    public UserDto create(CreateUserRequest createUserRequest) {
        boolean emailExists = userRepositoryInDatabase.findAll().stream()
                .anyMatch(user -> user.getEmail().equals(createUserRequest.getEmail()));
        if (emailExists)
            throw new ConflictException("Пользователь с почтой " + createUserRequest.getEmail() + " уже существует");
        User user = userMapper.toEntity(createUserRequest);
        User savedUser = userRepositoryInDatabase.save(user);
        log.info("Создан пользователь с id: {}", savedUser.getId());
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepositoryInDatabase.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден"));
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepositoryInDatabase.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto update(UpdateUserRequest updateUserRequest, Long id) {
        User existingUser = userRepositoryInDatabase.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден"));
        boolean emailExists = userRepositoryInDatabase.findAll().stream()
                .anyMatch(user -> !user.getId().equals(id)
                        && user.getEmail().equals(updateUserRequest.getEmail()));
        if (emailExists)
            throw new ConflictException("Пользователь с почтой " + updateUserRequest.getEmail() + " уже существует");
        userMapper.updateEntity(existingUser, updateUserRequest);
        User updatedUser = userRepositoryInDatabase.save(existingUser);
        log.info("Обновлен пользователь с id: {}", updatedUser.getId());
        return userMapper.toDto(updatedUser);
    }

    @Override
    public void delete(Long id) {
        if (!userRepositoryInDatabase.existsById(id))
            throw new NotFoundException("Пользователь с id " + id + " не найден");
        User user = userRepositoryInDatabase.findById(id).orElseThrow(() -> new NotFoundException("Пользователоя не существует"));
        userRepositoryInDatabase.delete(user);
        log.info("Удален пользователь с id: {}", id);
    }
}