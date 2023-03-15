package ru.practicum.ewm.stats.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.stats.common.dto.EndpointHitPostDto;
import ru.practicum.ewm.stats.server.entity.EndpointHit;
import ru.practicum.ewm.stats.server.entity.ServiceApp;
import ru.practicum.ewm.stats.server.entity.ViewStats;
import ru.practicum.ewm.stats.server.repository.EndpointHitRepository;
import ru.practicum.ewm.stats.server.repository.ServiceAppRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final EndpointHitRepository endpointHitRepository;

    private final ServiceAppRepository serviceAppRepository;

    @Override
    @Transactional
    public EndpointHitPostDto save(EndpointHitPostDto endpointHitPostDto) {
        EndpointHit endpointHit = toEndpointHit(endpointHitPostDto);
        ServiceApp serviceApp = getServiceApp(endpointHit.getServiceApp());
        endpointHit.setServiceApp(serviceApp);

        return toEndpointHitDto(endpointHitRepository.save(endpointHit));
    }

    @Override
    public List<ViewStats> findAllByTimestampBetween(
            LocalDateTime start,
            LocalDateTime end,
            boolean unique,
            Pageable pageable
    ) {
        return unique
                ? endpointHitRepository.findAllDistinctIpByTimestampBetween(start, end, pageable)
                : endpointHitRepository.findAllByTimestampBetween(start, end, pageable);
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
                ? endpointHitRepository.findAllDistinctIpByTimestampBetweenAndUriIn(start, end, uris, pageable)
                : endpointHitRepository.findAllByTimestampBetweenAndUriIn(start, end, uris, pageable);
    }

    private ServiceApp getServiceApp(ServiceApp serviceApp) {
        ServiceApp sa = serviceAppRepository.findByName(serviceApp.getName());
        if (sa == null) {
            sa = serviceAppRepository.save(serviceApp);
        }
        return sa;
    }

    private EndpointHit toEndpointHit(EndpointHitPostDto endpointHitPostDto) {
        return StatsMapper.toEndpointHit(endpointHitPostDto);
    }

    private EndpointHitPostDto toEndpointHitDto(EndpointHit endpointHit) {
        return StatsMapper.toEndpointHitDto(endpointHit);
    }

}
