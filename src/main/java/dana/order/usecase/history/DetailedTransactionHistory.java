package dana.order.usecase.history;

import dana.order.adapter.wrapper.ResponseWrapper;
import dana.order.entity.*;
import dana.order.usecase.exception.PaymentFailedException;
import dana.order.usecase.exception.UserException;
import dana.order.usecase.port.DatabaseMapper;
import dana.order.usecase.port.HistoryRepository;
import dana.order.usecase.port.TransactionRepository;
import dana.order.usecase.port.UserRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class DetailedTransactionHistory {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DatabaseMapper databaseMapper;

    @Autowired
    HistoryRepository historyRepository;

    public JSONObject get(JSONObject json){

        if (userRepository.doesUserExist(""+json.get("idUser")) == Boolean.FALSE){
            throw new UserException("The user is not found.", HttpStatus.NOT_FOUND);
        }

        Transaction transaction = databaseMapper.getTransactionById(Integer.valueOf(""+json.get("idTransaction")));

        if (transaction == null){
            throw new PaymentFailedException("The transaction is not found.", HttpStatus.NOT_FOUND);
        }

        if (!transaction.getIdUser().equals(""+json.get("idUser"))){
            throw new PaymentFailedException("The transaction does not belong to this user.", HttpStatus.BAD_REQUEST);
        }

        JSONObject transactionDetails = historyRepository.getUserDetailedHistory(transaction.getIdTransaction());

        return ResponseWrapper.wrap("Your detailed transaction history has been collected", 200, transactionDetails);
    }
}
