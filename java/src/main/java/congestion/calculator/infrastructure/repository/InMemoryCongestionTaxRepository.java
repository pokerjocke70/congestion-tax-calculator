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
    private final List<String> freeDates = List.of(
            "2013-01-01",
            "2013-03-28",
            "2013-03-29",
            "2013-04-01",
            "2013-04-30",
            "2013-05-01",
            "2013-05-08",
            "2013-05-09",
            "2013-06-05",
            "2013-06-06",
            "2013-06-21",
            "2013-07-01",
            "2013-07-02",
            "2013-07-03",
            "2013-07-04",
            "2013-07-05",
            "2013-07-06",
            "2013-07-07",
            "2013-07-08",
            "2013-07-09",
            "2013-07-10",
            "2013-07-11",
            "2013-07-12",
            "2013-07-13",
            "2013-07-14",
            "2013-07-15",
            "2013-07-16",
            "2013-07-17",
            "2013-07-18",
            "2013-07-19",
            "2013-07-20",
            "2013-07-21",
            "2013-07-22",
            "2013-07-23",
            "2013-07-24",
            "2013-07-25",
            "2013-07-26",
            "2013-07-27",
            "2013-07-28",
            "2013-07-29",
            "2013-07-30",
            "2013-07-31",
            "2013-11-01",
            "2013-12-24",
            "2013-12-25",
            "2013-12-26",
            "2013-12-31"
    );

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

        return freeDates.contains(date.toString());
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
