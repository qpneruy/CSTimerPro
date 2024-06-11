package me.qpneruy.timerplugin.Task;

import me.qpneruy.timerplugin.TimerPro;
import org.bukkit.scheduler.BukkitRunnable;


import static org.bukkit.Bukkit.getServer;

public class TaskManager { ;
    private final TimerPro plugin;

    public TaskManager(TimerPro plugin) {
        this.plugin = plugin;
    }

    public void start() {
        String currentTime = TimeCalibarate.getTime("ss");
        int secondsUntilNextMinute = 60 - Integer.parseInt(currentTime);
        getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
            BukkitRunnable Task = new Task(TaskManager.this.plugin);
            Task.runTaskTimer(TaskManager.this.plugin, 0L, 1200L);
        }, secondsUntilNextMinute * 20L);
    }
}
