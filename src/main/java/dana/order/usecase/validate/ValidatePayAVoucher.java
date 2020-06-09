package dana.order.usecase.validate;

import dana.order.usecase.exception.OrderFailedException;
import dana.order.usecase.exception.PaymentFailedException;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ValidatePayAVoucher {
    public void check(JSONObject json){
        if(json.get("idTransaction") == null){
            throw new PaymentFailedException("Voucher ID cannot be empty.", HttpStatus.BAD_REQUEST);
        }

        if(idTransactionCheck(""+json.get("idTransaction")) == Boolean.FALSE){
            throw new PaymentFailedException("Invalid transaction ID.", HttpStatus.BAD_REQUEST);
        }
    }

    private Boolean idTransactionCheck(String idTransaction){
        String regex = "^[\\d]+$";
        if(!Pattern.matches(regex, idTransaction)){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
