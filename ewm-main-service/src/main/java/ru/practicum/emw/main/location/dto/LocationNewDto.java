package ru.practicum.emw.main.location.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class LocationNewDto {

    @NotBlank
    private String name;

    @NotNull
    private Double lat;

    @NotNull
    private Double lon;

}
