package me.qpneruy.timerplugin.Commands;

import me.qpneruy.timerplugin.Task.archiver;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.qpneruy.timerplugin.Task.TimeCalibarate.getDayOfWeek;

public class DeleteSchedCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("[TimerPro]: Lệnh này chỉ có thể sử dụng trong game!");
            return false;
        }
        Player player = (Player) commandSender;
        if (!player.hasPermission("TimerPro.XoaLenh")) {
            player.sendMessage("§6[TimerPro]: §cBạn không có quyền sử dụng lệnh này!");
        }
        if (strings.length < 2) {
            player.sendMessage("§6[TimerPro]: §cVui lòng nhập tên lệnh cần xóa!");
            return false;
        }
        String DateOrDay = strings[0];
        String cmdName = strings[1];
        if (DateOrDay.equals("Hom_Nay")) {
            DateOrDay = getDayOfWeek();
        }
        archiver DelArchiver = new archiver();
        if (!DelArchiver.RemoveCommand(DateOrDay, cmdName)) {
            player.sendMessage("§6[TimerPro]: §cKhông tìm thấy lệnh cần xóa!");
            return false;
        }
        player.sendMessage("§6[TimerPro]: §fĐã xóa lệnh §c\"" + cmdName + "\" §fthành công!");
        return true;
    }
}
