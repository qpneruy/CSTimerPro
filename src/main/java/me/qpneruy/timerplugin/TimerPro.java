package me.qpneruy.timerplugin;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public final class TimerPro extends JavaPlugin {
    public int scheduledTask;
    private static TimerPro plugin;
    public static TimerPro getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        System.out.println("-------------------------------");
        System.out.println("||     Plugin is started      ||");
        System.out.println("-------------------------------");
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
//        getServer().getPluginManager().registerEvents(new calibrate(this), this);
        Boss_Task();
        getCommand("taskcancel").setExecutor(new Task_command(this));
        getCommand("StartSave").setExecutor(new calibrate(this));
        getCommand("add_Time").setExecutor(new time_add(this));
    }
    public void Boss_Task(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ss");
        LocalTime localTime = LocalTime.now();
        String currentTime = dtf.format(localTime);
        int secondsUntilNextMinute = 60 - Integer.parseInt(currentTime);
        System.out.println(secondsUntilNextMinute);

        scheduledTask = getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                BukkitRunnable Task = new task(TimerPro.this);
                Task.runTaskTimer(TimerPro.this, 0L, 1200L);
            }
        }, secondsUntilNextMinute * 20L);
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("-------------------------------");
        System.out.println("||     Plugin is stopped      ||");
        System.out.println("-------------------------------");
    }
}
