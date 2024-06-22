package me.qpneruy.timerplugin.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeCalibarate {

    public static String getTime(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault()); // Sử dụng Locale.getDefault()
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        Date currentTime = new Date();
        return dateFormat.format(currentTime);
    }

    public static boolean isDisparity(String time) {
        return time.equals("00");
    }

    public static String getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        return switch (dayOfWeek) {
            case Calendar.SUNDAY -> "Chu_Nhat";
            case Calendar.MONDAY -> "Thu_Hai";
            case Calendar.TUESDAY -> "Thu_Ba";
            case Calendar.WEDNESDAY -> "Thu_Tu";
            case Calendar.THURSDAY -> "Thu_Nam";
            case Calendar.FRIDAY -> "Thu_Sau";
            case Calendar.SATURDAY -> "Thu_Bay";
            default -> "";
        };
    }
}
