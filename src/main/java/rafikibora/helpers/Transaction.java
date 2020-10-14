package rafikibora.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Transaction {

    public static Date formatTransmissionDate(String transmissionDateTime) throws ParseException {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        String month = transmissionDateTime.substring(0,2);
        String day = transmissionDateTime.substring(2,4);
        String hour = transmissionDateTime.substring(4,6);
        String min = transmissionDateTime.substring(6,8);
        String sec = transmissionDateTime.substring(8);
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String fullDateTime = year+"-"+month+"-"+day+" "+hour+":"+min+":"+sec;
        SimpleDateFormat transmitDateTime = new SimpleDateFormat(pattern);
        Date date = transmitDateTime.parse(fullDateTime);
        return date;
    }
    /**
     * Format currency code of transaction
     * @param currencyCode in numerals
     * @return currency code words
     */
    public static String formatCurrencyCode(String currencyCode){
        String code = "";
        switch(currencyCode){
            case "810":
                code = "KES";
                break;
            case "840":
                code = "USD";
                break;
            default:
                ;
        }
        return code;
    }


}
