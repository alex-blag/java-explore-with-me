package ru.practicum.emw.main.exception.request;

public class RequestNotPendingException extends RuntimeException {

    public RequestNotPendingException(String message) {
        super(message);
    }

}
