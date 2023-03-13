package ru.practicum.emw.main.compilation.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.emw.main.compilation.dto.NewCompilationDto;
import ru.practicum.emw.main.compilation.dto.UpdateCompilationRequest;
import ru.practicum.emw.main.compilation.entity.Compilation;
import ru.practicum.emw.main.compilation.service.CompilationAdminService;
import ru.practicum.emw.main.compilation.service.CompilationService;
import ru.practicum.emw.main.event.entity.Event;
import ru.practicum.emw.main.event.service.EventAdminService;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CompilationAdminServiceImpl implements CompilationAdminService {

    private final CompilationService compilationService;

    private final EventAdminService eventAdminService;

    @Override
    @Transactional
    public Compilation save(NewCompilationDto newCompilationDto) {
        log.debug("save (newCompilationDto = {})", newCompilationDto);

        Compilation compilation = new Compilation();

        List<Long> eventIds = newCompilationDto.getEvents();
        List<Event> events = eventAdminService.findAllByIds(eventIds);
        compilation.setEvents(events);

        compilation.setPinned(newCompilationDto.getPinned());

        compilation.setTitle(newCompilationDto.getTitle());

        return compilationService.save(compilation);
    }

    @Override
    @Transactional
    public Compilation updateById(long id, UpdateCompilationRequest updateCompilationRequest) {
        log.debug("updateById (id = {}, updateCompilationRequest = {})", id, updateCompilationRequest);

        Compilation compilation = compilationService.findById(id);

        List<Long> eventIds = updateCompilationRequest.getEvents();
        if (eventIds != null) {
            compilation.setEvents(eventAdminService.findAllByIds(eventIds));
        }

        Boolean pinned = updateCompilationRequest.getPinned();
        if (pinned != null) {
            compilation.setPinned(pinned);
        }

        String title = updateCompilationRequest.getTitle();
        if (hasText(title)) {
            compilation.setTitle(title);
        }

        return compilation;
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        log.debug("deleteById (id = {})", id);

        compilationService.deleteById(id);
    }

}
