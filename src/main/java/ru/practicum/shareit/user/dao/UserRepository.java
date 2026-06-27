package ru.practicum.shareit.user.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

@Primary
@Repository("userDatabaseRepository")
public interface UserRepository extends JpaRepository<User, Long> {

}
