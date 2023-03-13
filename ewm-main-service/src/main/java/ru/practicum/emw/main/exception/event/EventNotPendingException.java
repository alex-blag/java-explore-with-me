package ru.practicum.emw.main.exception.event;

public class EventNotPendingException extends RuntimeException {

    public EventNotPendingException(String message) {
        super(message);
    }

}
