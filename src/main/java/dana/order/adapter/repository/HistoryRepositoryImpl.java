package dana.order.adapter.repository;

import dana.order.entity.Transaction;
import dana.order.entity.TransactionHistoryModel;
import dana.order.usecase.port.DatabaseMapper;
import dana.order.usecase.port.HistoryRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class HistoryRepositoryImpl implements HistoryRepository {

    @Autowired
    DatabaseMapper databaseMapper;

    public JSONObject getUserHistory(JSONObject json){
        JSONObject data = new JSONObject();
        String startDate = null, endDate = null;
        Integer start, page, category1 = 0, category2 = 0, category3 = 0, category4 = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (json.get("category") != null){
            if ((""+json.get("category")).equals("COMPLETED")){
                category1 = 1;
                category2 = 2;
                category3 = 3;
            }else{
                category4 = 4;
            }
        }else{
            category1 = 1;
            category2 = 2;
            category3 = 3;
            category4 = 4;
        }

        if (json.get("startDate") != null && json.get("endDate") != null){
            startDate = ""+json.get("startDate");
            endDate = ""+json.get("endDate");
        }

        if (json.get("startDate") != null && json.get("endDate") == null){
            startDate = ""+json.get("startDate");
            Date toCalculateEndDate = null;
            try{
                toCalculateEndDate = sdf.parse(startDate);
            }catch (ParseException e){e.printStackTrace();}

            Calendar c = Calendar.getInstance();
            c.setTime(toCalculateEndDate);
            c.add(Calendar.DATE, 7);

            endDate = sdf.format(c.getTime());
        }

        if (json.get("startDate") == null && json.get("endDate") != null){
            endDate = ""+json.get("endDate");
            Date toCalculateStartDate = null;
            try {
                toCalculateStartDate = sdf.parse(endDate);
            }catch (ParseException e){e.printStackTrace();}

            Calendar c = Calendar.getInstance();
            c.setTime(toCalculateStartDate);
            c.add(Calendar.DATE, -7);

            endDate = sdf.format(c.getTime());
        }

        if (json.get("startDate") == null && json.get("endDate") == null){
            Transaction first = databaseMapper.getUserFirstTransaction(""+json.get("idUser"));
            Transaction second = databaseMapper.getUserLastTransaction(""+json.get("idUser"));
            if (first == null){
                startDate = sdf.format(new Date());
                endDate = sdf.format(new Date());
            }else{
                startDate = sdf.format(first.getCreatedAt());
                endDate = sdf.format(second.getCreatedAt());
            }
        }

        if (json.get("page") != null){
            start = Integer.valueOf(""+json.get("page"))*10-10;
            page = Integer.valueOf(""+json.get("page"));
        }else {
            start = 0;
            page = 1;
        }

        List<TransactionHistoryModel> result = databaseMapper.selectTransactionHistory(""+json.get("idUser"),
                category1, category2, category3, category4, startDate, endDate, start);

        Integer totalRecords = databaseMapper.getTotalTransactionHistory(""+json.get("idUser"),
                category1, category2, category3, category4, startDate, endDate);
        Integer totalPages = Math.abs(totalRecords/10)+1;
        Integer currentRecords = 0;
        if (totalPages == page){
            currentRecords = totalRecords%10;
        }else if (totalPages < page){
            currentRecords = 0;
        }else {
            currentRecords = 10;
        }

        JSONObject pagination = new JSONObject();
        pagination.put("currentPage", Integer.valueOf(page));
        pagination.put("totalPages", Integer.valueOf(totalPages));
        pagination.put("currentRecords", Integer.valueOf(currentRecords));
        pagination.put("totalRecords", Integer.valueOf(totalRecords));

        JSONObject finalResult = new JSONObject();
        finalResult.put("content", result);
        finalResult.put("pagination", pagination);

        return finalResult;
    }

    public JSONObject getUserDetailedHistory(Integer idTransaction){
        JSONObject data = new JSONObject();

        return data;
    }
}
