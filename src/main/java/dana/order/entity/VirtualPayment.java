package dana.order.entity;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

public class VirtualPayment {
    private Integer idTransaction;
    private String virtualNumber;
    private Double amount;

    public Integer getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(Integer idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getVirtualNumber() {
        return virtualNumber;
    }

    public void setVirtualNumber(String virtualNumber) {
        this.virtualNumber = virtualNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String toJsonString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public JSONObject toJsonObject(){
        JSONObject obj = new JSONObject();
        obj.put("idTransaction", Integer.valueOf(idTransaction));
        obj.put("virtualNumber", virtualNumber);
        obj.put("amount", Double.valueOf(amount));

        return obj;
    }
}
