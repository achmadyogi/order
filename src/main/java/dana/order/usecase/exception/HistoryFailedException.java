package dana.order.usecase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class HistoryFailedException extends ResponseStatusException {

    public HistoryFailedException(String message, HttpStatus status){
        super(status, message);
    }
}
