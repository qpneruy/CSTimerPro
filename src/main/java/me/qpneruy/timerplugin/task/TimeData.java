package me.qpneruy.timerplugin.task;

public class TimeData {
    private String time;
    private String command;

    public TimeData(String time, String command) {
        this.time = time;
        this.command = command;
    }

    public String getTime() {
        return time;
    }
    public String getCommand() { return command; }
    public void setTime(String Time) { this.time = Time; }
    public void setCommand(String Command) { this.command = Command; }
}
