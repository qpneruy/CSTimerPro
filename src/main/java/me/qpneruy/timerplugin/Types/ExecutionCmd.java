package me.qpneruy.timerplugin.Types;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ExecutionCmd {
    private static final List<String> VALID_DAYS_OF_WEEK = Arrays.asList(
            "Moi_Ngay", "Thu_Hai", "Thu_Ba", "Thu_Tu", "Thu_Nam", "Thu_Sau", "Thu_Bay", "Chu_Nhat"
    );
    private String name;
    private String command;
    private String execDate;
    private String execDayOfWeek;
    private String startTime;
    private String endTime;
    private boolean eachHour;
    private boolean eachMinute;
    private boolean isEnabled = true;

    /**
     * Khởi tạo một lệnh hẹn giờ mới.
     *
     * @param name         Tên của lệnh.
     * @param command       Lệnh sẽ được thực thi.
     * @param execDateTime Chuỗi thời gian thực thi, có thể là ngày tháng (MM/dd/yyyy) hoặc thứ trong tuần.
     * @param startTime     Thời gian bắt đầu thực thi, theo định dạng HH:mm.
     * @return true nếu khởi tạo thành công, false nếu không.
     */
    public boolean init(String name, String command, String execDateTime, String startTime) {
        this.name = name;
        this.setCommand(command);
        if (!setExecDateTime(execDateTime)) {
            return false;
        }
        if (!setStartTime(startTime)) {
            return false;
        }
        this.endTime = this.startTime;
        this.eachHour = false;
        this.eachMinute = false;
        this.isEnabled = true;
        return true;
    }

    public String getName() {
        return this.name != null ? this.name : "UnnamedCommand";
    }

    public String getCommand() {
        return this.command;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getExecDateTime() {
        if (this.execDate != null) {
            return this.execDate;
        }
        return this.execDayOfWeek;
    }

    public boolean setExecDateTime(String execDateTime) {

        if (isValidDate(execDateTime)) {
            this.execDate = execDateTime;
            return true;
        }
        if (isValidDayOfWeek(execDateTime)) {
            this.execDayOfWeek = execDateTime;
            return true;
        }
        return false;
    }

    public boolean setStartTime(String startTime) {
        if (isValidTime(startTime)) {
            this.startTime = startTime;
            return true;
        }
        return false;
    }

    public boolean setEndTime(String endTime) {
        if (isValidTime(endTime)) {
            this.endTime = endTime;
            return true;
        }
        return false;
    }

    public void toggleEachHour() {
        this.eachHour = !this.eachHour;
    }

    public void toggleEachMinute() {
        this.eachMinute = !this.eachMinute;
    }

    public void toggleCommand() {
        this.isEnabled = !this.isEnabled;
    }

    private static boolean isValidTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        sdf.setLenient(false);
        try {
            sdf.parse(time);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isValidDayOfWeek(String dayOfWeek) {
        return VALID_DAYS_OF_WEEK.contains(dayOfWeek);
    }

    public static boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean isEachHour() {
        return this.eachHour;
    }

    public boolean isEachMinute() {
        return this.eachMinute;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }
}
