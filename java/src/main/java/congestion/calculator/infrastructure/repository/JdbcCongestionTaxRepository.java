package congestion.calculator.infrastructure.repository;

import congestion.calculator.domain.CongestionTaxRepository;
import congestion.calculator.domain.TaxPeriod;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Profile("jdbc")
public class JdbcCongestionTaxRepository implements CongestionTaxRepository {

    @Override
    public boolean isFreeVehicle(String vehicleType) {
        return false;
    }

    @Override
    public boolean isFreeDate(LocalDate date) {
        return false;
    }

    @Override
    public List<TaxPeriod> getTaxPeriods() {
        return List.of();
    }

    @Override
    public int getMaxFee() {
        return 0;
    }

    @Override
    public int bucketDuration() {
        return 0;
    }
}
