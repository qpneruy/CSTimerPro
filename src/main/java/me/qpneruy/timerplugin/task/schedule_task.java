package me.qpneruy.timerplugin.task;

import me.qpneruy.timerplugin.TimerPro;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getServer;

public class schedule_task extends BukkitRunnable {

    private final TimerPro plugin;
    private final timerTask task;
    private final archive JsonWritter = new archive();
    private final World world = getServer().getWorlds().get(0);
    private final DateTimeZone zone = DateTimeZone.forID("Asia/Ho_Chi_Minh");
    private final org.joda.time.format.DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm");
    private static Boolean isReset = false;
    private String CurrentDDD;

    public schedule_task(TimerPro plugin) {
        this.plugin = plugin;
        this.task = new timerTask(this.plugin);
        this.CurrentDDD = getDayOfWeek();
    }
    private String getDayOfWeek() {
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

    @Override
    public void run() {
        JsonWritter.LoadData();
        List<TimeData> data = this.JsonWritter.getDayTimeList(CurrentDDD);
        DateTime timeData = DateTime.now(zone);

        String currentTime = fmt.print(timeData);
        if (Objects.equals(currentTime, "00:00")) { this.CurrentDDD = getDayOfWeek(); }
        System.out.println(currentTime);
        for (TimeData items : data) {

            if (Objects.equals(items.getTime(), currentTime)) {
                getServer().dispatchCommand(getServer().getConsoleSender(), items.getCommand());
            }
        }
        long worldTime = world.getTime();
        if (worldTime > 13000 && !isReset) {
            Bukkit.getScheduler().cancelTasks(this.plugin);
            task.Run_Task();
            isReset = true;
        }
        if (worldTime < 13000 && isReset) isReset = false;

    }
}
