package ru.practicum.ewm.stats.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.stats.common.dto.EndpointHitPostDto;
import ru.practicum.ewm.stats.server.entity.ViewStats;
import ru.practicum.ewm.stats.server.service.StatsService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
class StatsController {

    private static final String HIT_RESOURCE = "/hit";

    private static final String STATS_RESOURCE = "/stats";

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private final StatsService statsService;

    @PostMapping(HIT_RESOURCE)
    @ResponseStatus(HttpStatus.CREATED)
    EndpointHitPostDto postEndpointHit(
            @RequestBody EndpointHitPostDto endpointHitPostDto
    ) {
        log.debug(HIT_RESOURCE + "({})", endpointHitPostDto);

        return statsService.save(endpointHitPostDto);
    }

    @GetMapping(STATS_RESOURCE)
    List<ViewStats> getStats(
            @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime end,
            @RequestParam(required = false, defaultValue = "") List<String> uris,
            @RequestParam(required = false, defaultValue = "false") boolean unique
    ) {
        log.debug(STATS_RESOURCE + "(start = {}, end = {}, uris = {}, unique = {})", start, end, uris, unique);

        return statsService.findAllByTimestampBetweenAndUriIn(start, end, uris, unique);
    }

}
