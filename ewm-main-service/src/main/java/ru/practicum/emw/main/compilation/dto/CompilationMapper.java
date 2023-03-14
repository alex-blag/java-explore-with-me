package ru.practicum.emw.main.compilation.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.emw.main.compilation.entity.Compilation;
import ru.practicum.emw.main.event.entity.Event;

import java.util.List;
import java.util.Set;

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

    public static Compilation toCompilation(NewCompilationDto newCompilationDto, Set<Event> events) {
        Compilation compilation = new Compilation();

        compilation.setEvents(events);

        compilation.setPinned(newCompilationDto.getPinned());

        compilation.setTitle(newCompilationDto.getTitle());

        return compilation;
    }

}
