package dana.order.entity;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private Integer idTransaction;
    private String idUser;
    private Double amount;
    private Date transactionDate;
    private Boolean isCredit;
    private Integer idTransactionStatus;
    private Integer idPaymentMethod;
    private Integer idService;
    private Integer idGoods;
    private Date createdAt;
    private Date updatedAt;

    public Integer getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(Integer idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Boolean getIsCredit() {
        return isCredit;
    }

    public void setIsCredit(Boolean isCredit) {
        this.isCredit = isCredit;
    }

    public Integer getIdTransactionStatus() {
        return idTransactionStatus;
    }

    public void setIdTransactionStatus(Integer idTransactionStatus) {
        this.idTransactionStatus = idTransactionStatus;
    }

    public Integer getIdPaymentMethod() {
        return idPaymentMethod;
    }

    public void setIdPaymentMethod(Integer idPaymentMethod) {
        this.idPaymentMethod = idPaymentMethod;
    }

    public Integer getIdService() {
        return idService;
    }

    public void setIdService(Integer idService) {
        this.idService = idService;
    }

    public Integer getIdGoods() {
        return idGoods;
    }

    public void setIdGoods(Integer idGoods) {
        this.idGoods = idGoods;
    }

    public String toJsonString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    public JSONObject toJsonObject(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject obj = new JSONObject();
        obj.put("idTransaction", Integer.valueOf(idTransaction));
        obj.put("idUser", Integer.valueOf(idUser));
        obj.put("amount", Double.valueOf(amount));
        obj.put("transactionDate", transactionDate);
        obj.put("isCredit", Boolean.valueOf(isCredit));
        obj.put("idTransactionStatus", Integer.valueOf(idTransactionStatus));
        obj.put("idPaymentMethod", Integer.valueOf(idPaymentMethod));
        obj.put("idService", Integer.valueOf(idService));
        obj.put("idGoods", Integer.valueOf(idGoods));
        obj.put("createAt", sdf.format(createdAt));
        obj.put("updatedAt", sdf.format(updatedAt));

        return obj;
    }
}
