package ru.practicum.emw.main.stats.service;

import ru.practicum.ewm.stats.common.dto.EndpointHitPostDto;

public interface StatsPublicService {

    @SuppressWarnings("UnusedReturnValue")
    EndpointHitPostDto postHit(EndpointHitPostDto buildMainServiceEndpointHitPostDto);

}
