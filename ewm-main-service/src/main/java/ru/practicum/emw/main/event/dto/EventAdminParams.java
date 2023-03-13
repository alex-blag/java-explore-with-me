package ru.practicum.emw.main.event.dto;

import lombok.Value;
import ru.practicum.emw.main.event.entity.State;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class EventAdminParams {

    List<Long> initiatorIds;

    List<State> states;

    List<Long> categoryIds;

    LocalDateTime rangeStart;

    LocalDateTime rangeEnd;

}
