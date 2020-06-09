package dana.order.usecase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TOPUPFailedException extends ResponseStatusException {

    public TOPUPFailedException(String message, HttpStatus status){
        super(status, message);
    }
}
