package me.qpneruy.timerplugin.Command;

import me.qpneruy.timerplugin.task.TimeType;
import me.qpneruy.timerplugin.task.archiver;
import me.qpneruy.timerplugin.task.TimeCalibarate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SchedCmdOperations implements CommandExecutor {
    private static final String TIME_24H_REGEX = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
    public static boolean isValidTime24H(String time) {
        Pattern pattern = Pattern.compile(TIME_24H_REGEX);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        archiver setData = new archiver();
        Player player = (Player) commandSender;
        if (!player.hasPermission("TimerPro.Doi_DuLieu")) {
            player.sendMessage("§6[TimerPro]: §cBạn không có quyền sử dụng lệnh này!");
        }
        String DayOfWeek = strings[0];
        TimeCalibarate Timer = new TimeCalibarate();
        if (Objects.equals(strings[0], "Hom_Nay")) { DayOfWeek = Timer.getDayOfWeek(); }
        List<TimeType> commandData = setData.getDayTimeList(DayOfWeek);
        int index = Integer.parseInt(strings[1]) - 1;
        if (index < 0 || index > commandData.size()) {
            player.sendMessage("§6[TimerPro]: §fKhông tồn tại dữ liệu này!");
            return false;
        }
        TimeType data = commandData.get(index);
        switch (strings[2].toLowerCase()) {
            case "thoi_gian":
                if (isValidTime24H(strings[3])) {
                    player.sendMessage("§6[TimerPro]: §fĐã thay đổi thời gian (Lệnh:" + index + ").");
                    player.sendMessage("§6[TimerPro]: §f└->  §c" + data.getTime() + " §f-> §a" + strings[3]);
                    data.setTime(strings[3]);
                } else {
                    player.sendMessage("§6[TimerPro]: §fThời gian không hợp lệ!");
                    return false;
                }
                break;
            case "lenh":
                StringBuilder full_command = new StringBuilder(strings[3]);
                for (int i = 4; i < strings.length; i++) {
                    full_command.append(" ").append(strings[i]);
                }
                if (Objects.equals(data.getCommand(), full_command.toString())) {
                    player.sendMessage("§6[TimerPro]: §fKhông có thay đổi !.");
                } else {
                    player.sendMessage("§6[TimerPro]: §fĐã thay đổi lệnh (Lệnh:" + index + ").");
                    player.sendMessage("§6[TimerPro]: §c[-] /" + data.getCommand());
                    player.sendMessage("§6[TimerPro]: §a[+] /" + full_command);
                    data.setCommand(full_command.toString());
                }
                break;
        }
        setData.setDayTimeData(DayOfWeek, index, data);
        return true;
    }
}
