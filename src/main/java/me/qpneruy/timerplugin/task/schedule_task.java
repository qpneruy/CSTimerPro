package me.qpneruy.timerplugin.task;

import me.qpneruy.timerplugin.TimerPro;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

public class schedule_task extends BukkitRunnable {

    private final TimerPro plugin;
    private final command_task task;
    private final World world = getServer().getWorlds().get(0);
    private static Boolean isReset = false;

    public schedule_task(TimerPro plugin) {
        this.plugin = plugin;
        this.task = new command_task(this.plugin);
    }

    @Override
    public void run() {
        archive JsonWritter = new archive();
        List<TimeData> data = JsonWritter.getTimeDataList();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.now();
        String currentTime = dtf.format(localTime);
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
