package congestion.calculator.domain;

import java.time.LocalTime;

public record TaxPeriod(LocalTime start, LocalTime end, int fee) {

    public boolean isWithin(LocalTime time) {
        return start.equals(time) || time.isAfter(start) && time.isBefore(end);
    }
}
