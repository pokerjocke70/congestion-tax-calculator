package congestion.calculator.application;

import congestion.calculator.domain.Car;
import congestion.calculator.domain.CongestionTaxService;
import congestion.calculator.domain.MotorCycle;
import congestion.calculator.domain.Vehicle;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class CongestionTaxController {

    private final CongestionTaxService congestionTaxService;

    public CongestionTaxController(CongestionTaxService congestionTaxService) {
        this.congestionTaxService = congestionTaxService;
    }

    @GetMapping(value = "/calculate", produces = APPLICATION_JSON_VALUE)
    public CongestionTaxResponse calculateTax(CongestionTaxRequest request) {
        return new CongestionTaxResponse(congestionTaxService.calculateTax(toVehicle(request.vehicle()), request.dates()));
    }

    private static Vehicle toVehicle(String vehicle) {
        return vehicle == null ? null : switch (vehicle.toLowerCase()) {
            case "car" -> new Car();
            case "motorcycle" -> new MotorCycle();
            default -> throw new IllegalArgumentException("Unknown vehicle type: " + vehicle);
        };
    }

    private record CongestionTaxResponse(int sum) {
    }

    private record CongestionTaxRequest(String vehicle, List<LocalDateTime> dates) {
    }
}
