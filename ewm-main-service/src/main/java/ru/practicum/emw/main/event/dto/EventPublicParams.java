package ru.practicum.emw.main.event.dto;

import lombok.Value;
import ru.practicum.emw.main.location.dto.AreaOfInterest;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class EventPublicParams {

    String searchTextInAnnotationOrDescription;

    List<Long> categoryIds;

    Boolean paid;

    LocalDateTime rangeStart;

    LocalDateTime rangeEnd;

    Long locationId;

    AreaOfInterest areaOfInterest;

}
