package ru.practicum.emw.main.compilation.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.emw.main.compilation.entity.Compilation;

import java.util.List;

public interface CompilationPublicService {

    Compilation findById(long id);

    List<Compilation> findAllByPinned(Boolean pinned, Pageable pageable);

}
