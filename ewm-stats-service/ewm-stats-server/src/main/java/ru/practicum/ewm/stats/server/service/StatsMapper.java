package ru.practicum.ewm.stats.server.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.stats.common.dto.EndpointHitPostDto;
import ru.practicum.ewm.stats.server.entity.EndpointHit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatsMapper {

    public static EndpointHit toEndpointHit(EndpointHitPostDto dto) {
        EndpointHit endpointHit = new EndpointHit();
        endpointHit.setApp(dto.getApp());
        endpointHit.setUri(dto.getUri());
        endpointHit.setIp(dto.getIp());
        endpointHit.setTimestamp(dto.getTimestamp());
        return endpointHit;
    }

    public static EndpointHitPostDto toEndpointHitDto(EndpointHit endpointHit) {
        EndpointHitPostDto dto = new EndpointHitPostDto();
        dto.setId(endpointHit.getId());
        dto.setApp(endpointHit.getApp());
        dto.setUri(endpointHit.getUri());
        dto.setIp(endpointHit.getIp());
        dto.setTimestamp(endpointHit.getTimestamp());
        return dto;
    }

}
