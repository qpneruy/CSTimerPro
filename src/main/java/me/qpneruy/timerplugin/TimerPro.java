package me.qpneruy.timerplugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


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
        getLogger().info("-------------------------------");
        getLogger().info("||     Đã khởi động Plugin   ||");
        getLogger().info("-------------------------------");
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
//        getServer().getPluginManager().registerEvents(new calibrate(this), this);
        Boss_Task();
        Objects.requireNonNull(getCommand("Sync_Time")).setExecutor(new calibrate(this));
        Objects.requireNonNull(getCommand("Add_Time")).setExecutor(new time_add(this));
        Objects.requireNonNull(getCommand("Add_Time")).setTabCompleter(new auto_complete());
    }
    public void Boss_Task(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ss");
        LocalTime localTime = LocalTime.now();
        String currentTime = dtf.format(localTime);
        int secondsUntilNextMinute = 60 - Integer.parseInt(currentTime);
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
        getLogger().info("-------------------------------");
        getLogger().info("||       Đã dừng PLugin      ||");
        getLogger().info("-------------------------------");
    }
}
