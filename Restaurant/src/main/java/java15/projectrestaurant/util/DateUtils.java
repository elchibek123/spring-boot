package java15.projectrestaurant.util;

import java.time.LocalDate;
import java.util.function.Function;

public class DateUtils {
    public static <T> LocalDate resolveDate(T request, Function<T, LocalDate> dateMapper) {
        return (request == null || dateMapper.apply(request) == null) ? LocalDate.now() : dateMapper.apply(request);
    }
}