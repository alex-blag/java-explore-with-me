package ru.practicum.emw.main.compilation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class NewCompilationDto {

    private List<Long> events;

    private Boolean pinned;

    @NotBlank
    @Size(max = 120)
    private String title;

}
