package ru.practicum.emw.main.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static ru.practicum.emw.main.common.CommonUtils.DATE_TIME_PATTERN;

@Data
@NoArgsConstructor
public class ParticipationRequestDto {

    private Long id;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime created;

    private Long event;

    private Long requester;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private RequestStatus status;

}
