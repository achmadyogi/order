package dana.order.usecase.port;

import dana.order.entity.TransactionHistoryModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public interface HistoryRepository {
    JSONObject getUserHistory(JSONObject json);
    JSONObject getUserDetailedHistory(Integer idTransaction);
}
