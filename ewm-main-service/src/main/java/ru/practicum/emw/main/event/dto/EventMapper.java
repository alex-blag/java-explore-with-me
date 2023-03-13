package ru.practicum.emw.main.event.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.emw.main.event.entity.Event;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static ru.practicum.emw.main.category.dto.CategoryMapper.toCategoryDto;
import static ru.practicum.emw.main.event.dto.LocationMapper.toLocationDto;
import static ru.practicum.emw.main.user.dto.UserMapper.toUserShortDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {

    public static EventFullDto toEventFullDto(Event event) {
        EventFullDto dto = new EventFullDto();

        dto.setId(event.getId());

        dto.setAnnotation(event.getAnnotation());

        dto.setCategory(toCategoryDto(event.getCategory()));

        dto.setCreatedOn(event.getCreatedOn());

        dto.setDescription(event.getDescription());

        dto.setEventDate(event.getEventDate());

        dto.setInitiator(toUserShortDto(event.getInitiator()));

        dto.setLocation(toLocationDto(event.getLocation()));

        dto.setPaid(event.getPaid());

        dto.setParticipantLimit(event.getParticipantLimit());

        dto.setPublishedOn(event.getPublishedOn());

        dto.setRequestModeration(event.getRequestModeration());

        dto.setState(event.getState());

        dto.setTitle(event.getTitle());

        dto.setConfirmedRequests(event.getConfirmedRequests());

        dto.setViews(event.getViews());

        return dto;
    }

    public static List<EventFullDto> toEventFullDtos(List<Event> events) {
        return events
                .stream()
                .map(EventMapper::toEventFullDto)
                .collect(toList());
    }

    public static EventShortDto toEventShortDto(Event event) {
        EventShortDto dto = new EventShortDto();

        dto.setId(event.getId());

        dto.setAnnotation(event.getAnnotation());

        dto.setCategory(toCategoryDto(event.getCategory()));

        dto.setEventDate(event.getEventDate());

        dto.setInitiator(toUserShortDto(event.getInitiator()));

        dto.setPaid(event.getPaid());

        dto.setTitle(event.getTitle());

        dto.setConfirmedRequests(event.getConfirmedRequests());

        dto.setViews(event.getViews());

        return dto;
    }

    public static List<EventShortDto> toEventShortDtos(List<Event> events) {
        return events
                .stream()
                .map(EventMapper::toEventShortDto)
                .collect(toList());
    }

}
