package me.qpneruy.timerplugin.task;

import me.qpneruy.timerplugin.TimerPro;

import org.bukkit.scheduler.BukkitRunnable;


import static org.bukkit.Bukkit.getServer;

public class TaskStarting {
    private final TimerPro plugin;
    private final TimeCalibarate timer;

    public TaskStarting(TimerPro plugin) {
        timer = new TimeCalibarate();
        this.plugin = plugin;
    }

    public void Run_Task(){
        String currentTime = timer.getTime("ss");
        int secondsUntilNextMinute = 60 - Integer.parseInt(currentTime);
        getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
            @Override
            public void run() {
                BukkitRunnable Task = new Task(TaskStarting.this.plugin);
                Task.runTaskTimer(TaskStarting.this.plugin, 0L, 1200L);
            }
        }, secondsUntilNextMinute * 20L);
    }
}
