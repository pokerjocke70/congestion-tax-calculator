package congestion.calculator.domain;

import congestion.calculator.infrastructure.repository.InMemoryCongestionTaxRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
        var localDateTimes = List.of(LocalDateTime.parse("2021-03-24T00:00:00"));

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


    @Test
    void When_Calculating_Several_Dates_Expect_All_But_One_To_Be_Dropped_Due_To_Holiday() {
        // Given
        var saturday = LocalDateTime.parse("2023-03-25T00:00:00");
        var sunday = LocalDateTime.parse("2023-03-26T00:00:00");
        var monday = LocalDateTime.parse("2023-03-27T00:00:00");
        var localDateTimes = List.of(saturday, sunday, monday);

        // When
        congestionTaxService.calculateTax(new Car(), localDateTimes);

        // Then
        verify(congestionTaxCalculator, times(1)).getTax(argThat(l -> l.size() == 1), eq(60), eq(60), any());
    }

}