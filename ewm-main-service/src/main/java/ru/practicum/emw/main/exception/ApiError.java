package ru.practicum.emw.main.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.emw.main.common.CommonUtils.DATE_TIME_PATTERN;

@Value
public class ApiError {

    List<String> errors;

    String message;

    String reason;

    HttpStatus status;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    LocalDateTime timestamp;

}
