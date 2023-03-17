package ru.practicum.emw.main.event.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.emw.main.category.entity.Category;
import ru.practicum.emw.main.category.service.CategoryAdminService;
import ru.practicum.emw.main.event.dto.EventAdminParams;
import ru.practicum.emw.main.event.dto.EventAdminStateAction;
import ru.practicum.emw.main.event.dto.UpdateEventAdminRequest;
import ru.practicum.emw.main.event.entity.Event;
import ru.practicum.emw.main.event.entity.QEvent;
import ru.practicum.emw.main.event.entity.State;
import ru.practicum.emw.main.event.service.EventAdminService;
import ru.practicum.emw.main.event.service.EventService;
import ru.practicum.emw.main.location.dto.LocationDto;
import ru.practicum.emw.main.location.entity.Location;
import ru.practicum.emw.main.location.service.LocationAdminService;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;
import static ru.practicum.emw.main.common.CheckUtils.checkEventDateAfterEarlyStartOrThrow;
import static ru.practicum.emw.main.common.CheckUtils.checkEventNotPublishedYetOrThrow;
import static ru.practicum.emw.main.common.CheckUtils.checkEventPendingOrThrow;
import static ru.practicum.emw.main.common.CommonUtils.ONE_HOUR_BEFORE_EARLY_START;
import static ru.practicum.emw.main.common.CommonUtils.toPage;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class EventAdminServiceImpl implements EventAdminService {

    private static final QEvent Q_EVENT = QEvent.event;

    private final EventService eventService;

    @Lazy
    private final CategoryAdminService categoryAdminService;

    @Lazy
    private final LocationAdminService locationAdminService;

    @Override
    @Transactional
    public Event updateById(long id, UpdateEventAdminRequest updateEventAdminRequest) {
        log.debug("updateById (id = {}, updateEventAdminRequest = {})", id, updateEventAdminRequest);

        Event event = eventService.findById(id);

        String annotation = updateEventAdminRequest.getAnnotation();
        if (hasText(annotation)) {
            event.setAnnotation(annotation);
        }

        Long categoryId = updateEventAdminRequest.getCategory();
        if (categoryId != null) {
            Category category = categoryAdminService.findById(categoryId);
            event.setCategory(category);
        }

        String description = updateEventAdminRequest.getDescription();
        if (hasText(description)) {
            event.setDescription(description);
        }

        LocalDateTime eventDate = updateEventAdminRequest.getEventDate();
        if (eventDate != null) {
            checkEventDateAfterEarlyStartOrThrow(id, eventDate, ONE_HOUR_BEFORE_EARLY_START);
            event.setEventDate(eventDate);
        }

        LocationDto locationDto = updateEventAdminRequest.getLocation();
        if (locationDto != null) {
            Location location = locationAdminService.findLocationByLatAndLonOrSaveNew(
                    locationDto.getLat(), locationDto.getLon()
            );

            Double lat = locationDto.getLat();
            if (lat != null) {
                location.setLat(lat);
            }

            Double lon = locationDto.getLon();
            if (lon != null) {
                location.setLon(lon);
            }

            event.setLocation(location);
        }

        Boolean paid = updateEventAdminRequest.getPaid();
        if (paid != null) {
            event.setPaid(paid);
        }

        Integer participantLimit = updateEventAdminRequest.getParticipantLimit();
        if (participantLimit != null) {
            event.setParticipantLimit(participantLimit);
        }

        Boolean requestModeration = updateEventAdminRequest.getRequestModeration();
        if (requestModeration != null) {
            event.setRequestModeration(requestModeration);
        }

        LocalDateTime publishedOn;
        EventAdminStateAction stateAction = updateEventAdminRequest.getStateAction();
        if (stateAction != null) {
            State state;
            switch (stateAction) {
                case PUBLISH_EVENT:
                    checkEventPendingOrThrow(id, event.getState());
                    publishedOn = LocalDateTime.now();
                    state = State.PUBLISHED;
                    break;

                case REJECT_EVENT:
                    checkEventNotPublishedYetOrThrow(id, event.getState());
                    publishedOn = null;
                    state = State.CANCELED;
                    break;

                default:
                    throw new UnsupportedOperationException(stateAction.name());
            }
            event.setPublishedOn(publishedOn);
            event.setState(state);
        }

        String title = updateEventAdminRequest.getTitle();
        if (hasText(title)) {
            event.setTitle(title);
        }

        eventService.updateConfirmedRequestsAndViews(event);

        return event;
    }

    @Override
    public List<Event> findAllByIds(List<Long> ids) {
        log.debug("findAllByIds (ids = {})", ids);

        if (isEmpty(ids)) {
            return List.of();
        }

        Predicate p = buildQEventPredicateByIds(ids);
        List<Event> events = eventService.findAll(p);

        eventService.updateConfirmedRequestsAndViews(events);

        return events;
    }

    @Override
    public List<Event> findAllByParams(EventAdminParams eventAdminParams, Pageable pageable) {
        log.debug("findAllByParams (eventAdminParams = {}, pageable = {})", eventAdminParams, pageable);

        Predicate p = buildQEventPredicateByParams(eventAdminParams);
        List<Event> events = eventService.findAll(p);

        eventService.updateConfirmedRequestsAndViews(events);

        return toPage(events, pageable).getContent();
    }

    @Override
    public boolean existsByCategoryId(long categoryId) {
        log.debug("existsByCategoryId (categoryId = {})", categoryId);

        Predicate p = buildQEventPredicateByCategoryId(categoryId);
        return eventService.exists(p);
    }

    @Override
    public boolean existsByLocationId(long locationId) {
        log.debug("existsByLocationId (locationId = {})", locationId);

        Predicate p = buildQEventPredicateByLocationId(locationId);
        return eventService.exists(p);
    }

    private Predicate buildQEventPredicateByCategoryId(long categoryId) {
        return new BooleanBuilder()
                .and(Q_EVENT.category.id.eq(categoryId));
    }

    private Predicate buildQEventPredicateByLocationId(long locationId) {
        return new BooleanBuilder()
                .and(Q_EVENT.location.id.eq(locationId));
    }

    private Predicate buildQEventPredicateByIds(List<Long> ids) {
        BooleanBuilder builder = new BooleanBuilder();

        if (!isEmpty(ids)) {
            builder.and(Q_EVENT.id.in(ids));
        }

        return builder;
    }

    private Predicate buildQEventPredicateByParams(EventAdminParams params) {
        BooleanBuilder builder = new BooleanBuilder();

        List<Long> initiatorIds = params.getInitiatorIds();
        if (initiatorIds != null) {
            builder.and(Q_EVENT.initiator.id.in(initiatorIds));
        }

        List<State> states = params.getStates();
        if (states != null) {
            builder.and(Q_EVENT.state.in(states));
        }

        List<Long> categoryIds = params.getCategoryIds();
        if (categoryIds != null) {
            builder.and(Q_EVENT.category.id.in(categoryIds));
        }

        LocalDateTime rangeStart = params.getRangeStart();
        if (rangeStart != null) {
            builder.and(Q_EVENT.eventDate.after(rangeStart));
        }

        LocalDateTime rangeEnd = params.getRangeEnd();
        if (rangeEnd != null) {
            builder.and(Q_EVENT.eventDate.before(rangeEnd));
        }

        return builder;
    }

}
