package dana.order.usecase.validate;

import dana.order.usecase.exception.HistoryFailedException;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

@Service
public class ValidateTransactionHistory {

    public void check(JSONObject json){
        if (json.get("category") != null){
            if ((""+json.get("category")).equals("COMPLETED") || (""+json.get("category")).equals("IN-PROGRESS")){
                // skip
            }else {
                throw new HistoryFailedException("Category only consists of COMPLETED or IN-PROGRESS content.", HttpStatus.BAD_REQUEST);
            }
        }

        if (json.get("page") != null){
            if (checkPage(""+json.get("page")) == Boolean.FALSE){
                throw new HistoryFailedException("Page parameter uses only numbers.", HttpStatus.BAD_REQUEST);
            }

            Integer page = Integer.valueOf(""+json.get("page"));
            if (page < 1){
                throw new HistoryFailedException("A page cannot have pagination numbers less than 1.", HttpStatus.BAD_REQUEST);
            }
        }

        if (json.get("startDate") != null){
            if (checkDate(""+json.get("startDate")) == Boolean.FALSE){
                throw new HistoryFailedException("Please use a valid date format (yyyy-mm-dd) for history start date.", HttpStatus.BAD_REQUEST);
            }
        }

        if (json.get("endDate") != null){
            if (checkDate(""+json.get("endDate")) == Boolean.FALSE){
                throw new HistoryFailedException("Please use a valid date format (yyyy-mm-dd) for history end date.", HttpStatus.BAD_REQUEST);
            }
        }

        if (json.get("startDate") != null && json.get("endDate") != null){

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = null, endDate = null;

            try {
                startDate = df.parse(""+json.get("startDate"));
                endDate = df.parse(""+json.get("endDate"));
            } catch (ParseException e) {e.printStackTrace();}

            if (startDate.after(endDate)){
                throw new HistoryFailedException("Start date cannot be more current than end date.", HttpStatus.BAD_REQUEST);
            }

            // Maximum search for history is within 7 days
            Calendar testCalendar = Calendar.getInstance();
            testCalendar.setTime(startDate);
            testCalendar.add(Calendar.DATE, 7);
            Date testDate = null;

            try{
                testDate = df.parse(""+df.format(testCalendar.getTime()));
            }catch (ParseException e){e.printStackTrace();}

            if (endDate.after(testDate)){
                throw new HistoryFailedException("Maximum search for history is within 7 days.", HttpStatus.BAD_REQUEST);
            }
        }
    }

    private Boolean checkPage(String page){
        String regex = "^[\\d]+$";
        if(!Pattern.matches(regex, page)){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private Boolean checkDate(String date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            df.parse(date);
            return Boolean.TRUE;
        } catch (ParseException e) {
            return Boolean.FALSE;
        }
    }
}
