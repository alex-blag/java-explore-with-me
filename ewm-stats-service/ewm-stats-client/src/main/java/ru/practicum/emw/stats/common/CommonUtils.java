package ru.practicum.emw.stats.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtils {

    public static final String EWM_STATS_SERVER_URI_PROPERTY = "${ewm.stats.server.uri}";

    public static final String EWM_STATS_HIT_ENDPOINT = "/hit";

    public static final String EWM_STATS_STATS_ENDPOINT = "/stats";

}
