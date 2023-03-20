package ru.practicum.emw.main.location.service;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import ru.practicum.emw.main.location.dto.AreaOfInterest;
import ru.practicum.emw.main.location.entity.Location;

import java.util.List;

public interface LocationService {

    Location save(Location location);

    Location findById(long id);

    List<Location> findAll(Predicate predicate, Pageable pageable);

    Location findLocationByLatAndLonOrSaveNew(double lat, double lon);

    void deleteById(long id);

    List<Location> findAllByAreaOfInterest(AreaOfInterest areaOfInterest);

    List<Long> findIdsByAreaOfInterest(AreaOfInterest areaOfInterest);

}
