package congestion.calculator.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleException(Exception ex) {
        return ResponseEntity.internalServerError().body(new ErrorResponse(ex.getMessage()));
    }

    private record ErrorResponse(String message) {
    }
}
