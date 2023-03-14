package ru.practicum.emw.stats.client;

import ru.practicum.ewm.stats.common.dto.EndpointHitPostDto;
import ru.practicum.ewm.stats.common.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsClient {

    EndpointHitPostDto postHit(EndpointHitPostDto endpointHitPostDto);

    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);

}
