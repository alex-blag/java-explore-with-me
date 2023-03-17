package ru.practicum.emw.main.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.emw.main.exception.category.CategoryHasAssociatedEventException;
import ru.practicum.emw.main.exception.category.CategoryNotFoundException;
import ru.practicum.emw.main.exception.event.EventAlreadyPublishedException;
import ru.practicum.emw.main.exception.event.EventCannotBeUpdatedException;
import ru.practicum.emw.main.exception.event.EventDateTooEarlyException;
import ru.practicum.emw.main.exception.event.EventNotPendingException;
import ru.practicum.emw.main.exception.event.EventNotPublishedException;
import ru.practicum.emw.main.exception.event.EventParticipantLimitReachedException;
import ru.practicum.emw.main.exception.location.LocationNotFoundException;
import ru.practicum.emw.main.exception.request.RequestAlreadyCreatedException;
import ru.practicum.emw.main.exception.request.RequestNotFoundException;
import ru.practicum.emw.main.exception.request.RequesterOwnsEventException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
class ExceptionHandlerController {

    @ExceptionHandler
    ResponseEntity<ApiError> handle(CategoryNotFoundException e) {
        return buildResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    ResponseEntity<ApiError> handle(CategoryHasAssociatedEventException e) {
        return buildResponse(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    ResponseEntity<ApiError> handle(EventDateTooEarlyException e) {
        return buildResponse(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    ResponseEntity<ApiError> handle(EventNotPendingException e) {
        return buildResponse(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    ResponseEntity<ApiError> handle(EventAlreadyPublishedException e) {
        return buildResponse(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    ResponseEntity<ApiError> handle(EventNotPublishedException e) {
        return buildResponse(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    ResponseEntity<ApiError> handle(EventParticipantLimitReachedException e) {
        return buildResponse(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    ResponseEntity<ApiError> handle(EventCannotBeUpdatedException e) {
        return buildResponse(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handle(RequestNotFoundException e) {
        return buildResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    ResponseEntity<ApiError> handle(RequestAlreadyCreatedException e) {
        return buildResponse(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    ResponseEntity<ApiError> handle(RequesterOwnsEventException e) {
        return buildResponse(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    ResponseEntity<ApiError> handle(LocationNotFoundException e) {
        return buildResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    ResponseEntity<ApiError> handle(HttpRequestMethodNotSupportedException e) {
        return buildResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    ResponseEntity<ApiError> handle(MissingServletRequestParameterException e) {
        return buildResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    ResponseEntity<ApiError> handle(MethodArgumentNotValidException e) {
        return buildResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    ResponseEntity<ApiError> handle(ConstraintViolationException e) {
        return buildResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    ResponseEntity<ApiError> handle(DataIntegrityViolationException e) {
        return buildResponse(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    ResponseEntity<ApiError> handle(HttpMessageNotReadableException e) {
        return buildResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    ResponseEntity<ApiError> handle(Throwable e) {
        return buildResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    ResponseEntity<ApiError> buildResponse(Throwable e, HttpStatus httpStatus) {
        log.error("", e);

        ApiError apiError = new ApiError(
                null,
                e.getMessage(),
                null,
                httpStatus,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, httpStatus);
    }

}
