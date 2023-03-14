package ru.practicum.emw.main.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.emw.main.event.entity.Event;
import ru.practicum.ewm.stats.common.dto.EndpointHitPostDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Collections.min;
import static java.util.Comparator.comparing;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtils {

    public static final String EWM_MAIN_SERVICE = "ewm-main-service";

    public static final String EVENTS_ENDPOINT = "/events";

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    public static final String DEFAULT_FROM = "0";

    public static final String DEFAULT_SIZE = "10";

    public static final int ONE_HOUR_BEFORE_EARLY_START = 1;

    public static final int TWO_HOURS_BEFORE_EARLY_START = 2;

    public static Pageable getPageRequestUnsorted(int from, int size) {
        int page = from / size;
        return PageRequest.of(page, size, Sort.unsorted());
    }

    public static <T> Page<T> toPage(List<T> list, Pageable pageable) {
        int size = list.size();
        int from = Math.min((int) pageable.getOffset(), size);
        int to = Math.min(from + pageable.getPageSize(), size);
        return new PageImpl<>(list.subList(from, to), pageable, size);
    }

    public static <T> List<T> toList(Iterable<T> iterable) {
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public static String buildEventEndpointUri(long eventId) {
        return EVENTS_ENDPOINT + "/" + eventId;
    }

    public static EndpointHitPostDto buildMainServiceEndpointHitPostDto(String endpointUri, String remoteIp) {
        return new EndpointHitPostDto(
                null,
                EWM_MAIN_SERVICE,
                endpointUri,
                remoteIp,
                LocalDateTime.now()
        );
    }

    public static LocalDateTime getEarliestEventDate(List<Event> events) {
        return !events.isEmpty()
                ? min(events, comparing(Event::getEventDate)).getEventDate()
                : LocalDateTime.now();
    }

    public static Boolean toBoolean(String s) {
        Boolean b;

        if (s == null || "null".equalsIgnoreCase(s)) {
            b = null;

        } else if ("true".equalsIgnoreCase(s)) {
            b = true;

        } else if ("false".equalsIgnoreCase(s)) {
            b = false;

        } else {
            throw new IllegalArgumentException(s);
        }

        return b;
    }

    public static String toText(String s) {
        String t;

        if (s == null || "null".equalsIgnoreCase(s)) {
            t = null;
        } else {
            t = s;
        }

        return t;
    }

}
