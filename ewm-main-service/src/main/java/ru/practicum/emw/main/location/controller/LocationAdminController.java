package ru.practicum.emw.main.location.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.emw.main.location.dto.LocationFullDto;
import ru.practicum.emw.main.location.dto.LocationNewDto;
import ru.practicum.emw.main.location.entity.Location;
import ru.practicum.emw.main.location.service.LocationAdminService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.emw.main.common.CommonUtils.DEFAULT_FROM;
import static ru.practicum.emw.main.common.CommonUtils.DEFAULT_SIZE;
import static ru.practicum.emw.main.common.CommonUtils.getPageRequestUnsorted;
import static ru.practicum.emw.main.location.dto.LocationMapper.toLocationFullDto;
import static ru.practicum.emw.main.location.dto.LocationMapper.toLocationFullDtos;

@RestController
@RequestMapping(path = "/admin/locations")
@Slf4j
@RequiredArgsConstructor
@Validated
class LocationAdminController {

    private final LocationAdminService locationAdminService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    LocationFullDto post(
            @RequestBody @Valid LocationNewDto locationNewDto
    ) {
        log.debug("post (locationNewDto = {})", locationNewDto);

        Location location = locationAdminService.save(locationNewDto);

        return toLocationFullDto(location);
    }

    @PatchMapping("/{id}")
    LocationFullDto patch(
            @PathVariable long id,
            @RequestBody @Valid LocationFullDto locationFullDto

    ) {
        log.debug("patch (id = {}, locationFullDto = {})", id, locationFullDto);

        Location location = locationAdminService.updateById(id, locationFullDto);

        return toLocationFullDto(location);
    }

    @GetMapping
    List<LocationFullDto> getAll(
            @RequestParam(defaultValue = "") List<Long> ids,
            @RequestParam(defaultValue = DEFAULT_FROM) @PositiveOrZero int from,
            @RequestParam(defaultValue = DEFAULT_SIZE) @Positive int size
    ) {
        log.debug("getAll (ids = {}, from = {}, size = {})", ids, from, size);

        Pageable pageable = getPageRequestUnsorted(from, size);
        List<Location> locations = locationAdminService.findAllByIds(ids, pageable);

        return toLocationFullDtos(locations);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(
            @PathVariable long id
    ) {
        log.debug("delete (id = {})", id);

        locationAdminService.deleteById(id);
    }

}
