package me.qpneruy.timerplugin;

import org.bukkit.scheduler.BukkitRunnable;

public class task extends BukkitRunnable {
    private final TimerPro plugin;
    public task(TimerPro plugin){
        this.plugin = plugin;
    }

    @Override
    public void run() {
        System.out.println("Task has been running");
    }
}
