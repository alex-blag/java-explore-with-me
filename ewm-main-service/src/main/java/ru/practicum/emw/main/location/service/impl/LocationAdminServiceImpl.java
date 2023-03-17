package ru.practicum.emw.main.location.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.emw.main.event.service.EventAdminService;
import ru.practicum.emw.main.location.dto.LocationFullDto;
import ru.practicum.emw.main.location.dto.LocationNewDto;
import ru.practicum.emw.main.location.entity.Location;
import ru.practicum.emw.main.location.entity.QLocation;
import ru.practicum.emw.main.location.service.LocationAdminService;
import ru.practicum.emw.main.location.service.LocationService;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;
import static ru.practicum.emw.main.common.CheckUtils.checkLocationHasNoAssociatedEventsOrThrow;
import static ru.practicum.emw.main.location.dto.LocationMapper.toLocation;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class LocationAdminServiceImpl implements LocationAdminService {

    private static final QLocation Q_LOCATION = QLocation.location;

    private final LocationService locationService;

    @Lazy
    private final EventAdminService eventAdminService;

    @Override
    @Transactional
    public Location save(LocationNewDto locationNewDto) {
        log.debug("save (locationNewDto = {})", locationNewDto);

        Location location = toLocation(locationNewDto);

        return locationService.save(location);
    }

    @Override
    @Transactional
    public Location updateById(long id, LocationFullDto locationFullDto) {
        log.debug("updateById (id = {}, locationFullDto = {})", id, locationFullDto);

        Location location = locationService.findById(id);

        String name = locationFullDto.getName();
        if (hasText(name)) {
            location.setName(name);
        }

        Double lat = locationFullDto.getLat();
        if (lat != null) {
            location.setLat(lat);
        }

        Double lon = locationFullDto.getLon();
        if (lon != null) {
            location.setLon(lon);
        }

        return location;
    }

    @Override
    public List<Location> findAllByIds(List<Long> ids, Pageable pageable) {
        log.debug("findAllByIds (ids = {}, pageable = {})", ids, pageable);

        Predicate p = buildQLocationPredicateByIds(ids);
        return locationService.findAll(p, pageable);
    }

    @Override
    public Location findLocationByLatAndLonOrSaveNew(double lat, double lon) {
        log.debug("findLocationByLatAndLonOrSaveNew (lat = {}, lon = {})", lat, lon);

        return locationService.findLocationByLatAndLonOrSaveNew(lat, lon);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        log.debug("deleteById (id = {})", id);

        checkLocationHasNoAssociatedEventsOrThrow(id, eventAdminService.existsByLocationId(id));
        locationService.deleteById(id);
    }

    private Predicate buildQLocationPredicateByIds(List<Long> ids) {
        BooleanBuilder builder = new BooleanBuilder();

        if (!isEmpty(ids)) {
            builder.and(Q_LOCATION.id.in(ids));
        }

        return builder;
    }

}
