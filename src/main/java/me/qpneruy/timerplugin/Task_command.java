package me.qpneruy.timerplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.apache.logging.log4j.LogManager.getLogger;

public class Task_command implements CommandExecutor {
    private final TimerPro plugin;

    public Task_command(TimerPro plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player) {
            Player player = (Player) sender;
            getLogger().info("đang chạy task:" + plugin.scheduledTask);
            Bukkit.getScheduler().cancelTask(plugin.scheduledTask);
            plugin.Boss_Task();
            getLogger().info("đang chạy task:" + plugin.scheduledTask);
            player.sendMessage("da khoi dong lai task");

        }
    return true;
    }
}
