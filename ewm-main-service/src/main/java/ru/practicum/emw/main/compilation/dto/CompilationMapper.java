package ru.practicum.emw.main.compilation.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.emw.main.compilation.entity.Compilation;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static ru.practicum.emw.main.event.dto.EventMapper.toEventShortDtos;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompilationMapper {

    public static List<CompilationDto> toCompilationDtos(List<Compilation> compilations) {
        return compilations
                .stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(toList());
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        CompilationDto dto = new CompilationDto();

        dto.setId(compilation.getId());
        dto.setEvents(toEventShortDtos(compilation.getEvents()));
        dto.setPinned(compilation.getPinned());
        dto.setTitle(compilation.getTitle());

        return dto;
    }

}
