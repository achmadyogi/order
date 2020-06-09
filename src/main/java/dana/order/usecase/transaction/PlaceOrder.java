package dana.order.usecase.transaction;

import dana.order.adapter.wrapper.ResponseWrapper;
import dana.order.entity.Transaction;
import dana.order.usecase.exception.OrderFailedException;
import dana.order.usecase.exception.UserException;
import dana.order.usecase.port.DatabaseMapper;
import dana.order.usecase.port.TransactionRepository;
import dana.order.usecase.port.UserRepository;
import dana.order.usecase.port.VoucherRepository;
import dana.order.usecase.validate.ValidateBuyAVoucher;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PlaceOrder{

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ValidateBuyAVoucher validateBuyAVoucher;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    DatabaseMapper databaseMapper;

    public JSONObject buyAVoucher(JSONObject json){

        validateBuyAVoucher.check(json);

        if (userRepository.doesUserExist(""+json.get("idUser")) == Boolean.FALSE){
            throw new UserException("User is not found.", HttpStatus.NOT_FOUND);
        }

        if (voucherRepository.validateExpiration(Integer.valueOf(""+json.get("idVoucher"))) == Boolean.FALSE){
            throw new OrderFailedException("The voucher is currently not available.", HttpStatus.NOT_FOUND);
        }

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
            throw new OrderFailedException("We cannot process your transaction for now. Please try again later!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        consistency = Boolean.FALSE; counter = 0;
        while (consistency == Boolean.FALSE && counter < 3){
            if (transactionRepository.validateVoucherConsistency(Integer.valueOf(""+json.get("idVoucher"))) == Boolean.FALSE){
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){e.printStackTrace();}
            }else{
                consistency = Boolean.TRUE;
            }
            counter += 1;
        }

        if (consistency == Boolean.FALSE){
            throw new OrderFailedException("We cannot process your transaction for now. Please try again later!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (voucherRepository.validateQuantity(Integer.valueOf(""+json.get("idVoucher"))) == Boolean.FALSE){
            throw new OrderFailedException("The voucher is currently out of stock.", HttpStatus.NOT_FOUND);
        }

        voucherRepository.insertNewOrder(""+json.get("idUser"), Integer.valueOf(""+json.get("idVoucher")));
        voucherRepository.broadcastNewOrder(""+json.get("idUser"), Integer.valueOf(""+json.get("idVoucher")));

        Transaction transaction = databaseMapper.getLatestUserInProgressTransaction(""+json.get("idUser"));
        JSONObject result = new JSONObject();
        result.put("idTransaction", transaction.getIdTransaction());

        return ResponseWrapper.wrap("A new transaction has been created!", 201, result);
    }
}
