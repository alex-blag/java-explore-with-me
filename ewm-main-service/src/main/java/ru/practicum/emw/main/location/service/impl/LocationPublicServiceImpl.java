package ru.practicum.emw.main.location.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.emw.main.location.dto.AreaOfInterest;
import ru.practicum.emw.main.location.entity.Location;
import ru.practicum.emw.main.location.service.LocationPublicService;
import ru.practicum.emw.main.location.service.LocationService;

import java.util.List;

import static ru.practicum.emw.main.common.CommonUtils.toPage;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class LocationPublicServiceImpl implements LocationPublicService {

    private final LocationService locationService;

    @Override
    public Location findById(long id) {
        log.debug("findById (id = {})", id);

        return locationService.findById(id);
    }

    @Override
    public List<Location> findAllByAreaOfInterest(AreaOfInterest areaOfInterest, Pageable pageable) {
        log.debug("findAllByAreaOfInterest (areaOfInterest = {}, pageable = {})", areaOfInterest, pageable);

        List<Location> locations;
        if (areaOfInterest != null) {
            locations = locationService.findAllByAreaOfInterest(areaOfInterest);
            locations = toPage(locations, pageable).getContent();

        } else {
            Predicate p = buildQLocationPredicate();
            locations = locationService.findAll(p, pageable);
        }

        return locations;
    }

    @Override
    public List<Long> findIdsByAreaOfInterest(AreaOfInterest areaOfInterest) {
        log.debug("findAllIdsByAreaOfInterest (areaOfInterest = {})", areaOfInterest);

        return locationService.findIdsByAreaOfInterest(areaOfInterest);
    }

    private Predicate buildQLocationPredicate() {
        return new BooleanBuilder();
    }

}
