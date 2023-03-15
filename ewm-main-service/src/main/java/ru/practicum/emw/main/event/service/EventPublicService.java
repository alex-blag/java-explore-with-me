package ru.practicum.emw.main.event.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.emw.main.event.dto.EventPublicParams;
import ru.practicum.emw.main.event.dto.EventPublicSort;
import ru.practicum.emw.main.event.entity.Event;

import java.util.List;

public interface EventPublicService {

    Event findById(long id);

    List<Event> findAllByParams(
            EventPublicParams eventPublicParams,
            boolean onlyAvailable,
            EventPublicSort eventPublicSort,
            Pageable pageable
    );

    void updateConfirmedRequestsAndViews(List<Event> events);

}
