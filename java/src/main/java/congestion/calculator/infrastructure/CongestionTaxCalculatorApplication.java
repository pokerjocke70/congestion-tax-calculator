package congestion.calculator.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "congestion.calculator")
public class CongestionTaxCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CongestionTaxCalculatorApplication.class, args);
	}

}
