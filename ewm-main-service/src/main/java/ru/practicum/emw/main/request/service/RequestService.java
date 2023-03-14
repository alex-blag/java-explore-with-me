package ru.practicum.emw.main.request.service;

import com.querydsl.core.types.Predicate;
import ru.practicum.emw.main.request.entity.Request;

import java.util.List;
import java.util.Map;

public interface RequestService {

    Request save(Request request);

    Request findOne(Predicate predicate);

    List<Request> findAll(Predicate predicate);

    Map<Long, Long> mapEventIdsToConfirmedRequests(List<Long> eventIds);

    boolean exists(Predicate predicate);

}
