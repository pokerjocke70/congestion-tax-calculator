package congestion.calculator.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CongestionTaxCalculatorTest {

    @ParameterizedTest
    @MethodSource("getTollFeesValues")
    void getTax(String tollDate, int expectedTollFee) {

        // Given
        var date = LocalDateTime.parse(tollDate);

        // When
        var tollFee = new CongestionTaxCalculator().getTollFee(date, List.of(
                new TaxPeriod(LocalTime.of(6, 0), LocalTime.of(6, 30), 8),
                new TaxPeriod(LocalTime.of(6, 30), LocalTime.of(7, 0), 13),
                new TaxPeriod(LocalTime.of(7, 0), LocalTime.of(8, 0), 18),
                new TaxPeriod(LocalTime.of(8, 0), LocalTime.of(8, 30), 13),
                new TaxPeriod(LocalTime.of(8, 30), LocalTime.of(15, 0), 8),
                new TaxPeriod(LocalTime.of(15, 0), LocalTime.of(15, 30), 13),
                new TaxPeriod(LocalTime.of(15, 30), LocalTime.of(17, 0), 18),
                new TaxPeriod(LocalTime.of(17, 0), LocalTime.of(18, 0), 13),
                new TaxPeriod(LocalTime.of(18, 0), LocalTime.of(18, 30), 8)
        ));

        // Then
        assertThat(tollFee).isEqualTo(expectedTollFee);
    }

    @ParameterizedTest
    @MethodSource("getTaxValues")
    void getTollFee(List<String> dates, int expectedTollFee) {

        // Given
        var tollDates = dates.stream().map(LocalDateTime::parse).toList();

        // When
        var tollFee = new CongestionTaxCalculator().getTax(tollDates, 60, 60, List.of(
                new TaxPeriod(LocalTime.of(6, 0), LocalTime.of(6, 30), 8),
                new TaxPeriod(LocalTime.of(6, 30), LocalTime.of(7, 0), 13),
                new TaxPeriod(LocalTime.of(7, 0), LocalTime.of(8, 0), 18),
                new TaxPeriod(LocalTime.of(8, 0), LocalTime.of(8, 30), 13),
                new TaxPeriod(LocalTime.of(8, 30), LocalTime.of(15, 0), 8),
                new TaxPeriod(LocalTime.of(15, 0), LocalTime.of(15, 30), 13),
                new TaxPeriod(LocalTime.of(15, 30), LocalTime.of(17, 0), 18),
                new TaxPeriod(LocalTime.of(17, 0), LocalTime.of(18, 0), 13),
                new TaxPeriod(LocalTime.of(18, 0), LocalTime.of(18, 30), 8)
        ));

        // Then
        assertThat(tollFee).isEqualTo(expectedTollFee);
    }


    private static Stream<Arguments> getTollFeesValues() {
        return Stream.of(
                Arguments.of("2013-12-02T10:23:00", 8),
                Arguments.of("2013-12-04T06:00:00", 8),
                Arguments.of("2013-12-03T05:59:00", 0),
                Arguments.of("2013-12-06T17:59:00", 13)
        );
    }


    private static Stream<Arguments> getTaxValues() {
        return Stream.of(
                Arguments.of(List.of("2013-12-02T06:23:00", "2013-12-02T07:03:00", "2013-12-02T17:53:00"), 31),
                Arguments.of(List.of("2013-12-02T06:23:00", "2013-12-02T07:03:00", "2013-12-02T07:22:00"), 18),
                Arguments.of(List.of("2013-12-02T06:23:00", "2013-12-02T07:03:00", "2013-12-02T07:22:00", "2013-12-02T16:23:00", "2013-12-02T17:03:00", "2013-12-02T17:22:00", "2013-12-02T18:29:00"), 44),
                Arguments.of(List.of("2013-12-02T06:00:00", "2013-12-02T07:01:00", "2013-12-02T08:02:00", "2013-12-02T09:03:00", "2013-12-02T10:04:00", "2013-12-02T11:05:00", "2013-12-02T12:06:00",
                        "2013-12-02T13:07:00", "2013-12-02T14:08:00", "2013-12-02T15:09:00", "2013-12-02T16:10:00", "2013-12-02T17:11:00", "2013-12-02T18:12:00"), 60)
        );
    }
}