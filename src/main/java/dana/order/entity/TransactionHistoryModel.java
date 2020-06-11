package dana.order.entity;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionHistoryModel {
    private Integer idTransaction;
    private Double amount;
    private String transactionDate;
    private String status;
    private String paymentMethod;
    private String service;
    private String voucherName;
    private String merchantName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
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
        obj.put("idTransaction", Integer.valueOf(idTransaction));
        obj.put("amount", Double.valueOf(amount));
        obj.put("transactionDate", sdf.format(transactionDate));
        obj.put("status", status);
        obj.put("paymentMethod", paymentMethod);
        obj.put("service", service);
        obj.put("voucherName", voucherName);
        obj.put("merchantName", merchantName);
        obj.put("createAt", createdAt);
        obj.put("updatedAt", updatedAt);

        return obj;
    }
}
