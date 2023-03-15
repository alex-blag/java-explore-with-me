package ru.practicum.emw.main.event.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.emw.main.event.dto.NewEventDto;
import ru.practicum.emw.main.event.dto.UpdateEventUserRequest;
import ru.practicum.emw.main.event.entity.Event;

import java.util.List;

public interface EventPrivateService {

    Event saveByInitiatorId(long initiatorId, NewEventDto newEventDto);

    Event updateByIdAndInitiatorId(long id, long initiatorId, UpdateEventUserRequest update);

    void updateConfirmedRequestsAndViews(Event event);

    Event findById(long id);

    Event findByIdAndInitiatorId(long id, long initiatorId);

    List<Event> findAllByInitiatorId(long initiatorId, Pageable pageable);

    boolean existsByIdAndInitiatorId(long id, long initiatorId);

}
