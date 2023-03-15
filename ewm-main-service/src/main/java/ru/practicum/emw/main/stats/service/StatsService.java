package ru.practicum.emw.main.stats.service;

import ru.practicum.ewm.stats.common.dto.EndpointHitPostDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface StatsService {

    EndpointHitPostDto postHit(EndpointHitPostDto endpointHitPostDto);

    Map<Long, Long> mapEventIdsToViews(List<Long> eventIds, LocalDateTime startStats);

}
