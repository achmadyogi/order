package dana.order.entity;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Voucher {
    private Integer idVoucher;
    private Integer idMerchant;
    private String voucherName;
    private Double voucherPrice;
    private Double maxDiscountPrice;
    private Double discount;
    private Integer voucherQuantity;
    private String expiredDate;
    private Boolean isActive;
    private Date createdAt;
    private Date updatedAt;

    public Integer getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(Integer idVoucher) {
        this.idVoucher = idVoucher;
    }

    public Integer getIdMerchant() {
        return idMerchant;
    }

    public void setIdMerchant(Integer idMerchant) {
        this.idMerchant = idMerchant;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public Double getVoucherPrice() {
        return voucherPrice;
    }

    public void setVoucherPrice(Double voucherPrice) {
        this.voucherPrice = voucherPrice;
    }

    public Double getMaxDiscountPrice() {
        return maxDiscountPrice;
    }

    public void setMaxDiscountPrice(Double maxDiscountPrice) {
        this.maxDiscountPrice = maxDiscountPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getVoucherQuantity() {
        return voucherQuantity;
    }

    public void setVoucherQuantity(Integer voucherQuantity) {
        this.voucherQuantity = voucherQuantity;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public void setIsActive(Integer isActive){
        if (isActive == 1){
            this.isActive = Boolean.TRUE;
        }else{
            this.isActive = Boolean.FALSE;
        }
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
        obj.put("idVoucher", Integer.valueOf(idVoucher));
        obj.put("idMerchant", Integer.valueOf(idMerchant));
        obj.put("voucherName", voucherName);
        obj.put("voucherPrice", Double.valueOf(voucherPrice));
        obj.put("maxDiscountPrice", Double.valueOf(maxDiscountPrice));
        obj.put("discount", Double.valueOf(discount));
        obj.put("voucherQuantity", Integer.valueOf(voucherQuantity));
        obj.put("expiredDate", expiredDate);
        obj.put("isActive", Boolean.valueOf(isActive));
        obj.put("createdAt", sdf.format(createdAt));
        obj.put("updatedAt", sdf.format(updatedAt));

        return obj;
    }
}
