package ru.practicum.emw.main.location.service;

import ru.practicum.emw.main.location.entity.Location;

public interface LocationPrivateService {

    Location findLocationByLatAndLonOrSaveNew(double lat, double lon);

}
