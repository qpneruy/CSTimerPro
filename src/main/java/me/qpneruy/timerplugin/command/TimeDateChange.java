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

public class TimeDateChange implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        archive setData = new archive();
        Player player = (Player) commandSender;
        if (!player.hasPermission("TimerPro.Doi_DuLieu")) {
            player.sendMessage("§6[TimerPro]: §cBạn không có quyền sử dụng lệnh này!");
        }
        List<TimeData> commandData = setData.getDayTimeList(strings[0]);
        int index = Integer.parseInt(strings[1]) - 1;
        if (index < 0 || index > commandData.size()) {
            player.sendMessage("§6[TimerPro]: §fKhông tồn tại dữ liệu này!");
            return false;
        }
        TimeData data = commandData.get(index);
        if (strings[2].equals("time")) {
            player.sendMessage("§6[TimerPro]: §fĐã thay đổi thời gian.");
            player.sendMessage("§6[TimerPro]: §f└->  §c" + data.getTime() + " §f-> §a" + strings[2]);
            data.setTime(strings[3]);
        } else if (strings[2].equals("command")) {
            StringBuilder full_command = new StringBuilder(strings[2]);
            for (int i = 4; i < strings.length; i++) {
                full_command.append(" ").append(strings[i]);
            }
            if (Objects.equals(data.getCommand(), full_command.toString())) {
                player.sendMessage("§6[TimerPro]: §fKhông có thay đổi !.");
            } else {
                player.sendMessage("§6[TimerPro]: §fĐã thay đổi lệnh.");
                player.sendMessage("§6[TimerPro]: §c[-] /" + data.getCommand());
                player.sendMessage("§6[TimerPro]: §a[+] /" + full_command);
                data.setCommand(full_command.toString());
            }
        }

        setData.setDayTimeData(strings[0], Integer.parseInt(strings[0]) - 1, data);
        return false;
    }
}
