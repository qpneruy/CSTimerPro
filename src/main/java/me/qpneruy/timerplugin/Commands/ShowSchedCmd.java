package me.qpneruy.timerplugin.Commands;


import me.qpneruy.timerplugin.Task.archiver;
import me.qpneruy.timerplugin.Types.ExecutionCmd;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static me.qpneruy.timerplugin.Task.TimeCalibarate.getDayOfWeek;


public class ShowSchedCmd implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage("Lệnh này chỉ có thể sử dụng trong game!");
            return false;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("TimerPro.xemlenh")) {
            player.sendMessage("§6[TimerPro]: §cBạn không có quyền sử dụng lệnh này!");
            return false;
        }
        archiver jsonReader = new archiver();
        String targetDay = args[0].equals("Hom_Nay") ? getDayOfWeek() : args[0];

        List<String> daysOfWeek = Arrays.asList("Moi_Ngay", "Thu_Hai", "Thu_Ba", "Thu_Tu", "Thu_Nam", "Thu_Sau", "Thu_Bay", "Chu_Nhat");
        boolean nam = false;
        if (daysOfWeek.contains(targetDay)) {
            Map<String, ExecutionCmd> commands = jsonReader.getCommands(targetDay);
            player.sendMessage("§6--------------------§e[" + targetDay.replace("_", " ") + "]§6--------------------");
            if (commands.isEmpty()) {
                player.sendMessage("§e1. §fLMHT §a16:00 §c[-]");
                player.sendMessage("§e└-> §f/Solo Yasour pc tòa nhà Quốc Hội.");
            }
            int count = 0;
            sendComponent(player, targetDay, commands, count);
            player.sendMessage("§6--------------------§e[ Hết ]§6--------------------");
        }

        if (targetDay.equals("Tat_Ca")) {
            for (String day : daysOfWeek) {
                Map<String, ExecutionCmd> commands = jsonReader.getCommands(day);
                if (!commands.isEmpty()) {
                    int count = 0;
                    player.sendMessage("§6--------------------§e[" + day + "]§6--------------------");
                    sendComponent(player, day, commands, count);
                }
            }
            player.sendMessage("§6--------------------§e[ Hết ]§6--------------------");
        }
        return true;
    }

    private void sendComponent(Player player, String targetDay, Map<String, ExecutionCmd> commands, int count) {
        for (Map.Entry<String, ExecutionCmd> entry : commands.entrySet()) {
            ExecutionCmd cmd = entry.getValue();
            ++count;

            Component deletebuttonHover = Component.text("Nhấn để xóa lệnh này." + "\n /" + cmd.getCommand());
            Component deleteButton = Component.text(" §c[-]")
                    .clickEvent(ClickEvent.suggestCommand("/xoa_lenh " + targetDay + " " + cmd.getName()))
                    .hoverEvent(HoverEvent.showText(deletebuttonHover));

            Component editbuttonHover = Component.text("Nhấn để sửa lệnh này");
            Component editButton = Component.text("§e" + count + ". §f" + cmd.getName())
                    .clickEvent(ClickEvent.runCommand("suaLenh " + cmd.getExecDateTime() + " " + cmd.getName()))
                    .hoverEvent(HoverEvent.showText(editbuttonHover));

            Component message = editButton.append(deleteButton);
            player.sendMessage(message);
        }
    }
}
