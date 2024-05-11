package me.qpneruy.timerplugin.Command;

import me.qpneruy.timerplugin.task.archiver;
import me.qpneruy.timerplugin.task.TimeCalibarate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DeleteSchedCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        if (!player.hasPermission("TimerPro.Xoa_Lenh")) {
            player.sendMessage("§6[TimerPro]: §cBạn không có quyền sử dụng lệnh này!");
        }
        archiver JsonDel = new archiver();
        String DayOfWeek = strings[0];
        TimeCalibarate Timer = new TimeCalibarate();
        if (Objects.equals(strings[0], "Hom_Nay")) { DayOfWeek = Timer.getDayOfWeek(); }
        int index = Integer.parseInt(strings[1]) - 1;
        if (index < 0 || index > JsonDel.getDayTimeList(DayOfWeek).size()) {
            player.sendMessage("§6[TimerPro]: §fVị trí không hợp lệ!");
            return false;
        }
        JsonDel.RemoveCommand(DayOfWeek, index);
        player.sendMessage("§6[TimerPro]: §fĐã xóa Lệnh thành công!");
        return true;
    }
}
