package cln.swiggy.notification.serviceImpl.OtherService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.List;

public class ConvertToDateTime {

    public static LocalDateTime listToDateTime(Object dateTimeParts) {
        if (!(dateTimeParts instanceof List)) {
            throw new IllegalArgumentException("Expected a List of date-time parts.");
        }

        List<?> parts = (List<?>) dateTimeParts;

        if (parts.size() < 6) {
            throw new IllegalArgumentException("List must contain at least 6 elements: [year, month, day, hour, minute, second]");
        }

        LocalDateTime dateTime = LocalDateTime.of(
                toInt(parts.get(0)), // year
                toInt(parts.get(1)), // month
                toInt(parts.get(2)), // day
                toInt(parts.get(3)), // hour
                toInt(parts.get(4)), // minute
                toInt(parts.get(5))  // second
        );

        if (parts.size() > 6) {
            dateTime = dateTime.with(ChronoField.NANO_OF_SECOND, toInt(parts.get(6)));
        }

        return dateTime;
    }

    private static int toInt(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        if (value instanceof String) {
            return Integer.parseInt((String) value);
        }
        throw new IllegalArgumentException("List elements must be numbers or numeric strings");
    }
}
