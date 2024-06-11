package me.qpneruy.timerplugin.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class ExecutionCmd {
    private String name;
    private String command;
    private String execDate = null;
    private String execDayOfWeek = null;
    private String startTime;
    private String endTime;
    private boolean eachHours;
    private boolean eachMinutes;
    private boolean isEnable = false;

    public boolean init(String name, String command, String execDateTime, String startTime) {
        this.name = name;
        this.setCommand(command);
        if (!this.setExecDateTime(execDateTime)) {return false;}
        if (!this.setStartTime(startTime)) {return false;}
        this.endTime = this.startTime;
        this.eachHours = false;
        this.eachMinutes = false;
        this.isEnable = false;
        return true;
    }

    private static final List<String> VALID_DAYS_OF_WEEK = Arrays.asList(
            "Thu_Hai", "Thu_Ba", "Thu_Tu", "Thu_Nam", "Thu_Sau", "Thu_Bay", "Chu_Nhat"
    );

    public String getName()  {
        return this.name != null ? this.name : "UnnamedCommand";
     }
    public String getCommand() { return this.command; }
    public String getExecDate() { return this.execDate; }
    public String getExecDayOfWeek() { return this.execDayOfWeek; }
    public String getStartTime() { return this.startTime; }
    public String getEndTime() { return this.endTime; }

    public void setName(String name) {this.name = name;}
    public void setCommand(String command) {this.command = command;}

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
    public void ToggleEachHour() {
        if (this.eachHours) {
            this.eachHours = false;
            return;
        }
        this.eachHours = true;
    }
    public void ToggleEachMinutes() {
        if (this.eachMinutes) {
            this.eachMinutes = false;
            return;
        }
        this.eachMinutes = true;
    }
    public void toggleCommand() {
        if (this.isEnable) {
            this.isEnable = false;
        }
        this.isEnable = true;
    }

    private static boolean isValidTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
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
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean isEachHour() {
        return this.eachHours;
    }

    public boolean isEachMinutes() {
        return this.eachMinutes;
    }

    public boolean isEnable() {
        return this.isEnable;
    }

}