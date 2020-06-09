package dana.order.entity;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

public class TransactionChanges {
    private Integer idTransaction;
    private Double initialPrice;
    private Double finalPrice;
    private Integer idPromo;

    public Integer getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(Integer idTransaction) {
        this.idTransaction = idTransaction;
    }

    public Double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Integer getIdPromo() {
        return idPromo;
    }

    public void setIdPromo(Integer idPromo) {
        this.idPromo = idPromo;
    }

    public String toJsonString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public JSONObject toJsonObject(){
        JSONObject obj = new JSONObject();
        obj.put("idTransaction", Integer.valueOf(idTransaction));
        obj.put("initialPrice", Double.valueOf(initialPrice));
        obj.put("finalPrice", Double.valueOf(finalPrice));
        obj.put("idPromo", Integer.valueOf(idPromo));

        return obj;
    }
}
