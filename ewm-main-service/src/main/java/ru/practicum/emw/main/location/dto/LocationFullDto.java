package ru.practicum.emw.main.location.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class LocationFullDto {

    private Long id;

    @Size(max = 120)
    private String name;

    private Double lat;

    private Double lon;

    @Size(max = 7000)
    private String description;

}
