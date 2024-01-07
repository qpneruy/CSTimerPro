package me.qpneruy.timerplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.apache.logging.log4j.LogManager.getLogger;
import static org.bukkit.Bukkit.getServer;

public class task extends BukkitRunnable {
    private final TimerPro plugin;
    private final World world = getServer().getWorlds().get(0);
    private static Boolean isReset = false;
    public task(TimerPro plugin){
        this.plugin = plugin;
    }
    @Override
    public void run(){
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        json JsonWritter = new json();
        List<TimeData> data = JsonWritter.getTimeDataList();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm");
        LocalTime localTime = LocalTime.now();
        String currentTime = dtf.format(localTime);

        for (TimeData items: data){
            if (Objects.equals(items.getTime(), currentTime)){
                LocationData location = items.getLocation();
                getLogger().info("Da summon");
                getServer().dispatchCommand(getServer().getConsoleSender(), "summon minecraft:" + items.getName() + " " + location.getX() + " " + location.getY() + " " + location.getZ());
            }
        }
        System.out.println(currentTime);
        long worldTime = world.getTime();
        getLogger().info("Current time: " + worldTime + " " + isReset);
        if (worldTime > 13000 && !isReset){
            getLogger().info("đang chạy task:" + plugin.scheduledTask);
            Bukkit.getScheduler().cancelTask(plugin.scheduledTask);
            plugin.Boss_Task();
            getLogger().info("đang chạy task:" + plugin.scheduledTask);
            isReset = true;
        }
        if (worldTime < 13000 && isReset) isReset = false;

    }
}
