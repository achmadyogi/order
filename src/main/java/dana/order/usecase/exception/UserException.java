package dana.order.usecase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserException extends ResponseStatusException {
    private Integer dealsStatus;

    public UserException(String message, HttpStatus status){
        super(status, message);
    }

    public Integer getDealsStatus() {
        return dealsStatus;
    }

    public void setDealsStatus(Integer dealsStatus) {
        this.dealsStatus = dealsStatus;
    }
}
