package congestion.calculator.domain;

import java.time.LocalDate;
import java.util.List;

public interface CongestionTaxRepository {

    boolean isFreeVehicle(String vehicleType);

    boolean isFreeDate(LocalDate date);

    List<TaxPeriod> getTaxPeriods();

    int getMaxFee();

    int bucketDuration();
}
