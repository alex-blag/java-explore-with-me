package ru.practicum.emw.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.emw.main.category.dto.CategoryDto;
import ru.practicum.emw.main.event.entity.State;
import ru.practicum.emw.main.user.dto.UserShortDto;

import java.time.LocalDateTime;

import static ru.practicum.emw.main.common.CommonUtils.DATE_TIME_PATTERN;

@Data
@NoArgsConstructor
public class EventFullDto {

    private Long id;

    private String annotation;

    private CategoryDto category;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime createdOn;

    private String description;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime eventDate;

    private UserShortDto initiator;

    private LocationDto location;

    private Boolean paid;

    private Integer participantLimit;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime publishedOn;

    private Boolean requestModeration;

    private State state;

    private String title;

    private Long confirmedRequests;

    private Long views;

}
