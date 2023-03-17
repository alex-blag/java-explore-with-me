package ru.practicum.emw.main.location.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.emw.main.location.dto.LocationFullDto;
import ru.practicum.emw.main.location.dto.LocationNewDto;
import ru.practicum.emw.main.location.entity.Location;

import java.util.List;

public interface LocationAdminService {

    Location save(LocationNewDto locationNewDto);

    Location updateById(long id, LocationFullDto locationFullDto);

    List<Location> findAllByIds(List<Long> ids, Pageable pageable);

    Location findLocationByLatAndLonOrSaveNew(double lat, double lon);

    void deleteById(long id);

}
