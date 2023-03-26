package congestion.calculator.domain;

import jakarta.annotation.Nonnull;
import jakarta.inject.Inject;
import jakarta.inject.Named;

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

    public int calculateTax(@Nonnull Vehicle vehicle, @Nonnull List<LocalDateTime> localDateTimes) {
        Objects.requireNonNull(vehicle, "Vehicle cannot be null");
        Objects.requireNonNull(localDateTimes, "dates cannot be null");
        if (congestionTaxRepository.isFreeVehicle(vehicle.getVehicleType()) || congestionTaxRepository.isFreeDate(localDateTimes.stream().findFirst().orElseThrow().toLocalDate())) {
            return 0;
        }
        return congestionTaxCalculator.getTax(localDateTimes, congestionTaxRepository.getMaxFee(), congestionTaxRepository.bucketDuration(), congestionTaxRepository.getTaxPeriods());
    }
}
