package ru.practicum.emw.main.request.service;

import ru.practicum.emw.main.event.dto.EventRequestStatusUpdateRequest;
import ru.practicum.emw.main.event.dto.EventRequestStatusUpdateResult;
import ru.practicum.emw.main.request.entity.Request;

import java.util.List;

public interface RequestPrivateService {

    Request saveByEventIdAndRequesterId(long eventId, long requesterId);

    EventRequestStatusUpdateResult updateRequestStatusByEventIdAndInitiatorId(
            long eventId, long initiatorId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest
    );

    Request cancelByIdAndRequesterId(long id, long requesterId);

    List<Request> findAllByRequesterId(long requesterId);

    List<Request> findAllByEventIdAndInitiatorId(long eventId, long initiatorId);

}
