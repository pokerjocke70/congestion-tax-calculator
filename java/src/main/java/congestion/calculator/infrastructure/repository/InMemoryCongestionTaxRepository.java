package congestion.calculator.infrastructure.repository;

import congestion.calculator.domain.CongestionTaxRepository;
import congestion.calculator.domain.TaxPeriod;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Repository
@Profile("!jdbc")
public class InMemoryCongestionTaxRepository implements CongestionTaxRepository {

    static final List<String> tollFreeVehicles = List.of(
            "Motorcycle",
            "Tractor",
            "Emergency",
            "Diplomat",
            "Foreign",
            "Military");

    @Override
    public boolean isFreeVehicle(String vehicleType) {
        return tollFreeVehicles.contains(vehicleType);
    }

    @Override
    public boolean isFreeDate(LocalDate date) {
        var day = date.getDayOfWeek();

        if (DayOfWeek.SUNDAY.equals(day) || DayOfWeek.SATURDAY.equals(day)) {
            return true;
        }

        if (date.getYear() == 2013) {
            var month = date.getMonthValue();
            var dayOfMonth = date.getDayOfMonth();
            return month == 1 && dayOfMonth == 1 ||
                    month == 3 && (dayOfMonth == 28 || dayOfMonth == 29) ||
                    month == 4 && (dayOfMonth == 1 || dayOfMonth == 30) ||
                    month == 5 && (dayOfMonth == 1 || dayOfMonth == 8 || dayOfMonth == 9) ||
                    month == 6 && (dayOfMonth == 5 || dayOfMonth == 6 || dayOfMonth == 21) ||
                    month == 7 ||
                    month == 11 && dayOfMonth == 1 ||
                    month == 12 && (dayOfMonth == 24 || dayOfMonth == 25 || dayOfMonth == 26 || dayOfMonth == 31);
        }
        return false;
    }

    @Override
    public List<TaxPeriod> getTaxPeriods() {
        return List.of(
                new TaxPeriod(LocalTime.of(6, 0), LocalTime.of(6, 30), 8),
                new TaxPeriod(LocalTime.of(6, 30), LocalTime.of(7, 0), 13),
                new TaxPeriod(LocalTime.of(7, 0), LocalTime.of(8, 0), 18),
                new TaxPeriod(LocalTime.of(8, 0), LocalTime.of(8, 30), 13),
                new TaxPeriod(LocalTime.of(8, 30), LocalTime.of(15, 0), 8),
                new TaxPeriod(LocalTime.of(15, 0), LocalTime.of(15, 30), 13),
                new TaxPeriod(LocalTime.of(15, 30), LocalTime.of(17, 0), 18),
                new TaxPeriod(LocalTime.of(17, 0), LocalTime.of(18, 0), 13),
                new TaxPeriod(LocalTime.of(18, 0), LocalTime.of(18, 30), 8)
        );
    }

    @Override
    public int getMaxFee() {
        return 60;
    }

    @Override
    public int bucketDuration() {
        return 60;
    }
}
