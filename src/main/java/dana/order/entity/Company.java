package dana.order.entity;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

public class Company {
    private Integer idCompany;
    private String companyName;
    private String description;
    private Integer companyCode;
    private String companyPhoto;

    public Integer getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(Integer idCompany) {
        this.idCompany = idCompany;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(Integer companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyPhoto() {
        return companyPhoto;
    }

    public void setCompanyPhoto(String companyPhoto) {
        this.companyPhoto = companyPhoto;
    }

    public String toJsonString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public JSONObject toJsonObject(){
        JSONObject obj = new JSONObject();
        obj.put("idCompany", Integer.valueOf(idCompany));
        obj.put("companyName", companyName);
        obj.put("description", description);
        obj.put("companyCode", Integer.valueOf(companyCode));
        obj.put("companyPhoto", companyPhoto);

        return obj;
    }
}
