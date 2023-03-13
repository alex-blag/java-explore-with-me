package ru.practicum.emw.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.emw.main.common.CommonUtils.DATE_TIME_PATTERN;

@Data
@NoArgsConstructor
public class UpdateEventUserRequest {

    @Size(min = 20, max = 2000)
    private String annotation;

    private Long category;

    @Size(min = 20, max = 7000)
    private String description;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime eventDate;

    private LocationDto location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private EventUserStateAction stateAction;

    @Size(min = 3, max = 120)
    private String title;

}
