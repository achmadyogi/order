package dana.order.usecase.validate;

import dana.order.usecase.exception.TOPUPFailedException;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ValidateTOPUP {
    public void check(JSONObject json){
        if (json.get("virtualNumber") == null){
            throw new TOPUPFailedException("Virtual Number cannot be empty.", HttpStatus.BAD_REQUEST);
        }

        if (json.get("amount") == null){
            throw new TOPUPFailedException("TOPUP amount cannot be empty.", HttpStatus.BAD_REQUEST);
        }

        if (virtualNumberCheck(""+json.get("virtualNumber")) == Boolean.FALSE){
            throw new TOPUPFailedException("Virtual Number is invalid.", HttpStatus.BAD_REQUEST);
        }

        if (amountCheck(""+json.get("amount")) == Boolean.FALSE){
            throw new TOPUPFailedException("Amount is invalid.", HttpStatus.BAD_REQUEST);
        }
    }

    public Boolean virtualNumberCheck(String va){
        String regex = "^[\\d]{15,16}$";
        if(!Pattern.matches(regex, va)){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Boolean amountCheck(String amount){
        String regex = "^(?=.*[\\d])(?!.*[\\D]).+$|^[\\d][.][\\d]+$";
        if(!Pattern.matches(regex, amount)){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
