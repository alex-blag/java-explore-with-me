package ru.practicum.emw.main.location.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class LocationNewDto {

    @NotBlank
    @Size(max = 120)
    private String name;

    @NotNull
    private Double lat;

    @NotNull
    private Double lon;

    @NotBlank
    @Size(max = 7000)
    private String description;

}
