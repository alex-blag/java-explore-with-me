package ru.practicum.emw.main.exception.event;

public class EventNotPublishedException extends RuntimeException {

    public EventNotPublishedException(String message) {
        super(message);
    }

}
