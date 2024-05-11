package me.qpneruy.timerplugin.task;

import me.qpneruy.timerplugin.TimerPro;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class Task extends BukkitRunnable {

    private final TimerPro plugin;
    private final TaskStarting task;
    private final archiver JsonWritter = new archiver();
    private final World world = getServer().getWorlds().get(0);
    private static Boolean isReset = false;
    private String CurrentDDD;
    private final TimeCalibarate Timer;

    public Task(TimerPro plugin) {
        this.plugin = plugin;
        Timer = new TimeCalibarate();
        this.task = new TaskStarting(this.plugin);
        this.CurrentDDD = Timer.getDayOfWeek();


    }


    @Override
    public void run() {
        JsonWritter.LoadData();
        List<TimeType> data = this.JsonWritter.getDayTimeList(CurrentDDD);
        String currentTime = Timer.getTime("HH:mm");
        if (Objects.equals(currentTime, "00:00")) { this.CurrentDDD = Timer.getDayOfWeek(); }
        getLogger().info("Current Time: " + currentTime);

        if (data != null) {
            for (TimeType items : data) {
                if (Objects.equals(items.getTime(), currentTime)) {
                    getServer().dispatchCommand(getServer().getConsoleSender(), items.getCommand());
                }
            }
        }

        data = this.JsonWritter.getDayTimeList("Tat_Ca");
        if (data != null) {
            for (TimeType items : data) {
                if (Objects.equals(items.getTime(), currentTime)) {
                    getServer().dispatchCommand(getServer().getConsoleSender(), items.getCommand());
                }
            }
        }

        long worldTime = world.getTime();
        if (worldTime > 13000 && !isReset) {
            Bukkit.getScheduler().cancelTasks(this.plugin);
            task.Run_Task();
            isReset = true;
            getLogger().info("Đã hiệu chỉnh thời gian!");
        }
        if (worldTime < 13000 && isReset) isReset = false;
    }
}
