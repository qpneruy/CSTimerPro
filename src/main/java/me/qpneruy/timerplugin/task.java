package me.qpneruy.timerplugin;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

import static org.apache.logging.log4j.LogManager.getLogger;
import static org.bukkit.Bukkit.getConsoleSender;
import static org.bukkit.Bukkit.getServer;

public class task extends BukkitRunnable {
    private final TimerPro plugin;
    private final World world = getServer().getWorlds().get(0);
    private Boolean isReset = false;
    public task(TimerPro plugin){
        this.plugin = plugin;
    }
    @Override
    public void run(){
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player items : players){
            Player player = items.getPlayer();
            Location location = player.getLocation();
            getServer().dispatchCommand(getServer().getConsoleSender(), "summon minecraft:axolotl " + location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ());
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm:ss");
        LocalTime localTime = LocalTime.now();
        String currentTime = dtf.format(localTime);
        System.out.println(currentTime);
        long worldTime = world.getTime();
        getLogger().info("Current time: " + worldTime);
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
