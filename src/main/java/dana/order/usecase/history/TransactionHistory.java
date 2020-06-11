package dana.order.usecase.history;

import dana.order.adapter.wrapper.ResponseWrapper;
import dana.order.entity.TransactionHistoryModel;
import dana.order.usecase.exception.UserException;
import dana.order.usecase.port.HistoryRepository;
import dana.order.usecase.port.UserRepository;
import dana.order.usecase.validate.ValidateTransactionHistory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionHistory {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ValidateTransactionHistory validateTransactionHistory;

    @Autowired
    HistoryRepository historyRepository;

    public JSONObject get(JSONObject json){

        validateTransactionHistory.check(json);

        if (userRepository.doesUserExist(""+json.get("idUser")) == Boolean.FALSE){
            throw new UserException("User is not found.", HttpStatus.NOT_FOUND);
        }

        JSONObject result = historyRepository.getUserHistory(json);

        return ResponseWrapper.wrap("Your transaction history has been collected.", 200, result);
    }
}
