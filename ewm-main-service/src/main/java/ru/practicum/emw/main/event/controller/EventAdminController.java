package ru.practicum.emw.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.emw.main.event.dto.EventAdminParams;
import ru.practicum.emw.main.event.dto.EventFullDto;
import ru.practicum.emw.main.event.dto.UpdateEventAdminRequest;
import ru.practicum.emw.main.event.entity.Event;
import ru.practicum.emw.main.event.entity.State;
import ru.practicum.emw.main.event.service.EventAdminService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.emw.main.common.CommonUtils.DATE_TIME_PATTERN;
import static ru.practicum.emw.main.common.CommonUtils.DEFAULT_FROM;
import static ru.practicum.emw.main.common.CommonUtils.DEFAULT_SIZE;
import static ru.practicum.emw.main.common.CommonUtils.getPageRequestUnsorted;
import static ru.practicum.emw.main.event.dto.EventMapper.toEventFullDto;
import static ru.practicum.emw.main.event.dto.EventMapper.toEventFullDtos;

@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
@Slf4j
class EventAdminController {

    private final EventAdminService eventAdminService;

    @GetMapping
    List<EventFullDto> getAll(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<State> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeEnd,
            @RequestParam(required = false, defaultValue = DEFAULT_FROM) int from,
            @RequestParam(required = false, defaultValue = DEFAULT_SIZE) int size
    ) {
        log.debug(
                "getAll (users = {}, states = {}, categories = {}, rangeStart = {}, rangeEnd = {}, from = {}, size = {})",
                users, states, categories, rangeStart, rangeEnd, from, size
        );

        EventAdminParams params = new EventAdminParams(
                users,
                states,
                categories,
                rangeStart,
                rangeEnd
        );

        Pageable pageable = getPageRequestUnsorted(from, size);
        List<Event> events = eventAdminService.findAllByParams(params, pageable);
        return toEventFullDtos(events);
    }

    @PatchMapping("/{eventId}")
    EventFullDto patch(
            @PathVariable long eventId,
            @RequestBody @Valid UpdateEventAdminRequest updateEventAdminRequest
    ) {
        log.debug("patch (eventId = {}, updateEventAdminRequest = {})", eventId, updateEventAdminRequest);

        Event event = eventAdminService.updateById(eventId, updateEventAdminRequest);
        return toEventFullDto(event);
    }

}
