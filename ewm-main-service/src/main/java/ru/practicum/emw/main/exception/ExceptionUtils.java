package ru.practicum.emw.main.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.emw.main.common.CommonUtils;
import ru.practicum.emw.main.event.entity.State;
import ru.practicum.emw.main.exception.category.CategoryHasAssociatedEventException;
import ru.practicum.emw.main.exception.category.CategoryNotFoundException;
import ru.practicum.emw.main.exception.compilation.CompilationNotFoundException;
import ru.practicum.emw.main.exception.event.EventAlreadyPublishedException;
import ru.practicum.emw.main.exception.event.EventCannotBeUpdatedException;
import ru.practicum.emw.main.exception.event.EventDateTooEarlyException;
import ru.practicum.emw.main.exception.event.EventNotFoundException;
import ru.practicum.emw.main.exception.event.EventNotPendingException;
import ru.practicum.emw.main.exception.event.EventNotPublishedException;
import ru.practicum.emw.main.exception.event.EventParticipantLimitReachedException;
import ru.practicum.emw.main.exception.request.RequestAlreadyCreatedException;
import ru.practicum.emw.main.exception.request.RequestNotFoundException;
import ru.practicum.emw.main.exception.request.RequestNotPendingException;
import ru.practicum.emw.main.exception.request.RequesterOwnsEventException;
import ru.practicum.emw.main.exception.user.UserNotFoundException;
import ru.practicum.emw.main.request.dto.RequestStatus;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionUtils {

    public static UserNotFoundException getUserNotFoundException(long userId) {
        return new UserNotFoundException(
                String.format("%s [userId = %d]", ExceptionMessage.USER_NOT_FOUND, userId)
        );
    }

    public static CategoryNotFoundException getCategoryNotFoundException(long categoryId) {
        return new CategoryNotFoundException(
                String.format("%s [categoryId = %d]", ExceptionMessage.CATEGORY_NOT_FOUND, categoryId)
        );
    }

    public static CompilationNotFoundException getCompilationNotFoundException(long compilationId) {
        return new CompilationNotFoundException(
                String.format("%s [compilationId = %d]", ExceptionMessage.COMPILATION_NOT_FOUND, compilationId)
        );
    }

    public static CategoryHasAssociatedEventException getCategoryHasAssociatedEventException(long categoryId) {
        return new CategoryHasAssociatedEventException(
                String.format("%s [categoryId = %d]", ExceptionMessage.CATEGORY_HAS_ASSOCIATED_EVENT, categoryId)
        );
    }

    public static EventNotFoundException getEventNotFoundException(long eventId) {
        return new EventNotFoundException(
                String.format("%s [eventId = %d]", ExceptionMessage.EVENT_NOT_FOUND, eventId)
        );
    }

    public static EventNotFoundException getEventNotFoundException(String message) {
        return new EventNotFoundException(
                String.format("%s [%s]", ExceptionMessage.EVENT_NOT_FOUND, message)
        );
    }

    public static RequesterOwnsEventException getRequesterOwnsEventException(long requesterId, long eventId) {
        return new RequesterOwnsEventException(
                String.format("%s [requesterId = %d, eventId = %d]",
                        ExceptionMessage.REQUESTER_OWNS_EVENT,
                        requesterId,
                        eventId
                )
        );
    }

    public static EventNotPublishedException getEventNotPublishedException(long eventId, State state) {
        return new EventNotPublishedException(
                String.format(
                        "%s [eventId = %d, state = %s]", ExceptionMessage.EVENT_NOT_PUBLISHED, eventId, state.name()
                )
        );
    }

    public static EventParticipantLimitReachedException getEventParticipantLimitReachedException(
            long eventId,
            int participantLimit
    ) {
        return new EventParticipantLimitReachedException(
                String.format(
                        "%s [eventId = %d, participantLimit = %d]",
                        ExceptionMessage.EVENT_PARTICIPANT_LIMIT_REACHED,
                        eventId,
                        participantLimit
                )
        );
    }

    public static RequestNotFoundException getRequestNotFoundException(long eventId, long requesterId) {
        return new RequestNotFoundException(
                String.format(
                        "%s [eventId = %d, requesterId = %d]",
                        ExceptionMessage.REQUEST_NOT_FOUND,
                        eventId,
                        requesterId
                )
        );
    }

    public static RequestNotFoundException getRequestNotFoundException(String message) {
        return new RequestNotFoundException(
                String.format("%s [%s]", ExceptionMessage.REQUEST_NOT_FOUND, message)
        );
    }

    public static EventCannotBeUpdatedException getEventCannotBeUpdatedException(long eventId, State state) {
        return new EventCannotBeUpdatedException(
                String.format(
                        "%s [eventId = %d, state = %s]",
                        ExceptionMessage.EVENT_CANNOT_BE_UPDATED,
                        eventId,
                        state.name()
                )
        );
    }

    public static RequestNotPendingException getRequestNotPendingException(long requestId, RequestStatus requestStatus) {
        return new RequestNotPendingException(
                String.format(
                        "%s [requestId = %d, status = %s]",
                        ExceptionMessage.REQUEST_NOT_PENDING,
                        requestId,
                        requestStatus.name()
                )
        );
    }

    public static EventAlreadyPublishedException getEventAlreadyPublishedException(long eventId, State state) {
        return new EventAlreadyPublishedException(
                String.format(
                        "%s [eventId = %d, state = %s]",
                        ExceptionMessage.EVENT_ALREADY_PUBLISHED,
                        eventId,
                        state.name()
                )
        );
    }

    public static EventDateTooEarlyException getEventDateTooEarlyException(long eventId, LocalDateTime eventDate) {
        return new EventDateTooEarlyException(
                String.format(
                        "%s [eventId = %d, eventDate = %s]",
                        ExceptionMessage.EVENT_DATE_TOO_EARLY,
                        eventId,
                        CommonUtils.DATE_TIME_FORMATTER.format(eventDate)
                )
        );
    }

    public static EventNotPendingException getEventNotPendingException(long eventId, State state) {
        return new EventNotPendingException(
                String.format(
                        "%s [eventId = %d, state = %s]",
                        ExceptionMessage.EVENT_NOT_PENDING,
                        eventId,
                        state.name()
                )
        );
    }

    public static RequestAlreadyCreatedException getRequestAlreadyCreatedException(long eventId, long requesterId) {
        return new RequestAlreadyCreatedException(
                String.format(
                        "%s [eventId = %d, requesterId = %d]",
                        ExceptionMessage.REQUEST_ALREADY_CREATED,
                        eventId,
                        requesterId
                )
        );
    }

}
