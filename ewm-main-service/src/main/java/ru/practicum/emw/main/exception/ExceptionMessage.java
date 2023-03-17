package ru.practicum.emw.main.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessage {

    public static final String USER_NOT_FOUND = "User not found";

    public static final String CATEGORY_NOT_FOUND = "Category not found";

    public static final String CATEGORY_HAS_ASSOCIATED_EVENT = "Category has associated event";

    public static final String EVENT_NOT_FOUND = "Event not found";

    public static final String EVENT_DATE_TOO_EARLY = "Event date too early";

    public static final String EVENT_NOT_PENDING = "Event not pending";

    public static final String EVENT_NOT_PUBLISHED = "Event not published";

    public static final String EVENT_ALREADY_PUBLISHED = "Event already published";

    public static final String EVENT_PARTICIPANT_LIMIT_REACHED = "Event participant limit reached";

    public static final String EVENT_CANNOT_BE_UPDATED = "Event cannot be updated";

    public static final String REQUEST_NOT_FOUND = "Request not found";

    public static final String REQUEST_NOT_PENDING = "Request not pending";

    public static final String REQUEST_ALREADY_CREATED = "Request already created";

    public static final String REQUESTER_OWNS_EVENT = "Requester owns event";

    public static final String COMPILATION_NOT_FOUND = "Compilation not found";

    public static final String LOCATION_NOT_FOUND = "Location not found";

    public static final String LOCATION_HAS_ASSOCIATED_EVENT = "Location has associated event";

}
