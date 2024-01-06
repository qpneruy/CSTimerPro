package me.qpneruy.timerplugin;

import org.bukkit.plugin.java.JavaPlugin;


//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;


public final class TimerPro extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();
        System.out.println("-------------------------------");
        System.out.println("||     Plugin is started      ||");
        System.out.println("-------------------------------");
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ss");
//        LocalTime localTime = LocalTime.now();
//        String currentTime = dtf.format(localTime);
//        int secondsUntilNextMinute = 60 - Integer.parseInt(currentTime);

//        BukkitScheduler scheduler = getServer().getScheduler();
//        scheduler.scheduleSyncDelayedTask(this, new Runnable() {
//            @Override
//            public void run() {
//                // Bắt đầu vòng lặp sự kiện sau khi đợi đến phút mới
//                BukkitRunnable task = new YourTask(YourPlugin.this);
//                task.runTaskTimer(YourPlugin.this, 0L, 100L);
//            }
//        }, secondsUntilNextMinute * 20L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("-------------------------------");
        System.out.println("||     Plugin is stopped      ||");
        System.out.println("-------------------------------");
    }
}
