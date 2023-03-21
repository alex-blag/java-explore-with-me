package ru.practicum.emw.main.compilation.service.impl;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.emw.main.compilation.entity.Compilation;
import ru.practicum.emw.main.compilation.repository.CompilationRepository;
import ru.practicum.emw.main.compilation.service.CompilationService;

import java.util.List;

import static ru.practicum.emw.main.common.CheckUtils.checkCompilationExistsOrThrow;
import static ru.practicum.emw.main.exception.ExceptionUtils.getCompilationNotFoundException;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;

    @Override
    @Transactional
    public Compilation save(Compilation compilation) {
        log.debug("save (compilation = {})", compilation);

        return compilationRepository.save(compilation);
    }

    @Override
    public Compilation findById(long id) {
        log.debug("findById (id = {})", id);

        return compilationRepository
                .findById(id)
                .orElseThrow(() -> getCompilationNotFoundException(id));
    }

    @Override
    public List<Compilation> findAll(Predicate predicate, Pageable pageable) {
        log.debug("findAll (predicate = [{}], pageable = {})", predicate, pageable);

        return compilationRepository
                .findAll(predicate, pageable)
                .getContent();
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        log.debug("deleteById (id = {})", id);

        checkCompilationExistsOrThrow(id, compilationRepository.existsById(id));
        compilationRepository.deleteById(id);
    }

}
