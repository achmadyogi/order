package dana.order.usecase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PaymentFailedException extends ResponseStatusException {

    public PaymentFailedException(String message, HttpStatus status){
        super(status, message);
    }
}
