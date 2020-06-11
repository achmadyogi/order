package dana.order.entity;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionHistoryModel {
    private Integer idTransaction;
    private Double amount;
    private String transactionDate;
    private String transactionStatus;
    private String paymentMethod;
    private String serviceName;
    private Boolean isCredit;
    private Date createdAt;
    private Date updatedAt;

    public Integer getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(Integer idTransaction) {
        this.idTransaction = idTransaction;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Boolean getIsCredit() {
        return isCredit;
    }

    public void setIsCredit(Boolean isCredit) {
        this.isCredit = isCredit;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getCreatedAt() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(createdAt);
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(updatedAt);
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
        obj.put("idTransaction", Integer.valueOf(idTransaction));
        obj.put("amount", Double.valueOf(amount));
        obj.put("isCredit", Boolean.valueOf(isCredit));
        obj.put("transactionDate", sdf.format(transactionDate));
        obj.put("transactionStatus", transactionStatus);
        obj.put("paymentMethod", paymentMethod);
        obj.put("serviceName", serviceName);
        obj.put("createAt", createdAt);
        obj.put("updatedAt", updatedAt);

        return obj;
    }
}
