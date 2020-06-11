package dana.order.usecase.validate;

import dana.order.usecase.exception.OrderFailedException;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ValidateBuyAVoucher {
    public void check(JSONObject json){
        if(json.get("idVoucher") == null){
            throw new OrderFailedException("Voucher ID cannot be empty.", HttpStatus.BAD_REQUEST);
        }

        if(idVoucherCheck(""+json.get("idVoucher")) == Boolean.FALSE){
            throw new OrderFailedException("Voucher ID is invalid.", HttpStatus.BAD_REQUEST);
        }
    }

    private Boolean idVoucherCheck(String idVoucher){
        String regex = "^[\\d]+$";
        if(!Pattern.matches(regex, idVoucher)){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
