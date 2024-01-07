package me.qpneruy.timerplugin;

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
        if (Sender instanceof Player) {
            json Json = new json();
        }
        return false;
    }
}
