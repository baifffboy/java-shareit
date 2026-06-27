package ru.practicum.shareit.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.shareit.item.dto.ItemShortDto;
import ru.practicum.shareit.user.dto.CreateUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = ItemShortDto.class)
public interface UserMapper {

    UserDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "demandItem", ignore = true)
    @Mapping(target = "supplyItem", ignore = true)
    User toEntity(CreateUserRequest createUserRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "demandItem", ignore = true)
    @Mapping(target = "supplyItem", ignore = true)
    void updateEntity(@MappingTarget User existingUser, UpdateUserRequest updateUserRequest);
}