package me.qpneruy.timerplugin;

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
                player.sendMessage("you didnot provide any args");
                return false;
        } else {

            int x_location = Integer.parseInt((String)  strings[1]);
            int y_location = Integer.parseInt((String)  strings[2]);
            int z_location = Integer.parseInt((String)  strings[3]);
        json JsonWritter = new json();
        JsonWritter.addTimeData(strings[0], x_location,y_location, z_location, strings[4]);
        }

        return false;
    }
}
