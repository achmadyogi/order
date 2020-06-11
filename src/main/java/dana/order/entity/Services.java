package dana.order.entity;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

public class Services {
    private Integer idService;
    private String serviceName;

    public Integer getIdService() {
        return idService;
    }

    public void setIdService(Integer idService) {
        this.idService = idService;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String toJsonString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public JSONObject toJsonObject(){
        JSONObject obj = new JSONObject();
        obj.put("idService", Integer.valueOf(idService));
        obj.put("serviceName", serviceName);

        return obj;
    }
}
