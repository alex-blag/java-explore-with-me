package ru.practicum.emw.main.event.service.impl;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.emw.main.event.entity.Event;
import ru.practicum.emw.main.event.repository.EventRepository;
import ru.practicum.emw.main.event.service.EventService;
import ru.practicum.emw.main.request.service.RequestService;
import ru.practicum.emw.main.stats.service.StatsService;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static ru.practicum.emw.main.common.CommonUtils.getEarliestEventDate;
import static ru.practicum.emw.main.common.CommonUtils.toList;
import static ru.practicum.emw.main.exception.ExceptionUtils.getEventNotFoundException;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final RequestService requestService;

    @Lazy
    private final StatsService statsService;

    @Override
    public Event save(Event event) {
        log.debug("save (event = {})", event);

        return eventRepository.save(event);
    }

    @Override
    public Event findById(long id) {
        log.debug("findById (id = {})", id);

        return eventRepository
                .findById(id)
                .orElseThrow(() -> getEventNotFoundException(id));
    }

    @Override
    public Event findOne(Predicate predicate) {
        log.debug("findOne (predicate = [{}])", predicate);

        return eventRepository
                .findOne(predicate)
                .orElseThrow(() -> getEventNotFoundException(predicate.toString()));
    }

    @Override
    public List<Event> findAll(Predicate predicate) {
        log.debug("findAll (predicate = [{}])", predicate);

        return toList(eventRepository.findAll(predicate));
    }

    @Override
    public boolean exists(Predicate predicate) {
        log.debug("exists (predicate = [{}])", predicate);

        return eventRepository.exists(predicate);
    }

    @Override
    public void updateConfirmedRequestsAndViews(Event event) {
        log.debug("updateConfirmedRequestsAndViews (event = {})", event);

        this.updateConfirmedRequestsAndViews(List.of(event));
    }

    @Override
    public void updateConfirmedRequestsAndViews(List<Event> events) {
        log.debug("updateConfirmedRequestsAndViews (events = {})", events);

        List<Long> ids = events
                .stream()
                .map(Event::getId)
                .collect(toList());

        Map<Long, Long> idsToConfirmedRequests = requestService.mapEventIdsToConfirmedRequests(ids);
        Map<Long, Long> idsToViews = statsService.mapEventIdsToViews(ids, getEarliestEventDate(events));

        events.forEach(event -> {
            long id = event.getId();
            event.setConfirmedRequests(idsToConfirmedRequests.getOrDefault(id, 0L));
            event.setViews(idsToViews.getOrDefault(id, 0L));
        });
    }

}
