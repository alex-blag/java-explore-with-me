package ru.practicum.emw.main.location.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationFullDto {

    private Long id;

    private String name;

    private Double lat;

    private Double lon;

}
