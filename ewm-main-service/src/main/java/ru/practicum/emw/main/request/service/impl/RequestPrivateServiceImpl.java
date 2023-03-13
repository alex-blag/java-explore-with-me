package ru.practicum.emw.main.request.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.emw.main.event.dto.EventRequestStatusUpdateRequest;
import ru.practicum.emw.main.event.dto.EventRequestStatusUpdateResult;
import ru.practicum.emw.main.event.entity.Event;
import ru.practicum.emw.main.event.service.EventPrivateService;
import ru.practicum.emw.main.request.dto.RequestStatus;
import ru.practicum.emw.main.request.dto.RequestStatusUpdate;
import ru.practicum.emw.main.request.entity.QRequest;
import ru.practicum.emw.main.request.entity.Request;
import ru.practicum.emw.main.request.service.RequestPrivateService;
import ru.practicum.emw.main.request.service.RequestService;
import ru.practicum.emw.main.user.entity.User;
import ru.practicum.emw.main.user.service.UserPrivateService;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.emw.main.common.CheckUtils.checkEventAlreadyPublishedOrThrow;
import static ru.practicum.emw.main.common.CheckUtils.checkEventExistsOrThrow;
import static ru.practicum.emw.main.common.CheckUtils.checkEventParticipantLimitNotReachedOrThrow;
import static ru.practicum.emw.main.common.CheckUtils.checkRequestNotCreatedYetOrThrow;
import static ru.practicum.emw.main.common.CheckUtils.checkRequestStatusPendingOrThrow;
import static ru.practicum.emw.main.common.CheckUtils.checkRequesterNotOwnEventOrThrow;
import static ru.practicum.emw.main.common.CheckUtils.checkUserExistsOrThrow;
import static ru.practicum.emw.main.common.CheckUtils.isEventParticipantLimitReached;
import static ru.practicum.emw.main.request.dto.RequestMapper.toParticipationRequestDtos;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RequestPrivateServiceImpl implements RequestPrivateService {

    private static final QRequest Q_REQUEST = QRequest.request;

    private final RequestService requestService;

    private final EventPrivateService eventPrivateService;

    private final UserPrivateService userPrivateService;

    @Override
    @Transactional
    public Request saveByEventIdAndRequesterId(long eventId, long requesterId) {
        log.debug("saveByEventIdAndRequesterId (eventId = {}, requesterId = {})", eventId, requesterId);

        Event event = eventPrivateService.findById(eventId);
        User requester = userPrivateService.findById(requesterId);

        checkRequestNotCreatedYetOrThrow(
                eventId,
                requesterId,
                requestService.exists(buildQRequestPredicateByEventIdAndRequesterId(eventId, requesterId))
        );
        checkRequesterNotOwnEventOrThrow(eventId, event.getInitiator().getId(), requesterId);
        checkEventAlreadyPublishedOrThrow(eventId, event.getState());

        eventPrivateService.updateConfirmedRequestsAndViews(event);

        checkEventParticipantLimitNotReachedOrThrow(eventId, event.getConfirmedRequests(), event.getParticipantLimit());

        Request request = new Request();

        request.setCreated(LocalDateTime.now());
        request.setEvent(event);
        request.setRequester(requester);

        RequestStatus requestStatus = event.getRequestModeration()
                ? RequestStatus.PENDING
                : RequestStatus.CONFIRMED;

        request.setStatus(requestStatus);

        return requestService.save(request);
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateRequestStatusByEventIdAndInitiatorId(
            long eventId, long initiatorId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest
    ) {
        log.debug(
                "updateRequestStatusByEventIdAndInitiatorId (eventId = {}, initiatorId = {}, eventRequestStatusUpdateRequest = {})",
                eventId, initiatorId, eventRequestStatusUpdateRequest
        );

        checkUserExistsOrThrow(initiatorId, userPrivateService.existsById(initiatorId));

        Event event = eventPrivateService.findByIdAndInitiatorId(eventId, initiatorId);

        long confirmedRequests = event.getConfirmedRequests();
        int participantLimit = event.getParticipantLimit();

        checkEventParticipantLimitNotReachedOrThrow(eventId, confirmedRequests, participantLimit);

        List<Long> ids = eventRequestStatusUpdateRequest.getRequestIds();
        List<Request> requests = requestService.findAll(buildQRequestPredicateByIds(ids));
        for (Request request : requests) {
            checkRequestStatusPendingOrThrow(request.getId(), request.getStatus());

            RequestStatus status;
            RequestStatusUpdate statusUpdate = eventRequestStatusUpdateRequest.getStatus();
            switch (statusUpdate) {
                case CONFIRMED:
                    if (isEventParticipantLimitReached(confirmedRequests, participantLimit)) {
                        status = RequestStatus.CANCELED;
                    } else {
                        status = RequestStatus.CONFIRMED;
                        confirmedRequests++;
                    }
                    break;

                case REJECTED:
                    status = RequestStatus.REJECTED;
                    break;

                default:
                    throw new UnsupportedOperationException(statusUpdate.name());
            }
            request.setStatus(status);
        }

        Predicate confirmedPredicate = buildQRequestPredicateByIdsAndStatus(ids, RequestStatus.CONFIRMED);
        List<Request> confirmed = requestService.findAll(confirmedPredicate);

        Predicate rejectedPredicate = buildQRequestPredicateByIdsAndStatus(ids, RequestStatus.REJECTED);
        List<Request> rejected = requestService.findAll(rejectedPredicate);

        return new EventRequestStatusUpdateResult(
                toParticipationRequestDtos(confirmed),
                toParticipationRequestDtos(rejected)
        );
    }

    @Override
    @Transactional
    public Request cancelByIdAndRequesterId(long id, long requesterId) {
        log.debug("cancelByIdAndRequesterId (id = {}, requesterId = {})", id, requesterId);

        checkUserExistsOrThrow(requesterId, userPrivateService.existsById(requesterId));

        Predicate p = buildQRequestPredicateByIdAndRequesterId(id, requesterId);
        Request request = requestService.findOne(p);
        request.setStatus(RequestStatus.CANCELED);

        return request;
    }

    @Override
    public List<Request> findAllByRequesterId(long requesterId) {
        log.debug("findAllByRequesterId (requesterId = {})", requesterId);

        checkUserExistsOrThrow(requesterId, userPrivateService.existsById(requesterId));

        Predicate p = buildQRequestPredicateByRequesterId(requesterId);
        return requestService.findAll(p);
    }

    @Override
    public List<Request> findAllByEventIdAndInitiatorId(long eventId, long initiatorId) {
        log.debug("findAllByEventIdAndInitiatorId (eventId = {}, initiatorId = {})", eventId, initiatorId);

        checkUserExistsOrThrow(initiatorId, userPrivateService.existsById(initiatorId));
        checkEventExistsOrThrow(eventId, eventPrivateService.existsByIdAndInitiatorId(eventId, initiatorId));

        Predicate p = buildQRequestPredicateByEventId(eventId);
        return requestService.findAll(p);
    }

    private Predicate buildQRequestPredicateByIdAndRequesterId(long id, long requesterId) {
        return buildQRequestPredicateByIdAndEventIdAndRequesterId(id, null, requesterId);
    }

    private Predicate buildQRequestPredicateByEventId(long eventId) {
        return buildQRequestPredicateByIdAndEventIdAndRequesterId(null, eventId, null);
    }

    private Predicate buildQRequestPredicateByRequesterId(long requesterId) {
        return buildQRequestPredicateByIdAndEventIdAndRequesterId(null, null, requesterId);
    }

    private Predicate buildQRequestPredicateByEventIdAndRequesterId(long eventId, long requesterId) {
        return buildQRequestPredicateByIdAndEventIdAndRequesterId(null, eventId, requesterId);
    }

    private Predicate buildQRequestPredicateByIdAndEventIdAndRequesterId(Long id, Long eventId, Long requesterId) {
        BooleanBuilder builder = new BooleanBuilder();

        if (id != null) {
            builder.and(Q_REQUEST.id.eq(id));
        }

        if (eventId != null) {
            builder.and(Q_REQUEST.event.id.eq(eventId));
        }

        if (requesterId != null) {
            builder.and(Q_REQUEST.requester.id.eq(requesterId));
        }

        return builder;
    }

    private Predicate buildQRequestPredicateByIds(List<Long> ids) {
        return buildQRequestPredicateByIdsAndStatus(ids, null);
    }

    private Predicate buildQRequestPredicateByIdsAndStatus(List<Long> ids, RequestStatus status) {
        BooleanBuilder builder = new BooleanBuilder();

        if (ids != null) {
            builder.and(Q_REQUEST.id.in(ids));
        }

        if (status != null) {
            builder.and(Q_REQUEST.status.eq(status));
        }

        return builder;
    }

}
