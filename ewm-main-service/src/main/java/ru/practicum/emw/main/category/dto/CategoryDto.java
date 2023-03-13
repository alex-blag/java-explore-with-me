package ru.practicum.emw.main.category.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CategoryDto {

    private Long id;

    @NotBlank
    private String name;

}
