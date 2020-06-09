package dana.order.usecase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OrderFailedException extends ResponseStatusException {

    public OrderFailedException(String message, HttpStatus status){
        super(status, message);
    }
}
