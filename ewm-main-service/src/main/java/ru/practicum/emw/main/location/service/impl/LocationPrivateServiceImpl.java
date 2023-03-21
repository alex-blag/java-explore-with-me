package ru.practicum.emw.main.location.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.emw.main.location.entity.Location;
import ru.practicum.emw.main.location.service.LocationPrivateService;
import ru.practicum.emw.main.location.service.LocationService;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class LocationPrivateServiceImpl implements LocationPrivateService {

    private final LocationService locationService;

    @Override
    public Location findLocationByLatAndLonOrSaveNew(double lat, double lon) {
        log.debug("findLocationByLatAndLonOrSaveNew (lat = {}, lon = {})", lat, lon);

        return locationService.findLocationByLatAndLonOrSaveNew(lat, lon);
    }

}
