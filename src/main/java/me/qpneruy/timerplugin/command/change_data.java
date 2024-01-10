package me.qpneruy.timerplugin.command;
import me.qpneruy.timerplugin.task.TimeData;
import me.qpneruy.timerplugin.task.archive;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class change_data implements CommandExecutor {



    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        archive setData = new archive();
        Player player = (Player) commandSender;
        List<TimeData> Command_data = setData.getTimeDataList();
        int index = Integer.parseInt(strings[0])-1;
        if (index < 0 || index > strings.length) {
            player.sendMessage("§6[TimerPro]: §fKhông tồn tại lệnh này !.");
            return false;
        }
        TimeData data = Command_data.get(index);
        if (commandSender instanceof Player) {
            if (strings[1].equals("time")){
                player.sendMessage("§6[TimerPro]: §fĐã thay đổi thời gian.");
                player.sendMessage("§6[TimerPro]: §f└->  §c" + data.getTime() + " §f-> §a" + strings[2]);
                data.setTime(strings[2]);
            } else if (strings[1].equals("command")) {
                StringBuilder full_command = new StringBuilder(strings[2]);
                for (int i = 3; i < strings.length; i++) {
                    full_command.append(" ").append(strings[i]);
                }
                if (Objects.equals(data.getCommand(), full_command.toString())) {
                    player.sendMessage("§6[TimerPro]: §fKhông có thay đổi !." );
                } else {
                player.sendMessage("§6[TimerPro]: §fĐã thay đổi lệnh." );
                player.sendMessage("§6[TimerPro]: §c[-] /" + data.getCommand());
                player.sendMessage("§6[TimerPro]: §a[+] /" + full_command);
                data.setCommand(full_command.toString());
                }
            }
        }
        setData.setTimdataList(Integer.parseInt(strings[0])-1, data);
        return false;
    }
}
