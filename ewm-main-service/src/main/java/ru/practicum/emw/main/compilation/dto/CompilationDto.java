package ru.practicum.emw.main.compilation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.emw.main.event.dto.EventShortDto;

import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
public class CompilationDto {

    private Long id;

    private Set<EventShortDto> events;

    private Boolean pinned;

    @Size(max = 120)
    private String title;

}
