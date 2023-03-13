package ru.practicum.emw.main.exception.event;

public class EventParticipantLimitReachedException extends RuntimeException {

    public EventParticipantLimitReachedException(String message) {
        super(message);
    }

}
