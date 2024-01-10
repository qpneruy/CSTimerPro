package me.qpneruy.timerplugin.command;

import me.qpneruy.timerplugin.task.TimeData;
import me.qpneruy.timerplugin.task.archive;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class fecth_command implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
           Player player = (Player) commandSender;
            archive JsonReader = new archive();
            List<TimeData> Command_data = JsonReader.getTimeDataList();
            int Count = 0;
            player.sendMessage("------------------§b[Danh Sách Lệnh]§f--------------------");
            for (TimeData commandp : Command_data) {
                Count++;
                player.sendMessage("§e" + Count + ". §6Thời Gian: §f" +  commandp.getTime());
                player.sendMessage("§e└->§f /" + commandp.getCommand());
            }
            player.sendMessage("------------------------§b[Hết]§f-------------------------");
        }
        return true;
    }
}
