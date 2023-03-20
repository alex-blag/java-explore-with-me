package ru.practicum.emw.main.location.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.emw.main.location.entity.Location;

import java.util.List;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationMapper {

    public static LocationDto toLocationDto(Location location) {
        LocationDto dto = new LocationDto();

        dto.setLat(location.getLat());

        dto.setLon(location.getLon());

        return dto;
    }

    public static Location toLocation(LocationDto dto) {
        Location location = new Location();

        location.setLat(dto.getLat());

        location.setLon(dto.getLon());

        return location;
    }

    public static Location toLocation(LocationNewDto dto) {
        Location location = new Location();

        location.setName(dto.getName());

        location.setLat(dto.getLat());

        location.setLon(dto.getLon());

        location.setDescription(dto.getDescription());

        return location;
    }

    public static LocationFullDto toLocationFullDto(Location location) {
        LocationFullDto dto = new LocationFullDto();

        dto.setId(location.getId());

        dto.setName(location.getName());

        dto.setLat(location.getLat());

        dto.setLon(location.getLon());

        dto.setDescription(location.getDescription());

        return dto;
    }

    public static List<LocationFullDto> toLocationFullDtos(List<Location> locations) {
        return locations
                .stream()
                .map(LocationMapper::toLocationFullDto)
                .collect(toList());
    }

    public static Location toLocation(double lat, double lon) {
        Location location = new Location();

        location.setLat(lat);

        location.setLon(lon);

        return location;
    }

}
