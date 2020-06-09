package dana.order.entity;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

public class ThirdParty {
    private Integer idThirdParty;
    private String partyName;
    private Integer partyCode;

    public Integer getIdThirdParty() {
        return idThirdParty;
    }

    public void setIdThirdParty(Integer idThirdParty) {
        this.idThirdParty = idThirdParty;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public Integer getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(Integer partyCode) {
        this.partyCode = partyCode;
    }

    public String toJsonString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public JSONObject toJsonObject(){
        JSONObject obj = new JSONObject();
        obj.put("idThirdParty", Integer.valueOf(idThirdParty));
        obj.put("partyName", partyName);
        obj.put("partyCode", Integer.valueOf(partyCode));

        return obj;
    }
}
