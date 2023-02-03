package ru.practicum.ewm.stats.server.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.stats.common.dto.EndpointHitPostDto;
import ru.practicum.ewm.stats.server.entity.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {

    EndpointHitPostDto save(EndpointHitPostDto endpointHitPostDto);

    List<ViewStats> findAllByTimestampBetween(
            LocalDateTime start,
            LocalDateTime end,
            boolean unique,
            Pageable pageable
    );

    List<ViewStats> findAllByTimestampBetweenAndUriIn(
            LocalDateTime start,
            LocalDateTime end,
            List<String> uris,
            boolean unique,
            Pageable pageable
    );

}
