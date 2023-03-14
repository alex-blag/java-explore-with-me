package ru.practicum.emw.main.stats.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.emw.main.stats.service.StatsPublicService;
import ru.practicum.emw.main.stats.service.StatsService;
import ru.practicum.ewm.stats.common.dto.EndpointHitPostDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsPublicServiceImpl implements StatsPublicService {

    private final StatsService statsService;

    @Override
    public EndpointHitPostDto postHit(EndpointHitPostDto endpointHitPostDto) {
        log.debug("postHit (endpointHitPostDto = {})", endpointHitPostDto);

        return statsService.postHit(endpointHitPostDto);
    }

}
