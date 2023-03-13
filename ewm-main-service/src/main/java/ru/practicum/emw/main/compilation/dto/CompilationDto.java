package ru.practicum.emw.main.compilation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.emw.main.event.dto.EventShortDto;

import java.util.List;

@Data
@NoArgsConstructor
public class CompilationDto {

    private Long id;

    private List<EventShortDto> events;

    private Boolean pinned;

    private String title;

}
