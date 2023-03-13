package ru.practicum.emw.main.request.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.emw.main.request.entity.Request;

import java.util.List;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestMapper {

    public static List<ParticipationRequestDto> toParticipationRequestDtos(List<Request> requests) {
        return requests
                .stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(toList());
    }

    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        ParticipationRequestDto dto = new ParticipationRequestDto();

        dto.setId(request.getId());

        dto.setCreated(request.getCreated());

        dto.setEvent(request.getEvent().getId());

        dto.setRequester(request.getRequester().getId());

        dto.setStatus(request.getStatus());

        return dto;
    }

}
