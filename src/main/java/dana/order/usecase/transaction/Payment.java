package dana.order.usecase.transaction;

import dana.order.adapter.wrapper.ResponseWrapper;
import dana.order.entity.Transaction;
import dana.order.entity.User;
import dana.order.usecase.exception.OrderFailedException;
import dana.order.usecase.exception.PaymentFailedException;
import dana.order.usecase.exception.UserException;
import dana.order.usecase.port.DatabaseMapper;
import dana.order.usecase.port.TransactionRepository;
import dana.order.usecase.port.UserRepository;
import dana.order.usecase.port.VoucherRepository;
import dana.order.usecase.validate.ValidatePayAVoucher;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class Payment {

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ValidatePayAVoucher validatePayAVoucher;

    @Autowired
    DatabaseMapper databaseMapper;

    @Autowired
    TransactionRepository transactionRepository;

    public JSONObject payAVoucher(JSONObject json){

        validatePayAVoucher.check(json);

        if (userRepository.doesUserExist(""+json.get("idUser")) == Boolean.FALSE){
            throw new UserException("User is not found.", HttpStatus.NOT_FOUND);
        }

        Transaction transaction = databaseMapper.getTransactionById(Integer.valueOf(""+json.get("idTransaction")));

        if (transaction == null){
            throw new PaymentFailedException("The transaction is not found.", HttpStatus.NOT_FOUND);
        }

        if (!transaction.getIdUser().equals(""+json.get("idUser"))){
            throw new PaymentFailedException("The transaction does not belong to this user.", HttpStatus.BAD_REQUEST);
        }

        if (transaction.getIdTransactionStatus() != 4){
            throw new PaymentFailedException("There is nothing to do with an already finished transaction.", HttpStatus.NOT_ACCEPTABLE);
        }

        if (voucherRepository.validateExpiration(transaction.getIdGoods()) == Boolean.FALSE){
            databaseMapper.fallingATransaction(""+json.get("idUser"), Integer.valueOf(""+json.get("idTransaction")));
            throw new PaymentFailedException("The payment has failed! The voucher is currently not available.", HttpStatus.NOT_FOUND);
        }

        if (transactionRepository.checkATransactionExpiration(Integer.valueOf(""+json.get("idTransaction"))) == Boolean.FALSE){
            databaseMapper.fallingATransaction(""+json.get("idUser"), Integer.valueOf(""+json.get("idTransaction")));
            throw new PaymentFailedException("This transaction has expired.", HttpStatus.NOT_ACCEPTABLE);
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
            if (transactionRepository.validateVoucherConsistency(transaction.getIdGoods()) == Boolean.FALSE){
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

        if (voucherRepository.validateQuantity(transaction.getIdGoods()) == Boolean.FALSE){
            databaseMapper.fallingATransaction(""+json.get("idUser"), Integer.valueOf(""+json.get("idTransaction")));
            throw new PaymentFailedException("The payment has failed! The voucher is currently out of stock.", HttpStatus.NOT_FOUND);
        }

        User user = databaseMapper.getUserById(""+json.get("idUser"));

        if (user.getBalance() - transaction.getAmount() < 0){
            throw new PaymentFailedException("You have not enough balance to continue this transaction.", HttpStatus.PAYMENT_REQUIRED);
        }

        transactionRepository.setFinishATransaction(Integer.valueOf(""+json.get("idTransaction")));
        transactionRepository.broadcastATransaction(Integer.valueOf(""+json.get("idTransaction")));

        return ResponseWrapper.wrap("Your payment is successful!", 200, null);
    }
}
