package ru.practicum.emw.stats.client;

import ru.practicum.ewm.stats.common.dto.EndpointHitPostDto;
import ru.practicum.ewm.stats.common.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsClient {

    EndpointHitPostDto postHit(EndpointHitPostDto endpointHitPostDto);

    ViewStatsDto getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);

}
