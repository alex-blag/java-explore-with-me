package ru.practicum.emw.main.location.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.emw.main.location.dto.AreaOfInterest;
import ru.practicum.emw.main.location.entity.Location;

import java.util.List;

public interface LocationPublicService {

    Location findById(long id);

    List<Location> findAllByAreaOfInterest(AreaOfInterest areaOfInterest, Pageable pageable);

    List<Long> findIdsByAreaOfInterest(AreaOfInterest areaOfInterest);

}
