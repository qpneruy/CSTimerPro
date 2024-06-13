package me.qpneruy.timerplugin.Commands;

import me.qpneruy.timerplugin.Task.ExecutionCmd;
import me.qpneruy.timerplugin.Task.archiver;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.kyori.adventure.text.event.ClickEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static me.qpneruy.timerplugin.Task.TimeCalibarate.getDayOfWeek;


public class ShowSchedCmd implements CommandExecutor {

    private static final Logger log = LoggerFactory.getLogger(ShowSchedCmd.class);
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            log.debug("Lệnh này chỉ có thể sử dụng trong game!");
            return false;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("TimerPro.Xem_Lenh")) {
            player.sendMessage("§6[TimerPro]: §cBạn không có quyền sử dụng lệnh này!");
            return false;
        }
        if (args.length < 1) {
            player.sendMessage("§6[TimerPro]: §cTruyền tham số vào đê!");
            return false;
        }

        Component delLore = Component.text("Nhấn vào Xóa lệnh này.");
        archiver jsonReader = new archiver();
        String[] daysOfWeek = {"Thu_Hai", "Thu_Ba", "Thu_Tu", "Thu_Nam", "Thu_Sau", "Thu_Bay", "Chu_Nhat"};
        String targetDay = args[0].equals("Hom_Nay") ? getDayOfWeek() : args[0];

        List<String> daysOfWeekList = Arrays.asList(daysOfWeek);

        if (daysOfWeekList.contains(targetDay)) {
            Map<String, ExecutionCmd> commands = jsonReader.getCommands(targetDay);
            player.sendMessage("§6--------------------§e[" + targetDay + "]§6--------------------");

            // Kiểm tra xem có lệnh nào trong ngày đó hay không.
            if (commands.isEmpty()) {
                player.sendMessage("Solo yasour ko?");
                return false;
            }

            int count = 0;
            delLore = getComponent(player, delLore, targetDay, commands, count);
            player.sendMessage("§6--------------------§e[ Hết ]§6--------------------");
        }

        if (targetDay.equals("Tat_Ca")) {
            for (String day : daysOfWeek) {
                Map<String, ExecutionCmd> commands = jsonReader.getCommands(day);
                if (!commands.isEmpty()) {
                    int count = 0;
                    player.sendMessage("§6--------------------§e[" + day + "]§6--------------------");
                    delLore = getComponent(player, delLore, day, commands, count);
                }
            }
        }
        return true;
    }

    private Component getComponent(Player player, Component delLore, String targetDay, Map<String, ExecutionCmd> commands, int count) {
        for (Map.Entry<String, ExecutionCmd> entry : commands.entrySet()) {
            ExecutionCmd commandp = entry.getValue();
            ++count;
            delLore = delLore.append(Component.text("\n /" + commandp.getCommand()));
            Component delElement = Component.text(" §c[-]")
                    .clickEvent(ClickEvent.suggestCommand("/xoa_lenh " + targetDay + " " + commandp.getName()))
                    .hoverEvent(HoverEvent.showText(delLore));
            Component message = Component.text("§e" + count + ". §f" + commandp.getName()).append(delElement);
            player.sendMessage(message);
        }
        return delLore;
    }
}