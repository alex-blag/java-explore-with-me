package ru.practicum.emw.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.emw.main.compilation.dto.CompilationDto;
import ru.practicum.emw.main.compilation.entity.Compilation;
import ru.practicum.emw.main.compilation.service.CompilationPublicService;

import java.util.List;

import static ru.practicum.emw.main.common.CommonUtils.DEFAULT_FROM;
import static ru.practicum.emw.main.common.CommonUtils.DEFAULT_SIZE;
import static ru.practicum.emw.main.common.CommonUtils.getPageRequestUnsorted;
import static ru.practicum.emw.main.compilation.dto.CompilationMapper.toCompilationDto;
import static ru.practicum.emw.main.compilation.dto.CompilationMapper.toCompilationDtos;

@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
@Slf4j
class CompilationPublicController {

    private final CompilationPublicService compilationPublicService;

    @GetMapping
    List<CompilationDto> getAll(
            @RequestParam(required = false) Boolean pinned,
            @RequestParam(required = false, defaultValue = DEFAULT_FROM) int from,
            @RequestParam(required = false, defaultValue = DEFAULT_SIZE) int size
    ) {
        log.debug("getAll (pinned = {}, from = {}, size = {})", pinned, from, size);

        Pageable pageable = getPageRequestUnsorted(from, size);
        List<Compilation> compilations = compilationPublicService.findAllByPinned(pinned, pageable);
        return toCompilationDtos(compilations);
    }

    @GetMapping("/{compId}")
    CompilationDto get(
            @PathVariable long compId
    ) {
        log.debug("get (compId = {})", compId);

        Compilation compilation = compilationPublicService.findById(compId);
        return toCompilationDto(compilation);
    }

}
