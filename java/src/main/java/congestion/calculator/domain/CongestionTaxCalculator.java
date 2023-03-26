package congestion.calculator.domain;

import jakarta.inject.Named;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Named
public class CongestionTaxCalculator {

    private record DateAndFee(LocalDateTime date, int fee) {
    }


    public int getTax(List<LocalDateTime> dates, int maxFee, int bucketDuration, List<TaxPeriod> billablePeriods) {
        if (dates == null || dates.isEmpty() || billablePeriods == null || billablePeriods.isEmpty()) {
            return 0;
        }

        var dateAndFees = dates.stream()
                .sorted()
                .map(date -> new DateAndFee(date, getTollFee(date, billablePeriods)))
                .toList();

        return Math.min(maxFee, getDateAndFees(dateAndFees, bucketDuration).stream()
                .mapToInt(dateAndFee -> dateAndFee.fee)
                .sum());
    }

    /**
     * Returns the toll fee for a vehicle at a specific date and time
     */
    int getTollFee(LocalDateTime zonedDateTime, List<TaxPeriod> billablePeriods) {

        return billablePeriods.stream()
                .filter(billablePeriod -> billablePeriod.isWithin(zonedDateTime.toLocalTime()))
                .findFirst()
                .map(TaxPeriod::fee)
                .orElse(0);
    }

    /**
     * Return a list of {@link DateAndFee} where the fee is the highest fee for each bucket duration
     */
    private static List<DateAndFee> getDateAndFees(List<DateAndFee> dateAndFees, int bucketDuration) {
        LinkedList<DateAndFee> filteredDateAndFees = new LinkedList<>();
        // Keep track of which bucket a date belongs to. The bucket starts with the first item. The first item not in the bucketDuration from the first one belongs to the next billable bucket.
        for (var dateAndFee : dateAndFees) {
            if (filteredDateAndFees.isEmpty()) {
                filteredDateAndFees.add(dateAndFee);
            } else {
                // Compare with last value in list
                var lastDateAndFee = filteredDateAndFees.peekLast();
                var diffInMinutes = Duration.between(lastDateAndFee.date, dateAndFee.date).getSeconds() / 60;
                // Check if we are in same bucket
                if (diffInMinutes <= bucketDuration) {
                    if (dateAndFee.fee > lastDateAndFee.fee) {
                        // Replace last value with new value keeping the old date to keep track of the start of this bucket
                        filteredDateAndFees.removeLast();
                        filteredDateAndFees.add(new DateAndFee(lastDateAndFee.date, dateAndFee.fee));
                    }
                } else {
                    // Outside current bucket, add to list
                    filteredDateAndFees.add(dateAndFee);
                }
            }
        }
        return Collections.unmodifiableList(filteredDateAndFees);
    }
}
