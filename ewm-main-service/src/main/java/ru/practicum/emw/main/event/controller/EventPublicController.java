package ru.practicum.emw.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.emw.main.event.dto.EventFullDto;
import ru.practicum.emw.main.event.dto.EventPublicParams;
import ru.practicum.emw.main.event.dto.EventPublicSort;
import ru.practicum.emw.main.event.dto.EventShortDto;
import ru.practicum.emw.main.event.entity.Event;
import ru.practicum.emw.main.event.service.EventPublicService;
import ru.practicum.emw.main.location.dto.AreaOfInterest;
import ru.practicum.emw.main.stats.service.StatsPublicService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.emw.main.common.CommonUtils.DATE_TIME_PATTERN;
import static ru.practicum.emw.main.common.CommonUtils.DEFAULT_FROM;
import static ru.practicum.emw.main.common.CommonUtils.DEFAULT_SIZE;
import static ru.practicum.emw.main.common.CommonUtils.EVENTS_ENDPOINT;
import static ru.practicum.emw.main.common.CommonUtils.buildMainServiceEndpointHitPostDto;
import static ru.practicum.emw.main.common.CommonUtils.getPageRequestUnsorted;
import static ru.practicum.emw.main.common.CommonUtils.toBoolean;
import static ru.practicum.emw.main.common.CommonUtils.toText;
import static ru.practicum.emw.main.event.dto.EventMapper.toEventFullDto;
import static ru.practicum.emw.main.event.dto.EventMapper.toEventShortDtos;

@RestController
@RequestMapping(path = EVENTS_ENDPOINT)
@Slf4j
@RequiredArgsConstructor
@Validated
class EventPublicController {

    private final EventPublicService eventPublicService;

    @Lazy
    private final StatsPublicService statsPublicService;

    @GetMapping
    List<EventShortDto> getAll(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) String paid,
            @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false) EventPublicSort sort,
            @RequestParam(defaultValue = DEFAULT_FROM) @PositiveOrZero int from,
            @RequestParam(defaultValue = DEFAULT_SIZE) @Positive int size,
            @RequestParam(required = false) Long locationId,
            @RequestBody(required = false) AreaOfInterest areaOfInterest,
            HttpServletRequest request
    ) {
        log.debug(
                "getAll(text = {}, categories = {}, paid = {}, rangeStart = {}, rangeEnd = {}, onlyAvailable = {}, sort = {}, from = {}, size = {}, locationId = {}, areaOfInterest = {})",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, locationId, areaOfInterest
        );

        EventPublicParams params = new EventPublicParams(
                toText(text),
                categories,
                toBoolean(paid),
                rangeStart,
                rangeEnd,
                locationId,
                areaOfInterest
        );

        Pageable pageable = getPageRequestUnsorted(from, size);
        List<Event> events = eventPublicService.findAllByParams(params, onlyAvailable, sort, pageable);

        postHit(request.getRequestURI(), request.getRemoteAddr());

        return toEventShortDtos(events);
    }

    @GetMapping("/{id}")
    EventFullDto get(
            @PathVariable long id,
            HttpServletRequest request
    ) {
        log.debug("get(id = {})", id);

        Event event = eventPublicService.findById(id);

        postHit(request.getRequestURI(), request.getRemoteAddr());

        return toEventFullDto(event);
    }

    private void postHit(String endpointUri, String remoteIp) {
        log.debug("postHit(endpointUri = {}, remoteIp = {})", endpointUri, remoteIp);

        statsPublicService.postHit(buildMainServiceEndpointHitPostDto(endpointUri, remoteIp));
    }

}
