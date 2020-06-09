package dana.order.controller;

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
                                                @RequestParam("category") Optional<String> category,
                                                @RequestParam("filter-start-date") Optional<String> startDate,
                                                @RequestParam("filter-end-date") Optional<String> endDate,
                                                @RequestParam("page") Optional<Integer> page){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/api/user/{idUser}/transaction/{idTransaction}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> detailedTransactionHistory(@PathVariable("idUser") String idUser,
                                                        @PathVariable("idTransaction") Integer idTransaction){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
