package dana.order.entity;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

public class PaymentMethod {
    private Integer idPaymentMethod;
    private String methodName;

    public Integer getIdPaymentMethod() {
        return idPaymentMethod;
    }

    public void setIdPaymentMethod(Integer idPaymentMethod) {
        this.idPaymentMethod = idPaymentMethod;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String toJsonString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public JSONObject toJsonObject(){
        JSONObject obj = new JSONObject();
        obj.put("idPaymentMethod", Integer.valueOf(idPaymentMethod));
        obj.put("methodName", methodName);

        return obj;
    }
}
