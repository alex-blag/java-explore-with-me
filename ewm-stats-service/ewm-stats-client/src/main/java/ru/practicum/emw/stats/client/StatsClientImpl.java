package ru.practicum.emw.stats.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.ewm.stats.common.dto.EndpointHitPostDto;
import ru.practicum.ewm.stats.common.dto.ViewStatsDto;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.emw.stats.common.CommonUtils.EWM_STATS_HIT_RESOURCE;
import static ru.practicum.emw.stats.common.CommonUtils.EWM_STATS_SERVER_URI_PROPERTY;
import static ru.practicum.emw.stats.common.CommonUtils.EWM_STATS_STATS_RESOURCE;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsClientImpl implements StatsClient {


    @Value(EWM_STATS_SERVER_URI_PROPERTY)
    private final String serverUri;

    private final RestTemplate restTemplate;

    @Override
    public EndpointHitPostDto postHit(EndpointHitPostDto endpointHitPostDto) {
        log.debug("postHit({})", endpointHitPostDto);

        URI hitUri = UriComponentsBuilder
                .fromUriString(serverUri)
                .path(EWM_STATS_HIT_RESOURCE)
                .build()
                .toUri();

        HttpEntity<EndpointHitPostDto> request = new HttpEntity<>(endpointHitPostDto);

        return restTemplate.postForObject(hitUri, request, EndpointHitPostDto.class);
    }

    @Override
    public ViewStatsDto getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        log.debug("getStats(start = {}, end = {}, uris = {}, unique = {})", start, end, uris, unique);

        URI statsUri = UriComponentsBuilder
                .fromUriString(serverUri)
                .path(EWM_STATS_STATS_RESOURCE)
                .build()
                .toUri();

        return restTemplate.getForObject(statsUri, ViewStatsDto.class);
    }

}
