package ru.practicum.emw.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.emw.main.event.dto.EventFullDto;
import ru.practicum.emw.main.event.dto.EventRequestStatusUpdateRequest;
import ru.practicum.emw.main.event.dto.EventRequestStatusUpdateResult;
import ru.practicum.emw.main.event.dto.EventShortDto;
import ru.practicum.emw.main.event.dto.NewEventDto;
import ru.practicum.emw.main.event.dto.UpdateEventUserRequest;
import ru.practicum.emw.main.event.entity.Event;
import ru.practicum.emw.main.event.service.EventPrivateService;
import ru.practicum.emw.main.request.dto.ParticipationRequestDto;
import ru.practicum.emw.main.request.entity.Request;
import ru.practicum.emw.main.request.service.RequestPrivateService;

import javax.validation.Valid;
import java.util.List;

import static ru.practicum.emw.main.common.CommonUtils.DEFAULT_FROM;
import static ru.practicum.emw.main.common.CommonUtils.DEFAULT_SIZE;
import static ru.practicum.emw.main.common.CommonUtils.getPageRequestUnsorted;
import static ru.practicum.emw.main.event.dto.EventMapper.toEventFullDto;
import static ru.practicum.emw.main.event.dto.EventMapper.toEventShortDtos;
import static ru.practicum.emw.main.request.dto.RequestMapper.toParticipationRequestDtos;

@RestController
@RequestMapping(path = "/users/{userId}/events")
@RequiredArgsConstructor
@Slf4j
class EventPrivateController {

    private final EventPrivateService eventPrivateService;

    private final RequestPrivateService requestPrivateService;

    @GetMapping
    List<EventShortDto> getAll(
            @PathVariable long userId,
            @RequestParam(required = false, defaultValue = DEFAULT_FROM) int from,
            @RequestParam(required = false, defaultValue = DEFAULT_SIZE) int size
    ) {
        log.debug("getAll (userId = {}, from = {}, size = {})", userId, from, size);

        Pageable pageable = getPageRequestUnsorted(from, size);
        List<Event> events = eventPrivateService.findAllByInitiatorId(userId, pageable);
        return toEventShortDtos(events);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    EventFullDto post(
            @PathVariable long userId,
            @RequestBody @Valid NewEventDto newEventDto
    ) {
        log.debug("post (userId = {}, newEventDto = {})", userId, newEventDto);

        Event event = eventPrivateService.saveByInitiatorId(userId, newEventDto);
        return toEventFullDto(event);
    }

    @GetMapping("/{eventId}")
    EventFullDto get(
            @PathVariable long userId,
            @PathVariable long eventId
    ) {
        log.debug("get (userId = {}, eventId = {})", userId, eventId);

        Event event = eventPrivateService.findByIdAndInitiatorId(eventId, userId);
        return toEventFullDto(event);
    }

    @PatchMapping("/{eventId}")
    EventFullDto patch(
            @PathVariable long userId,
            @PathVariable long eventId,
            @RequestBody @Valid UpdateEventUserRequest updateEventUserRequest
    ) {
        log.debug(
                "patch (userId = {}, eventId = {}, updateEventUserRequest = {})",
                userId, eventId, updateEventUserRequest
        );

        Event event = eventPrivateService.updateByIdAndInitiatorId(eventId, userId, updateEventUserRequest);
        return toEventFullDto(event);
    }

    @GetMapping("/{eventId}/requests")
    List<ParticipationRequestDto> getAllParticipationRequests(
            @PathVariable long userId,
            @PathVariable long eventId
    ) {
        log.debug("getAllParticipationRequests (userId = {}, eventId = {})", userId, eventId);

        List<Request> requests = requestPrivateService.findAllByEventIdAndInitiatorId(eventId, userId);
        return toParticipationRequestDtos(requests);
    }

    @PatchMapping("/{eventId}/requests")
    EventRequestStatusUpdateResult patchRequestStatus(
            @PathVariable long userId,
            @PathVariable long eventId,
            @RequestBody @Valid EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest
    ) {
        log.debug(
                "patchRequestStatus (userId = {}, eventId = {}, eventRequestStatusUpdateRequest = {})",
                userId, eventId, eventRequestStatusUpdateRequest
        );

        return requestPrivateService.updateRequestStatusByEventIdAndInitiatorId(
                eventId, userId, eventRequestStatusUpdateRequest
        );
    }

}
