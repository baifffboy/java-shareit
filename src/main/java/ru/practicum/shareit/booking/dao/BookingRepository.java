package ru.practicum.shareit.booking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBooker_IdOrderByStartDesc(Long userId);

    List<Booking> findByItem_Owner_IdOrderByStartDesc(Long userId);

    // Последнее завершённое APPROVED бронирование
    Optional<Booking> findFirstByItem_IdAndStatusAndEndBeforeOrderByEndDesc(
            Long itemId,
            Status status,
            LocalDateTime now
    );

    // Все бронирования вещи которые имели состояние APPROVED до нынешнего момента
    List<Booking> findAllByItem_IdAndStatusAndEndBeforeOrderByEndDesc(
            Long itemId,
            Status status,
            LocalDateTime now
    );

    // Ближайшее будущее APPROVED бронирование
    Optional<Booking> findFirstByItem_IdAndStatusAndStartAfterOrderByStartAsc(
            Long itemId,
            Status status,
            LocalDateTime now
    );
}
