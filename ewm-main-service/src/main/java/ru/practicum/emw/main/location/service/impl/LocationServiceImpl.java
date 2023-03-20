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
import ru.practicum.emw.main.location.entity.QLocation;
import ru.practicum.emw.main.location.repository.LocationRepository;
import ru.practicum.emw.main.location.service.LocationService;

import java.util.List;

import static ru.practicum.emw.main.common.CheckUtils.checkLocationExistsOrThrow;
import static ru.practicum.emw.main.exception.ExceptionUtils.getLocationNotFoundException;
import static ru.practicum.emw.main.location.dto.LocationMapper.toLocation;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private static final QLocation Q_LOCATION = QLocation.location;

    private final LocationRepository locationRepository;

    @Override
    @Transactional
    public Location save(Location location) {
        log.debug("save (location = {})", location);

        return locationRepository.save(location);
    }

    @Override
    public Location findById(long id) {
        log.debug("findById (id = {})", id);

        return locationRepository
                .findById(id)
                .orElseThrow(() -> getLocationNotFoundException(id));
    }

    @Override
    public List<Location> findAll(Predicate predicate, Pageable pageable) {
        log.debug("findAll (predicate = [{}], pageable = {})", predicate, pageable);

        return locationRepository
                .findAll(predicate, pageable)
                .getContent();
    }

    @Override
    @Transactional
    public Location findLocationByLatAndLonOrSaveNew(double lat, double lon) {
        log.debug("findLocationByLatAndLonOrSaveNew (lat = {}, lon = {})", lat, lon);

        Predicate p = buildQLocationPredicateByLatAndLon(lat, lon);
        return locationRepository
                .findOne(p)
                .orElseGet(() -> locationRepository.save(toLocation(lat, lon)));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        log.debug("deleteById (id = {})", id);

        checkLocationExistsOrThrow(id, locationRepository.existsById(id));
        locationRepository.deleteById(id);
    }

    @Override
    public List<Location> findAllByAreaOfInterest(AreaOfInterest areaOfInterest) {
        log.debug("findAllByAreaOfInterest (areaOfInterest = {})", areaOfInterest);

        return locationRepository.findAllByAreaOfInterest(
                areaOfInterest.getLat(), areaOfInterest.getLon(), areaOfInterest.getRadius()
        );
    }

    @Override
    public List<Long> findIdsByAreaOfInterest(AreaOfInterest areaOfInterest) {
        return locationRepository.findIdsByAreaOfInterest(
                areaOfInterest.getLat(), areaOfInterest.getLon(), areaOfInterest.getRadius()
        );
    }

    private Predicate buildQLocationPredicateByLatAndLon(double lat, double lon) {
        return new BooleanBuilder()
                .and(Q_LOCATION.lat.eq(lat))
                .and(Q_LOCATION.lon.eq(lon));
    }

}
