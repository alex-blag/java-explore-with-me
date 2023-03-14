package ru.practicum.emw.main.compilation.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.emw.main.compilation.entity.Compilation;
import ru.practicum.emw.main.compilation.entity.QCompilation;
import ru.practicum.emw.main.compilation.service.CompilationPublicService;
import ru.practicum.emw.main.compilation.service.CompilationService;
import ru.practicum.emw.main.event.entity.Event;
import ru.practicum.emw.main.event.service.EventPublicService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class CompilationPublicServiceImpl implements CompilationPublicService {

    private static final QCompilation Q_COMPILATION = QCompilation.compilation;

    private final CompilationService compilationService;

    private final EventPublicService eventPublicService;

    @Override
    public Compilation findById(long id) {
        log.debug("findById (id = {})", id);

        Compilation compilation = compilationService.findById(id);

        List<Event> events = new ArrayList<>(compilation.getEvents());

        eventPublicService.updateConfirmedRequestsAndViews(events);

        return compilation;
    }

    @Override
    public List<Compilation> findAllByPinned(Boolean pinned, Pageable pageable) {
        log.debug("findAllByPinned (pinned = {}, pageable = {})", pinned, pageable);

        Predicate p = buildQCompilationPredicateByPinned(pinned);
        List<Compilation> compilations = compilationService.findAll(p, pageable);

        List<Event> events = compilations
                .stream()
                .map(Compilation::getEvents)
                .flatMap(Collection::stream)
                .collect(toList());

        eventPublicService.updateConfirmedRequestsAndViews(events);

        return compilations;
    }

    private Predicate buildQCompilationPredicateByPinned(Boolean pinned) {
        BooleanBuilder builder = new BooleanBuilder();

        if (pinned != null) {
            builder.and(Q_COMPILATION.pinned.eq(pinned));
        }

        return builder;
    }

}
