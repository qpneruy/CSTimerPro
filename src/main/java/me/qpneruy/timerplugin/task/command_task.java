package me.qpneruy.timerplugin.task;

import me.qpneruy.timerplugin.TimerPro;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.bukkit.Bukkit.getServer;

public class command_task {
    private final TimerPro plugin;
    private static int schedule_task;

    public command_task(TimerPro plugin) {
        this.plugin = plugin;
    }

    public void Run_Task(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ss");
        LocalTime localTime = LocalTime.now();
        String currentTime = dtf.format(localTime);
        int secondsUntilNextMinute = 60 - Integer.parseInt(currentTime);
        schedule_task = getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
            @Override
            public void run() {
                BukkitRunnable Task = new schedule_task(command_task.this.plugin);
                Task.runTaskTimer(command_task.this.plugin, 0L, 1200L);
            }
        }, secondsUntilNextMinute * 20L);
    }
}
