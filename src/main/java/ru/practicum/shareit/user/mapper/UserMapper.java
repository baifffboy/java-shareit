package ru.practicum.shareit.user.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.dto.CreateUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setDemandItem(user.getDemandItem());
        userDto.setSupplyItem(user.getSupplyItem());
        return userDto;
    }

    public static User mapToUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setName(createUserRequest.getName());
        user.setEmail(createUserRequest.getEmail());
        if (createUserRequest.getDemandItem() != null)
            user.setDemandItem(new ArrayList<>(List.of(createUserRequest.getDemandItem())));
        if (createUserRequest.getSupplyItem() != null)
            user.setSupplyItem(new ArrayList<>(List.of(createUserRequest.getSupplyItem())));
        return user;
    }

    public static User mapToUser(User existingUser, UpdateUserRequest updateUserRequest) {
        if (updateUserRequest.getName() != null)
            existingUser.setName(updateUserRequest.getName());
        if (updateUserRequest.getEmail() != null)
            existingUser.setEmail(updateUserRequest.getEmail());
        if (updateUserRequest.getDemandItem() != null)
            existingUser.setDemandItem(new ArrayList<>(List.of(updateUserRequest.getDemandItem())));
        if (updateUserRequest.getSupplyItem() != null)
            existingUser.setSupplyItem(new ArrayList<>(List.of(updateUserRequest.getSupplyItem())));
        return existingUser;
    }
}