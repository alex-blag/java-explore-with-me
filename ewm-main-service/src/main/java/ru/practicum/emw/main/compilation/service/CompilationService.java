package ru.practicum.emw.main.compilation.service;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import ru.practicum.emw.main.compilation.entity.Compilation;

import java.util.List;

public interface CompilationService {

    Compilation save(Compilation compilation);

    Compilation findById(long id);

    List<Compilation> findAll(Predicate predicate, Pageable pageable);

    void deleteById(long id);

}
