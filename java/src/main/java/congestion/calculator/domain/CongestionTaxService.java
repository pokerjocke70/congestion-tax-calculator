package congestion.calculator.domain;

import jakarta.annotation.Nonnull;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Named
public class CongestionTaxService {

    private final CongestionTaxCalculator congestionTaxCalculator;

    private final CongestionTaxRepository congestionTaxRepository;

    @Inject
    public CongestionTaxService(CongestionTaxCalculator congestionTaxCalculator, CongestionTaxRepository congestionTaxRepository) {
        this.congestionTaxCalculator = congestionTaxCalculator;
        this.congestionTaxRepository = congestionTaxRepository;
    }

    public int calculateTax(@Nonnull Vehicle vehicle, @Nonnull List<LocalDateTime> dates) {
        Objects.requireNonNull(vehicle, "Vehicle cannot be null");
        Objects.requireNonNull(dates, "dates cannot be null");
        if (congestionTaxRepository.isFreeVehicle(vehicle.getVehicleType())) {
            return 0;
        }
        var filteredDates = dates.stream()
                .filter(localDateTime -> !isFreeDate(localDateTime.toLocalDate()))
                .toList();

        return congestionTaxCalculator.getTax(filteredDates, congestionTaxRepository.getMaxFee(), congestionTaxRepository.bucketDuration(), congestionTaxRepository.getTaxPeriods());
    }

    private boolean isFreeDate(LocalDate date) {
        return congestionTaxRepository.isFreeDate(date);
    }
}
