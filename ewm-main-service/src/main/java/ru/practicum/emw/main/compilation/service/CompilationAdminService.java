package ru.practicum.emw.main.compilation.service;

import ru.practicum.emw.main.compilation.dto.NewCompilationDto;
import ru.practicum.emw.main.compilation.dto.UpdateCompilationRequest;
import ru.practicum.emw.main.compilation.entity.Compilation;

public interface CompilationAdminService {

    Compilation save(NewCompilationDto newCompilationDto);

    Compilation updateById(long id, UpdateCompilationRequest updateCompilationRequest);

    void deleteById(long id);

}
