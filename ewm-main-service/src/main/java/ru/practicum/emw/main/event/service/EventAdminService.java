package ru.practicum.emw.main.event.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.emw.main.event.dto.EventAdminParams;
import ru.practicum.emw.main.event.dto.UpdateEventAdminRequest;
import ru.practicum.emw.main.event.entity.Event;

import java.util.List;

public interface EventAdminService {

    Event updateById(long id, UpdateEventAdminRequest updateEventAdminRequest);

    List<Event> findAllByIds(List<Long> ids);

    List<Event> findAllByParams(EventAdminParams eventAdminParams, Pageable pageable);

    boolean existsByCategoryId(long categoryId);

    boolean existsByLocationId(long locationId);

}
