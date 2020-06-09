package dana.order.usecase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserException extends ResponseStatusException {

    public UserException(String message, HttpStatus status){
        super(status, message);
    }

}
