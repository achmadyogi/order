package dana.order.entity;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    private String idUser;
    private Double balance;
    private String phoneNumber;
    private Date createdAt;
    private Date updatedAt;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String toJsonString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public JSONObject toJsonObject(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject obj = new JSONObject();
        obj.put("idUser", idUser);
        obj.put("balance", Double.valueOf(balance));
        obj.put("createAt", sdf.format(createdAt));
        obj.put("updatedAt", sdf.format(updatedAt));

        return obj;
    }
}
