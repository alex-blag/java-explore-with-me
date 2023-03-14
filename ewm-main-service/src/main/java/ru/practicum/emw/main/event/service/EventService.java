package ru.practicum.emw.main.event.service;

import com.querydsl.core.types.Predicate;
import ru.practicum.emw.main.event.entity.Event;

import java.util.List;

public interface EventService {

    Event save(Event event);

    Event findById(long id);

    Event findOne(Predicate predicate);

    List<Event> findAll(Predicate predicate);

    boolean exists(Predicate predicate);

    void updateConfirmedRequestsAndViews(Event event);

    void updateConfirmedRequestsAndViews(List<Event> events);

}
