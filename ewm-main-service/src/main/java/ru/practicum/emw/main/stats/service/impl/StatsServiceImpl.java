package ru.practicum.emw.main.stats.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.practicum.emw.main.common.CommonUtils;
import ru.practicum.emw.main.stats.service.StatsService;
import ru.practicum.emw.stats.client.StatsClient;
import ru.practicum.ewm.stats.common.dto.EndpointHitPostDto;
import ru.practicum.ewm.stats.common.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {

    @Lazy
    private final StatsClient statsClient;

    @Override
    public EndpointHitPostDto postHit(EndpointHitPostDto endpointHitPostDto) {
        log.debug("postHit (endpointHitPostDto = {})", endpointHitPostDto);

        return statsClient.postHit(endpointHitPostDto);
    }

    @Override
    public Map<Long, Long> mapEventIdsToViews(List<Long> eventIds, LocalDateTime startStats) {
        log.debug("mapEventIdsToViews (eventIds = {}, startStats = {})", eventIds, startStats);

        Map<Long, String> eventIdToUri = eventIds
                .stream()
                .collect(toMap(eventId -> eventId, CommonUtils::buildEventEndpointUri));

        List<String> uris = new ArrayList<>(eventIdToUri.values());

        Map<String, Long> uriToHits = statsClient
                .getStats(startStats, LocalDateTime.now(), uris, false)
                .stream()
                .collect(toMap(ViewStats::getUri, ViewStats::getHits));

        return eventIdToUri
                .entrySet()
                .stream()
                .collect(toMap(
                        Map.Entry::getKey,
                        mapEntry -> uriToHits.getOrDefault(mapEntry.getValue(), 0L)
                ));
    }

}
