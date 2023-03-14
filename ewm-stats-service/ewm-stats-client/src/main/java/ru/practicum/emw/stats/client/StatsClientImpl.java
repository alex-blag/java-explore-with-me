package ru.practicum.emw.stats.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.ewm.stats.common.dto.EndpointHitPostDto;
import ru.practicum.ewm.stats.common.dto.ViewStats;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.practicum.emw.stats.common.CommonUtils.EWM_STATS_HIT_ENDPOINT;
import static ru.practicum.emw.stats.common.CommonUtils.EWM_STATS_SERVER_URI_PROPERTY;
import static ru.practicum.emw.stats.common.CommonUtils.EWM_STATS_STATS_ENDPOINT;

@Service
@RequiredArgsConstructor
public class StatsClientImpl implements StatsClient {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    @Value(EWM_STATS_SERVER_URI_PROPERTY)
    private final String serverUri;

    @Lazy
    @Autowired
    private final RestTemplate restTemplate;

    @Override
    public EndpointHitPostDto postHit(EndpointHitPostDto endpointHitPostDto) {
        URI uri = UriComponentsBuilder
                .fromUriString(serverUri)
                .path(EWM_STATS_HIT_ENDPOINT)
                .build()
                .toUri();

        HttpEntity<EndpointHitPostDto> request = new HttpEntity<>(endpointHitPostDto);

        return restTemplate.postForObject(uri, request, EndpointHitPostDto.class);
    }

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        URI statsUri = UriComponentsBuilder
                .fromUriString(serverUri)
                .path(EWM_STATS_STATS_ENDPOINT)
                .queryParam("start", DATE_TIME_FORMATTER.format(start))
                .queryParam("end", DATE_TIME_FORMATTER.format(end))
                .queryParam("uris", uris)
                .queryParam("unique", unique)
                .build()
                .encode()
                .toUri();

        ViewStats[] viewStats = restTemplate.getForObject(statsUri, ViewStats[].class);
        return viewStats != null
                ? List.of(viewStats)
                : List.of();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
