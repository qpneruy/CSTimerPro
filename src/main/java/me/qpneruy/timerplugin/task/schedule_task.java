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
    private static final Logger logger = Logger.getLogger(archive.class.getName());
    private final TimerPro plugin;
    private final Timer_Task task;
    private final archive JsonWritter = new archive();
    private final World world = getServer().getWorlds().get(0);
    private final DateTimeZone zone = DateTimeZone.forID("Asia/Ho_Chi_Minh");
    private final org.joda.time.format.DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm");
    private static Boolean isReset = false;
    private String CurrentDDD;

    public schedule_task(TimerPro plugin) {
        this.plugin = plugin;
        this.task = new Timer_Task(this.plugin);
        this.CurrentDDD = getDayOfWeek();
    }
    private String getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
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
        if (Objects.equals(currentTime, "23:59")) { this.CurrentDDD = getDayOfWeek(); }
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
