package ru.practicum.emw.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.emw.main.category.dto.CategoryDto;
import ru.practicum.emw.main.user.dto.UserShortDto;

import java.time.LocalDateTime;

import static ru.practicum.emw.main.common.CommonUtils.DATE_TIME_PATTERN;

@Data
@NoArgsConstructor
public class EventShortDto {

    private Long id;

    private String annotation;

    private CategoryDto category;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime eventDate;

    private UserShortDto initiator;

    private Boolean paid;

    private String title;

    private Long confirmedRequests;

    private Long views;

}
