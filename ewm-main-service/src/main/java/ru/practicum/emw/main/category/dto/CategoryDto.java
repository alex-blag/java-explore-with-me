package ru.practicum.emw.main.category.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CategoryDto {

    private Long id;

    @NotBlank
    @Size(max = 120)
    private String name;

}
