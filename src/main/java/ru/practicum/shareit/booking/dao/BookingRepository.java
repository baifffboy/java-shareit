package ru.practicum.shareit.booking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBookerIdOrderByStartDateDesc(Long userId);

    List<Booking> findByItemOwnerIdOrderByStartDateDesc(Long userId);

    // Последнее завершённое APPROVED бронирование
    Optional<Booking> findFirstByItemIdAndStatusAndEndDateBeforeOrderByEndDateDesc(
            Long itemId,
            Status status,
            LocalDateTime now
    );

    // Все бронирования вещи которые имели состояние APPROVED до нынешнего момента
    List<Booking> findAllByItemIdAndStatusAndEndDateBeforeOrderByEndDateDesc(
            Long itemId,
            Status status,
            LocalDateTime now
    );

    // Ближайшее будущее APPROVED бронирование
    Optional<Booking> findFirstByItemIdAndStatusAndStartDateAfterOrderByStartDateAsc(
            Long itemId,
            Status status,
            LocalDateTime now
    );
}
