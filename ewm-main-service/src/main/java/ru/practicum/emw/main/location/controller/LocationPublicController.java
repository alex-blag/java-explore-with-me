package ru.practicum.emw.main.location.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.emw.main.location.dto.AreaOfInterest;
import ru.practicum.emw.main.location.dto.LocationFullDto;
import ru.practicum.emw.main.location.entity.Location;
import ru.practicum.emw.main.location.service.LocationPublicService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.emw.main.common.CommonUtils.DEFAULT_FROM;
import static ru.practicum.emw.main.common.CommonUtils.DEFAULT_SIZE;
import static ru.practicum.emw.main.common.CommonUtils.getPageRequestUnsorted;
import static ru.practicum.emw.main.location.dto.LocationMapper.toLocationFullDto;
import static ru.practicum.emw.main.location.dto.LocationMapper.toLocationFullDtos;

@RestController
@RequestMapping(path = "/locations")
@Slf4j
@RequiredArgsConstructor
@Validated
class LocationPublicController {

    private final LocationPublicService locationPublicService;

    @GetMapping("/{id}")
    LocationFullDto get(
            @PathVariable long id
    ) {
        log.debug("get (id = {})", id);

        Location location = locationPublicService.findById(id);

        return toLocationFullDto(location);
    }

    @GetMapping
    List<LocationFullDto> getAll(
            @RequestParam(defaultValue = DEFAULT_FROM) @PositiveOrZero int from,
            @RequestParam(defaultValue = DEFAULT_SIZE) @Positive int size,
            @RequestBody(required = false) AreaOfInterest areaOfInterest
    ) {
        log.debug("getAll (from = {}, size = {}, areaOfInterest = {})", from, size, areaOfInterest);

        Pageable pageable = getPageRequestUnsorted(from, size);
        List<Location> locations = locationPublicService.findAllByAreaOfInterest(areaOfInterest, pageable);

        return toLocationFullDtos(locations);
    }

}
