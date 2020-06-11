package dana.order.usecase.history;

import dana.order.adapter.wrapper.ResponseWrapper;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class DetailedTransactionHistory {
    public JSONObject get(JSONObject json){


        return ResponseWrapper.wrap("", 200, null);
    }
}
