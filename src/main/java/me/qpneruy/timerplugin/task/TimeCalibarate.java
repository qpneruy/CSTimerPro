package me.qpneruy.timerplugin.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeCalibarate {
    public String getTime(String Format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Format);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        Date currentTime = new Date();
        return dateFormat.format(currentTime);
    }
    public String getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SUNDAY:
                return "Chu_Nhat";
            case Calendar.MONDAY:
                return "Thu_Hai";
            case Calendar.TUESDAY:
                return "Thu_Ba";
            case Calendar.WEDNESDAY:
                return "Thu_Tu";
            case Calendar.THURSDAY:
                return "Thu_Nam";
            case Calendar.FRIDAY:
                return "Thu_Sau";
            case Calendar.SATURDAY:
                return "Thu_Bay";
            default:
                return "";
        }
    }
}
