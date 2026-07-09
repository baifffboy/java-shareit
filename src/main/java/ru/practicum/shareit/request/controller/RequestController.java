package ru.practicum.shareit.request.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.CreateRequest;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.service.RequestService;

import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/requests")
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<RequestDto> create(
            @RequestHeader("X-Sharer-User-Id") @Positive(message = "id пользователя должен быть положительным") Long userId,
            @Valid @RequestBody CreateRequest createRequest
    ) {
        log.info("Пользователь с id {} создал запрос на вещь", userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(requestService.create(userId, createRequest));
    }

    @GetMapping
    public ResponseEntity<Collection<RequestDto>> getUserRequests(
            @RequestHeader("X-Sharer-User-Id") @Positive(message = "id пользователя должен быть положительным") Long userId
    ) {
        log.info("Пользователь с id {} получает свои запросы", userId);
        return ResponseEntity
                .ok()
                .body(requestService.getUserRequests(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<RequestDto>> getOtherRequests(
            @RequestHeader("X-Sharer-User-Id") @Positive(message = "id пользователя должен быть положительным") Long userId
    ) {
        log.info("Пользователь с id {} получает запросы других пользователей", userId);
        return ResponseEntity
                .ok()
                .body(requestService.getOtherRequests(userId));
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<RequestDto> getRequest(
            @RequestHeader("X-Sharer-User-Id") @Positive(message = "id пользователя должен быть положительным") Long userId,
            @PathVariable @Positive(message = "id запроса должен быть положительным") Long requestId
    ) {
        log.info("Пользователь с id {} получает запрос с id {}", userId, requestId);
        return ResponseEntity
                .ok()
                .body(requestService.getRequestById(userId, requestId));
    }
}
