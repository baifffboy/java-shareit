package ru.practicum.shareit.item.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ItemMapper {

    OwnerItemDto toDtoOwner(Item item);

    ItemDto toDto(Item item);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "createItemRequest.name", target = "name")
    @Mapping(source = "createItemRequest.description", target = "description")
    @Mapping(source = "createItemRequest.available", target = "available")
    @Mapping(target = "comments", ignore = true)
    @Mapping(source = "owner", target = "owner")
    @Mapping(target = "countOfRent", constant = "0L")
    @Mapping(target = "lastRent", ignore = true)
    @Mapping(target = "nextRent", ignore = true)
    Item toEntity(CreateItemRequest createItemRequest, User owner);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "countOfRent", ignore = true)
    @Mapping(target = "lastRent", ignore = true)
    @Mapping(target = "nextRent", ignore = true)
    void updateEntity(@MappingTarget Item existingItem, UpdateItemRequest updateItemRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "createCommentRequest.comment", target = "comment")
    @Mapping(source = "item", target = "item")
    Comment toEntity(CreateCommentRequest createCommentRequest, Item item);
}