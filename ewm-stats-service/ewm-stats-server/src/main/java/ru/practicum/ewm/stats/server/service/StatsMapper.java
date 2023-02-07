package ru.practicum.ewm.stats.server.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.stats.common.dto.EndpointHitPostDto;
import ru.practicum.ewm.stats.server.entity.EndpointHit;
import ru.practicum.ewm.stats.server.entity.ServiceApp;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatsMapper {

    public static EndpointHit toEndpointHit(EndpointHitPostDto dto) {
        ServiceApp serviceApp = new ServiceApp();
        serviceApp.setName(dto.getApp());

        EndpointHit endpointHit = new EndpointHit();
        endpointHit.setServiceApp(serviceApp);
        endpointHit.setUri(dto.getUri());
        endpointHit.setIp(dto.getIp());
        endpointHit.setTimestamp(dto.getTimestamp());
        return endpointHit;
    }

    public static EndpointHitPostDto toEndpointHitDto(EndpointHit endpointHit) {
        EndpointHitPostDto dto = new EndpointHitPostDto();
        dto.setId(endpointHit.getId());
        dto.setApp(endpointHit.getServiceApp().getName());
        dto.setUri(endpointHit.getUri());
        dto.setIp(endpointHit.getIp());
        dto.setTimestamp(endpointHit.getTimestamp());
        return dto;
    }

}
