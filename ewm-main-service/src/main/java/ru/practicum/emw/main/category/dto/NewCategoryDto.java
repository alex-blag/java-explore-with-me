package ru.practicum.emw.main.category.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class NewCategoryDto {

    @NotBlank
    private String name;

}
