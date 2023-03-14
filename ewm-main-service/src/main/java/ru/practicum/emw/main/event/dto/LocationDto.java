package ru.practicum.emw.main.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {

    @NotNull
    private Double lat;

    @NotNull
    private Double lon;

}
