package java15.projectrestaurant.util;

import java.time.LocalDate;
import java.util.function.Function;

public class DateUtils {
    /**
     * Resolves a date from a request object using a mapper function.
     * If the request or the mapped date is null, it defaults to LocalDate.now().
     *
     * @param <T> the type of the request object
     * @param request the request object
     * @param dateMapper a function mapping the request to a LocalDate
     * @return the resolved LocalDate, defaulting to LocalDate.now()
     */
    public static <T> LocalDate resolveDate(T request, Function<T, LocalDate> dateMapper) {
        return (request == null || dateMapper.apply(request) == null) ? LocalDate.now() : dateMapper.apply(request);
    }
}