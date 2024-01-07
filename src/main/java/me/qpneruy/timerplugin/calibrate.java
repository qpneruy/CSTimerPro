package me.qpneruy.timerplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class calibrate implements CommandExecutor {
    private final TimerPro plugin;

    public calibrate(TimerPro plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender Sender, Command command, String label, String[] Args) {
        Player player = (Player) Sender;
        if (Sender instanceof Player) {
            Bukkit.getScheduler().cancelTask(plugin.scheduledTask);
            plugin.Boss_Task();
            player.sendMessage("Đã đồng bộ bộ đếm với thời gian thực!");
        }
        return false;
    }
}
