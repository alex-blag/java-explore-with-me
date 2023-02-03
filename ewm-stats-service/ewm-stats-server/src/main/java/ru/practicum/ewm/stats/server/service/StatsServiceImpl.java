package ru.practicum.ewm.stats.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.stats.common.dto.EndpointHitPostDto;
import ru.practicum.ewm.stats.server.entity.EndpointHit;
import ru.practicum.ewm.stats.server.entity.ViewStats;
import ru.practicum.ewm.stats.server.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    @Transactional
    @Override
    public EndpointHitPostDto save(EndpointHitPostDto endpointHitPostDto) {
        EndpointHit endpointHit = toEndpointHit(endpointHitPostDto);

        return toEndpointHitDto(statsRepository.save(endpointHit));
    }

    @Override
    public List<ViewStats> findAllByTimestampBetween(
            LocalDateTime start,
            LocalDateTime end,
            boolean unique,
            Pageable pageable
    ) {
        return unique
                ? statsRepository.findAllDistinctIpByTimestampBetween(start, end, pageable)
                : statsRepository.findAllByTimestampBetween(start, end, pageable);
    }

    @Override
    public List<ViewStats> findAllByTimestampBetweenAndUriIn(
            LocalDateTime start,
            LocalDateTime end,
            List<String> uris,
            boolean unique,
            Pageable pageable
    ) {
        return unique
                ? statsRepository.findAllDistinctIpByTimestampBetweenAndUriIn(start, end, uris, pageable)
                : statsRepository.findAllByTimestampBetweenAndUriIn(start, end, uris, pageable);
    }

    private EndpointHit toEndpointHit(EndpointHitPostDto endpointHitPostDto) {
        return StatsMapper.toEndpointHit(endpointHitPostDto);
    }

    private EndpointHitPostDto toEndpointHitDto(EndpointHit endpointHit) {
        return StatsMapper.toEndpointHitDto(endpointHit);
    }

}