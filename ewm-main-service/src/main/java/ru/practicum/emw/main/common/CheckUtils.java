package ru.practicum.emw.main.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.emw.main.event.entity.State;
import ru.practicum.emw.main.exception.ExceptionUtils;
import ru.practicum.emw.main.request.dto.RequestStatus;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckUtils {

    public static final int NOT_EXISTING_DEFAULT_EVENT_ID = -1;

    public static void checkEventDateAfterEarlyStartOrThrow(LocalDateTime eventDate, long hoursBeforeEarlyStart) {
        checkEventDateAfterEarlyStartOrThrow(NOT_EXISTING_DEFAULT_EVENT_ID, eventDate, hoursBeforeEarlyStart);
    }

    public static void checkEventDateAfterEarlyStartOrThrow(
            long eventId,
            LocalDateTime eventDate,
            long hoursBeforeEarlyStart
    ) {
        LocalDateTime earlyStart = LocalDateTime.now().plusHours(hoursBeforeEarlyStart);
        if (eventDate.isBefore(earlyStart)) {
            throw ExceptionUtils.getEventDateTooEarlyException(eventId, eventDate);
        }
    }

    public static void checkEventCanBeUpdatedOrThrow(long eventId, State state) {
        switch (state) {
            case PENDING:
            case CANCELED:
                break;

            default:
                throw ExceptionUtils.getEventCannotBeUpdatedException(eventId, state);
        }
    }

    public static void checkEventPendingOrThrow(long eventId, State state) {
        if (state != State.PENDING) {
            throw ExceptionUtils.getEventNotPendingException(eventId, state);
        }
    }

    public static void checkEventNotPublishedYetOrThrow(long eventId, State state) {
        if (state == State.PUBLISHED) {
            throw ExceptionUtils.getEventAlreadyPublishedException(eventId, state);
        }
    }

    public static void checkUserExistsOrThrow(long userId, boolean userExists) {
        if (!userExists) {
            throw ExceptionUtils.getUserNotFoundException(userId);
        }
    }

    public static void checkEventExistsOrThrow(long eventId, boolean eventExists) {
        if (!eventExists) {
            throw ExceptionUtils.getEventNotFoundException(eventId);
        }
    }

    public static void checkRequestStatusPendingOrThrow(long requestId, RequestStatus status) {
        if (status != RequestStatus.PENDING) {
            throw ExceptionUtils.getRequestNotPendingException(requestId, status);
        }
    }

    public static boolean isEventParticipantLimitReached(long confirmedRequests, int participantLimit) {
        return participantLimit > 0 && confirmedRequests >= participantLimit;
    }

    public static void checkEventParticipantLimitNotReachedOrThrow(
            long eventId, long confirmedRequests, int participantLimit
    ) {
        if (isEventParticipantLimitReached(confirmedRequests, participantLimit)) {
            throw ExceptionUtils.getEventParticipantLimitReachedException(eventId, participantLimit);
        }
    }

    public static void checkCategoryHasNoAssociatedEventsOrThrow(long categoryId, boolean eventExistsByCategory) {
        if (eventExistsByCategory) {
            throw ExceptionUtils.getCategoryHasAssociatedEventException(categoryId);
        }
    }

    public static void checkCategoryExistsOrThrow(long categoryId, boolean categoryExists) {
        if (!categoryExists) {
            throw ExceptionUtils.getCategoryNotFoundException(categoryId);
        }
    }

    public static void checkRequestNotCreatedYetOrThrow(long eventId, long requesterId, boolean requestExists) {
        if (requestExists) {
            throw ExceptionUtils.getRequestAlreadyCreatedException(eventId, requesterId);
        }
    }

    public static void checkRequesterNotOwnEventOrThrow(long eventId, long initiatorId, long requesterId) {
        if (initiatorId == requesterId) {
            throw ExceptionUtils.getRequesterOwnsEventException(requesterId, eventId);
        }
    }

    public static void checkEventAlreadyPublishedOrThrow(long eventId, State state) {
        if (state != State.PUBLISHED) {
            throw ExceptionUtils.getEventNotPublishedException(eventId, state);
        }
    }

    public static void checkCompilationExistsOrThrow(long compilationId, boolean compilationExists) {
        if (!compilationExists) {
            throw ExceptionUtils.getCompilationNotFoundException(compilationId);
        }
    }

    public static void checkLocationHasNoAssociatedEventsOrThrow(long locationId, boolean eventExists) {
        if (eventExists) {
            throw ExceptionUtils.getLocationHasAssociatedEventException(locationId);
        }
    }

    public static void checkLocationExistsOrThrow(long locationId, boolean locationExists) {
        if (!locationExists) {
            throw ExceptionUtils.getLocationNotFoundException(locationId);
        }
    }

}
