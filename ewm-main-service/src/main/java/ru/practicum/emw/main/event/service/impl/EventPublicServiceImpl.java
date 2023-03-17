package ru.practicum.emw.main.event.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.emw.main.event.dto.EventPublicParams;
import ru.practicum.emw.main.event.dto.EventPublicSort;
import ru.practicum.emw.main.event.entity.Event;
import ru.practicum.emw.main.event.entity.QEvent;
import ru.practicum.emw.main.event.entity.State;
import ru.practicum.emw.main.event.service.EventPublicService;
import ru.practicum.emw.main.event.service.EventService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.hasText;
import static ru.practicum.emw.main.common.CommonUtils.toPage;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class EventPublicServiceImpl implements EventPublicService {

    private static final QEvent Q_EVENT = QEvent.event;

    private final EventService eventService;

    @Override
    public Event findById(long id) {
        log.debug("findById(id = {})", id);

        Predicate p = buildQEventPredicatePublishedById(id);
        Event event = eventService.findOne(p);
        eventService.updateConfirmedRequestsAndViews(event);

        return event;
    }

    @Override
    public List<Event> findAllByParams(
            EventPublicParams eventPublicParams,
            boolean onlyAvailable,
            EventPublicSort eventPublicSort,
            Pageable pageable
    ) {
        log.debug(
                "findAllByParams(eventPublicParams = {}, onlyAvailable = {}, eventPublicSort = {}, pageable = {})",
                eventPublicParams, onlyAvailable, eventPublicSort, pageable
        );

        Predicate p = buildQEventPredicatePublishedByParams(eventPublicParams);
        List<Event> events = eventService.findAll(p);
        eventService.updateConfirmedRequestsAndViews(events);

        List<Event> sortedEvents = toSort(events, onlyAvailable, eventPublicSort);
        Page<Event> pagedEvents = toPage(sortedEvents, pageable);
        return pagedEvents.getContent();
    }

    @Override
    public void updateConfirmedRequestsAndViews(List<Event> events) {
        log.debug("updateConfirmedRequestsAndViews(events = {})", events);

        eventService.updateConfirmedRequestsAndViews(events);
    }

    private List<Event> toSort(List<Event> events, boolean onlyAvailable, EventPublicSort sort) {
        return toSort(events.stream(), onlyAvailable, sort).collect(toList());
    }

    private Stream<Event> toSort(Stream<Event> events, boolean onlyAvailable, EventPublicSort sort) {
        Stream<Event> filteredEvents = events
                .filter(event -> isEventAvailable(
                        onlyAvailable, event.getParticipantLimit(), event.getConfirmedRequests()
                ));

        if (sort == null) {
            return filteredEvents;
        }

        Stream<Event> sortedEvents;
        switch (sort) {
            case EVENT_DATE:
                sortedEvents = filteredEvents.sorted(comparing(Event::getEventDate));
                break;

            case VIEWS:
                sortedEvents = filteredEvents.sorted(comparing(Event::getViews));
                break;

            default:
                throw new UnsupportedOperationException();
        }

        return sortedEvents;
    }

    private boolean isEventAvailable(boolean onlyAvailable, int participantLimit, long confirmedRequests) {
        return !onlyAvailable || participantLimit == 0 || confirmedRequests < participantLimit;
    }

    private Predicate buildQEventPredicatePublishedById(long id) {
        return new BooleanBuilder()
                .and(Q_EVENT.state.eq(State.PUBLISHED))
                .and(Q_EVENT.id.eq(id));
    }

    private Predicate buildQEventPredicatePublishedByParams(EventPublicParams params) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(Q_EVENT.state.eq(State.PUBLISHED));

        String text = params.getSearchTextInAnnotationOrDescription();
        if (hasText(text)) {
            builder.and(Q_EVENT.annotation.containsIgnoreCase(text));
            builder.or(Q_EVENT.description.containsIgnoreCase(text));
        }

        List<Long> categoryIds = params.getCategoryIds();
        if (categoryIds != null) {
            builder.and(Q_EVENT.category.id.in(categoryIds));
        }

        Boolean paid = params.getPaid();
        if (paid != null) {
            builder.and(Q_EVENT.paid.eq(paid));
        }

        LocalDateTime rangeStart = params.getRangeStart();
        if (rangeStart != null) {
            builder.and(Q_EVENT.eventDate.after(rangeStart));
        }

        LocalDateTime rangeEnd = params.getRangeEnd();
        if (rangeEnd != null) {
            builder.and(Q_EVENT.eventDate.before(rangeEnd));
        }

        if (rangeStart == null && rangeEnd == null) {
            builder.and(Q_EVENT.eventDate.after(LocalDateTime.now()));
        }

        return builder;
    }

}
