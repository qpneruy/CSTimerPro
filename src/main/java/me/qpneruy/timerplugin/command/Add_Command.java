package me.qpneruy.timerplugin.command;

import me.qpneruy.timerplugin.task.archive;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Add_Command implements CommandExecutor {

    private static final String TIME_24H_REGEX = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

    public static boolean isValidTime24H(String time) {
        Pattern pattern = Pattern.compile(TIME_24H_REGEX);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        Player player = (Player) commandSender;
        if (!player.hasPermission("TimerPro.Them_Lenh")) {
            player.sendMessage("§6[TimerPro]: §cBạn không có quyền sử dụng lệnh này!");
        }
        if (strings.length == 0) {
            player.sendMessage("§6[TimerPro]: §cTruyền tham số vào đê!");
            return false;
        } else {
            if (isValidTime24H(strings[1]) && strings.length > 2) {
                archive JsonWritter = new archive();
                StringBuilder full_command = new StringBuilder(strings[2]);
                for (int i = 3; i < strings.length; i++) {
                    full_command.append(" ").append(strings[i]);
                }
                System.out.println(strings[0] + ' ' + strings[1] + ' ' + full_command.toString());
                JsonWritter.AddCommand(strings[0], strings[1], full_command.toString());
                player.sendMessage("§6[TimerPro]: §fĐã thêm lệnh Thành công!");
            } else {
                player.sendMessage("§6[TimerPro]: §cTham số không hợp lệ !");
                player.sendMessage("§6[TimerPro]: Dùng: <Command> <Time 24H> <excec_command>");
            }
        }
        return true;
    }
}
