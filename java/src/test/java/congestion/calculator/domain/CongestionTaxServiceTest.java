package congestion.calculator.domain;

import congestion.calculator.infrastructure.repository.InMemoryCongestionTaxRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CongestionTaxServiceTest {

    @Mock
    private CongestionTaxCalculator congestionTaxCalculator;

    private CongestionTaxRepository congestionTaxRepository = new InMemoryCongestionTaxRepository();

    private CongestionTaxService congestionTaxService;

    @BeforeEach
    void setUp() {
        congestionTaxService = new CongestionTaxService(congestionTaxCalculator, congestionTaxRepository);
    }

    @Test
    void When_Calculating_On_Excluded_Vehicle_Expect_Sum_to_Be_Zero() {
        // Given
        var  localDateTimes = List.of(LocalDateTime.parse("2021-03-24T00:00:00"));

        // When
        var actual = congestionTaxService.calculateTax(new MotorCycle(), localDateTimes);

        // Then
        assertEquals(0, actual);
    }

    @Test
    void When_Calculating_On_A_Sunday_Expect_Sum_to_Be_Zero() {
        // Given
        var localDateTimes = List.of(LocalDateTime.parse("2023-03-26T00:00:00"));

        // When
        var actual = congestionTaxService.calculateTax(new Car(), localDateTimes);

        // Then
        assertThat(actual).isEqualTo(0);
    }

}