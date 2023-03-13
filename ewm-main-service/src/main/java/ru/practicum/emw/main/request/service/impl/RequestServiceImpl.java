package ru.practicum.emw.main.request.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.emw.main.request.dto.RequestStatus;
import ru.practicum.emw.main.request.entity.QRequest;
import ru.practicum.emw.main.request.entity.Request;
import ru.practicum.emw.main.request.repository.RequestRepository;
import ru.practicum.emw.main.request.service.RequestService;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static ru.practicum.emw.main.common.CommonUtils.toList;
import static ru.practicum.emw.main.exception.ExceptionUtils.getRequestNotFoundException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {

    private static final QRequest Q_REQUEST = QRequest.request;

    private final RequestRepository requestRepository;

    @Override
    public Request save(Request request) {
        log.debug("save (request = {})", request);

        return requestRepository.save(request);
    }

    @Override
    public Request findOne(Predicate predicate) {
        log.debug("findOne (predicate = [{}])", predicate);

        return requestRepository
                .findOne(predicate)
                .orElseThrow(() -> getRequestNotFoundException(predicate.toString()));
    }

    @Override
    public List<Request> findAll(Predicate predicate) {
        log.debug("findAll (predicate = [{}])", predicate);

        return toList(requestRepository.findAll(predicate));
    }

    @Override
    public Map<Long, Long> mapEventIdsToConfirmedRequests(List<Long> eventIds) {
        log.debug("mapEventIdsToConfirmedRequests (eventIds = {})", eventIds);

        Predicate p = buildQRequestPredicateConfirmedByEventIds(eventIds);
        return this
                .findAll(p)
                .stream()
                .collect(groupingBy(r -> r.getEvent().getId(), counting()));
    }

    @Override
    public boolean exists(Predicate predicate) {
        log.debug("exists (predicate = [{}])", predicate);

        return requestRepository.exists(predicate);
    }

    private Predicate buildQRequestPredicateConfirmedByEventIds(List<Long> eventIds) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(Q_REQUEST.status.eq(RequestStatus.CONFIRMED));

        if (eventIds != null) {
            builder.and(Q_REQUEST.event.id.in(eventIds));
        }

        return builder;
    }

}
