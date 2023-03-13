package ru.practicum.emw.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.emw.main.compilation.dto.CompilationDto;
import ru.practicum.emw.main.compilation.dto.NewCompilationDto;
import ru.practicum.emw.main.compilation.dto.UpdateCompilationRequest;
import ru.practicum.emw.main.compilation.entity.Compilation;
import ru.practicum.emw.main.compilation.service.CompilationAdminService;

import javax.validation.Valid;

import static ru.practicum.emw.main.compilation.dto.CompilationMapper.toCompilationDto;

@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
@Slf4j
public class CompilationAdminController {

    private final CompilationAdminService compilationAdminService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CompilationDto post(
            @RequestBody @Valid NewCompilationDto newCompilationDto
    ) {
        log.debug("post (newCompilationDto = {})", newCompilationDto);

        Compilation compilation = compilationAdminService.save(newCompilationDto);
        return toCompilationDto(compilation);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(
            @PathVariable long compId
    ) {
        log.debug("delete (compId = {})", compId);

        compilationAdminService.deleteById(compId);
    }

    @PatchMapping("/{compId}")
    CompilationDto patch(
            @PathVariable long compId,
            @RequestBody @Valid UpdateCompilationRequest updateCompilationRequest
    ) {
        log.debug("patch (compId = {}, updateCompilationRequest = {})", compId, updateCompilationRequest);

        Compilation compilation = compilationAdminService.updateById(compId, updateCompilationRequest);
        return toCompilationDto(compilation);
    }

}
