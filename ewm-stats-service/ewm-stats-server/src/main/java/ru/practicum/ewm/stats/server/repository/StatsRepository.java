package ru.practicum.ewm.stats.server.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.stats.server.entity.EndpointHit;
import ru.practicum.ewm.stats.server.entity.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Long> {

    @Query(
            "SELECT eh.app AS app, " +
                    "   eh.uri AS uri, " +
                    "   COUNT(DISTINCT eh.ip) AS hits " +
                    "FROM EndpointHit eh " +
                    "WHERE eh.timestamp BETWEEN :start AND :end " +
                    "   AND eh.uri IN :uris " +
                    "GROUP BY eh.app, eh.uri"
    )
    List<ViewStats> findAllDistinctIpByTimestampBetweenAndUriIn(
            LocalDateTime start,
            LocalDateTime end,
            List<String> uris,
            Pageable pageable
    );

    @Query(
            "SELECT eh.app AS app, " +
                    "   eh.uri AS uri, " +
                    "   COUNT(eh.ip) AS hits " +
                    "FROM EndpointHit eh " +
                    "WHERE eh.timestamp BETWEEN :start AND :end " +
                    "   AND eh.uri IN :uris " +
                    "GROUP BY eh.app, eh.uri"
    )
    List<ViewStats> findAllByTimestampBetweenAndUriIn(
            LocalDateTime start,
            LocalDateTime end,
            List<String> uris,
            Pageable pageable
    );

}
