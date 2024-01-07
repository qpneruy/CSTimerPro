package me.qpneruy.timerplugin;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class time_add implements CommandExecutor {
    private final TimerPro plugin;

    public time_add(TimerPro plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        Player player = (Player) commandSender;
        if (strings.length == 0) {
                player.sendMessage("Truyền tham số vào đê!");
                return false;
        } else {
            Location location = player.getLocation();
            json JsonWritter = new json();
            JsonWritter.addTimeData(strings[0],  location.getBlockX() ,location.getBlockY(), location.getBlockZ(), strings[1]);
        }
        return false;
    }
}
