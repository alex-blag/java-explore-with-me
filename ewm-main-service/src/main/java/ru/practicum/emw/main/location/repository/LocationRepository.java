package ru.practicum.emw.main.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import ru.practicum.emw.main.location.entity.Location;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long>, QuerydslPredicateExecutor<Location> {

    @Procedure(procedureName = "find_location_ids_by_area_of_interest")
    List<Long> findIdsByAreaOfInterest(
            @Param("p_lat") double lat, @Param("p_lon") double lon, @Param("p_radius") int radius
    );

    @Procedure(procedureName = "find_all_locations_by_area_of_interest")
    List<Location> findAllByAreaOfInterest(
            @Param("p_lat") double lat, @Param("p_lon") double lon, @Param("p_radius") int radius
    );

}
