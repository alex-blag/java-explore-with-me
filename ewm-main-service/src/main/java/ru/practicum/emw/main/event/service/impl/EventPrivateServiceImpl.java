package ru.practicum.emw.main.event.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.emw.main.category.entity.Category;
import ru.practicum.emw.main.category.service.CategoryPrivateService;
import ru.practicum.emw.main.event.dto.EventUserStateAction;
import ru.practicum.emw.main.event.dto.LocationDto;
import ru.practicum.emw.main.event.dto.NewEventDto;
import ru.practicum.emw.main.event.dto.UpdateEventUserRequest;
import ru.practicum.emw.main.event.entity.Event;
import ru.practicum.emw.main.event.entity.Location;
import ru.practicum.emw.main.event.entity.QEvent;
import ru.practicum.emw.main.event.entity.State;
import ru.practicum.emw.main.event.service.EventPrivateService;
import ru.practicum.emw.main.event.service.EventService;
import ru.practicum.emw.main.user.entity.User;
import ru.practicum.emw.main.user.service.UserPrivateService;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static ru.practicum.emw.main.common.CheckUtils.checkEventCanBeUpdatedOrThrow;
import static ru.practicum.emw.main.common.CheckUtils.checkEventDateAfterEarlyStartOrThrow;
import static ru.practicum.emw.main.common.CommonUtils.toPage;
import static ru.practicum.emw.main.event.dto.LocationMapper.toLocation;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class EventPrivateServiceImpl implements EventPrivateService {

    private static final QEvent Q_EVENT = QEvent.event;

    private static final int HOURS_BEFORE_EARLY_START = 2;

    private final EventService eventService;

    private final CategoryPrivateService categoryPrivateService;

    private final UserPrivateService userPrivateService;

    @Override
    @Transactional
    public Event saveByInitiatorId(long initiatorId, NewEventDto newEventDto) {
        log.debug("saveByInitiatorId (initiatorId = {}, newEventDto = {})", initiatorId, newEventDto);

        Event event = new Event();

        event.setAnnotation(newEventDto.getAnnotation());

        long categoryId = newEventDto.getCategory();
        Category category = categoryPrivateService.findById(categoryId);
        event.setCategory(category);

        event.setCreatedOn(LocalDateTime.now());

        event.setDescription(newEventDto.getDescription());

        LocalDateTime eventDate = newEventDto.getEventDate();
        checkEventDateAfterEarlyStartOrThrow(eventDate, HOURS_BEFORE_EARLY_START);
        event.setEventDate(eventDate);

        User initiator = userPrivateService.findById(initiatorId);
        event.setInitiator(initiator);

        Location location = toLocation(newEventDto.getLocation());
        event.setLocation(location);

        event.setPaid(newEventDto.getPaid());

        event.setParticipantLimit(newEventDto.getParticipantLimit());

        event.setRequestModeration(newEventDto.getRequestModeration());

        event.setState(State.PENDING);

        event.setTitle(newEventDto.getTitle());

        event.setConfirmedRequests(0L);

        event.setViews(0L);

        return eventService.save(event);
    }

    @Override
    @Transactional
    public Event updateByIdAndInitiatorId(long id, long initiatorId, UpdateEventUserRequest updateEventUserRequest) {
        log.debug(
                "updateByIdAndInitiatorId (id = {}, initiatorId = {}, updateEventUserRequest = {})",
                id, initiatorId, updateEventUserRequest
        );

        Predicate p = buildQEventPredicateByIdAndInitiatorId(id, initiatorId);
        Event event = eventService.findOne(p);
        checkEventCanBeUpdatedOrThrow(id, event.getState());

        String annotation = updateEventUserRequest.getAnnotation();
        if (hasText(annotation)) {
            event.setAnnotation(annotation);
        }

        Long categoryId = updateEventUserRequest.getCategory();
        if (categoryId != null) {
            Category category = categoryPrivateService.findById(categoryId);
            event.setCategory(category);
        }

        String description = updateEventUserRequest.getDescription();
        if (hasText(description)) {
            event.setDescription(description);
        }

        LocalDateTime eventDate = updateEventUserRequest.getEventDate();
        if (eventDate != null) {
            checkEventDateAfterEarlyStartOrThrow(eventDate, HOURS_BEFORE_EARLY_START);
            event.setEventDate(eventDate);
        }

        LocationDto locationDto = updateEventUserRequest.getLocation();
        if (locationDto != null) {
            Location location = toLocation(locationDto);
            event.setLocation(location);
        }

        Boolean paid = updateEventUserRequest.getPaid();
        if (paid != null) {
            event.setPaid(paid);
        }

        Integer participantLimit = updateEventUserRequest.getParticipantLimit();
        if (participantLimit != null) {
            event.setParticipantLimit(participantLimit);
        }

        Boolean requestModeration = updateEventUserRequest.getRequestModeration();
        if (requestModeration != null) {
            event.setRequestModeration(requestModeration);
        }

        EventUserStateAction stateAction = updateEventUserRequest.getStateAction();
        if (stateAction != null) {
            State state;
            switch (stateAction) {
                case SEND_TO_REVIEW:
                    state = State.PENDING;
                    break;

                case CANCEL_REVIEW:
                    state = State.CANCELED;
                    break;

                default:
                    throw new UnsupportedOperationException(stateAction.name());
            }
            event.setState(state);
        }

        String title = updateEventUserRequest.getTitle();
        if (hasText(title)) {
            event.setTitle(title);
        }

        eventService.updateConfirmedRequestsAndViews(event);

        return event;
    }

    @Override
    public void updateConfirmedRequestsAndViews(Event event) {
        log.debug("updateConfirmedRequestsAndViews (event = {})", event);

        eventService.updateConfirmedRequestsAndViews(event);
    }

    @Override
    public Event findById(long id) {
        log.debug("findById (id = {})", id);

        return eventService.findById(id);
    }

    @Override
    public Event findByIdAndInitiatorId(long id, long initiatorId) {
        log.debug("findByIdAndInitiatorId (id = {}, initiatorId = {})", id, initiatorId);

        Predicate p = buildQEventPredicateByIdAndInitiatorId(id, initiatorId);
        Event event = eventService.findOne(p);
        eventService.updateConfirmedRequestsAndViews(event);

        return event;
    }

    @Override
    public List<Event> findAllByInitiatorId(long initiatorId, Pageable pageable) {
        log.debug("findAllByInitiatorId (initiatorId = {}, pageable = {})", initiatorId, pageable);

        Predicate p = buildQEventPredicateByInitiatorId(initiatorId);
        List<Event> events = eventService.findAll(p);
        eventService.updateConfirmedRequestsAndViews(events);

        return toPage(events, pageable).getContent();
    }

    @Override
    public boolean existsByIdAndInitiatorId(long id, long initiatorId) {
        log.debug("existsByIdAndInitiatorId (id = {}, initiatorId = {})", id, initiatorId);

        Predicate p = buildQEventPredicateByIdAndInitiatorId(id, initiatorId);
        return eventService.exists(p);
    }

    private Predicate buildQEventPredicateByInitiatorId(long initiatorId) {
        return new BooleanBuilder()
                .and(Q_EVENT.initiator.id.eq(initiatorId));
    }

    private Predicate buildQEventPredicateByIdAndInitiatorId(long id, long initiatorId) {
        return new BooleanBuilder()
                .and(Q_EVENT.id.eq(id))
                .and(Q_EVENT.initiator.id.eq(initiatorId));
    }

}
