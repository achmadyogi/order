package dana.order.usecase.transaction;

import dana.order.adapter.wrapper.ResponseWrapper;
import dana.order.entity.User;
import dana.order.usecase.exception.OrderFailedException;
import dana.order.usecase.exception.TOPUPFailedException;
import dana.order.usecase.port.DatabaseMapper;
import dana.order.usecase.port.TransactionRepository;
import dana.order.usecase.port.UserRepository;
import dana.order.usecase.validate.ValidateTOPUP;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TOPUP {

    @Autowired
    ValidateTOPUP validateTOPUP;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    DatabaseMapper databaseMapper;

    public JSONObject execute(JSONObject json){

        validateTOPUP.check(json);

        Boolean consistency = Boolean.FALSE;
        Integer counter = 0;
        while (consistency == Boolean.FALSE && counter < 3){
            if (transactionRepository.validateBalanceConsistency(""+json.get("idUser")) == Boolean.FALSE){
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){e.printStackTrace();}
            }else {
                consistency = Boolean.TRUE;
            }
            counter += 1;
        }

        if (consistency == Boolean.FALSE){
            throw new TOPUPFailedException("We cannot process your transaction for now. Please try again later!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (Double.valueOf(""+json.get("amount")) < 10000){
            throw new TOPUPFailedException("TOPUP failed! The minimum TOPUP amount is Rp 10.000,00.", HttpStatus.NOT_ACCEPTABLE);
        }

        User user = databaseMapper.getUserById(""+json.get("idUser"));

        if (user.getBalance() + Double.valueOf(""+json.get("amount")) > 1000000){
            throw new TOPUPFailedException("TOPUP failed! You have reached your maximum balance amount.", HttpStatus.NOT_ACCEPTABLE);
        }

        if (transactionRepository.thirdPartyCheck(""+json.get("virtualNumber")) == Boolean.FALSE){
            throw new TOPUPFailedException("The merchant is currently not available for balance TOPUP.", HttpStatus.NOT_FOUND);
        }

        transactionRepository.TOPUPBalance(""+json.get("idUser"), Double.valueOf(""+json.get("amount")),
                Integer.valueOf(""+json.get("virtualNumber")), Integer.valueOf(""+json.get("partyCode")));

        return ResponseWrapper.wrap("Your TOPUP is successful!", 200, null);
    }
}
