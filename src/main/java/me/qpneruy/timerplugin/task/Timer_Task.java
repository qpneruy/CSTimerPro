package me.qpneruy.timerplugin.task;

import me.qpneruy.timerplugin.TimerPro;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.bukkit.Bukkit.getServer;

public class Timer_Task {
    private final TimerPro plugin;

    public Timer_Task(TimerPro plugin) {
        this.plugin = plugin;
    }

    public void Run_Task(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ss");
        LocalTime localTime = LocalTime.now();
        String currentTime = dtf.format(localTime);
        int secondsUntilNextMinute = 60 - Integer.parseInt(currentTime);
        getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
            @Override
            public void run() {
                BukkitRunnable Task = new schedule_task(Timer_Task.this.plugin);
                Task.runTaskTimer(Timer_Task.this.plugin, 0L, 1200L);
            }
        }, secondsUntilNextMinute * 20L);
    }
}
