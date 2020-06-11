package dana.order.controller;

import dana.order.usecase.history.DetailedTransactionHistory;
import dana.order.usecase.history.TransactionHistory;
import dana.order.usecase.transaction.Payment;
import dana.order.usecase.transaction.PlaceOrder;
import dana.order.usecase.transaction.TOPUP;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class mobileController {

    @Autowired
    PlaceOrder placeOrder;

    @Autowired
    Payment payment;

    @Autowired
    TOPUP topup;

    @Autowired
    TransactionHistory transactionHistory;

    @Autowired
    DetailedTransactionHistory detailedTransactionHistory;

    @PostMapping(value = "/api/user/{idUser}/transaction/voucher", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> placeOrder(@PathVariable("idUser") String idUser, @RequestBody JSONObject json){
        json.put("idUser", idUser);
        JSONObject result = placeOrder.buyAVoucher(json);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/user/{idUser}/transaction/voucher", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> payOrder(@PathVariable("idUser") String idUser,
                                      @RequestBody JSONObject json){
        json.put("idUser", idUser);
        JSONObject result = payment.payAVoucher(json);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/api/user/{idUser}/transaction/topup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> TOPUP(@PathVariable("idUser") Integer idUser, @RequestBody JSONObject json){
        json.put("idUser", idUser);
        JSONObject result = topup.execute(json);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/api/user/{idUser}/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> transactionHistory(@PathVariable("idUser") String idUser,
                                                @RequestParam(value = "category", required = false) String category,
                                                @RequestParam(value = "filter-start-date", required = false) String startDate,
                                                @RequestParam(value = "filter-end-date", required = false) String endDate,
                                                @RequestParam(value = "page", required = false) Integer page){
        JSONObject json = new JSONObject();
        json.put("idUser", idUser);
        json.put("category", category);
        json.put("startDate", startDate);
        json.put("endDate", endDate);
        json.put("page", page);
        JSONObject result = transactionHistory.get(json);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/api/user/{idUser}/transaction/{idTransaction}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> detailedTransactionHistory(@PathVariable("idUser") String idUser,
                                                        @PathVariable("idTransaction") Integer idTransaction){
        JSONObject json = new JSONObject();
        json.put("idUser", idUser);
        json.put("idTransaction", idTransaction);
        JSONObject result = detailedTransactionHistory.get(json);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
