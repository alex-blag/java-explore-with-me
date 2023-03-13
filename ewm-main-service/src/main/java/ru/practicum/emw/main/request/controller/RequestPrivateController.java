package ru.practicum.emw.main.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.emw.main.request.dto.ParticipationRequestDto;
import ru.practicum.emw.main.request.entity.Request;
import ru.practicum.emw.main.request.service.RequestPrivateService;

import java.util.List;

import static ru.practicum.emw.main.request.dto.RequestMapper.toParticipationRequestDto;
import static ru.practicum.emw.main.request.dto.RequestMapper.toParticipationRequestDtos;

@RestController
@RequestMapping(path = "/users/{userId}/requests")
@RequiredArgsConstructor
@Slf4j
class RequestPrivateController {

    private final RequestPrivateService requestPrivateService;

    @GetMapping
    List<ParticipationRequestDto> getAll(
            @PathVariable long userId
    ) {
        log.debug("getAll (userId = {})", userId);

        List<Request> requests = requestPrivateService.findAllByRequesterId(userId);
        return toParticipationRequestDtos(requests);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ParticipationRequestDto post(
            @PathVariable long userId,
            @RequestParam long eventId
    ) {
        log.debug("post (userId = {}, eventId = {})", userId, eventId);

        Request request = requestPrivateService.saveByEventIdAndRequesterId(eventId, userId);
        return toParticipationRequestDto(request);
    }

    @PatchMapping("/{requestId}/cancel")
    ParticipationRequestDto patch(
            @PathVariable long userId,
            @PathVariable long requestId
    ) {
        log.debug("patch (userId = {}, requestId = {})", userId, requestId);

        Request request = requestPrivateService.cancelByIdAndRequesterId(requestId, userId);
        return toParticipationRequestDto(request);
    }

}
