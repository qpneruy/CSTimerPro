package me.qpneruy.timerplugin.Command;

import me.qpneruy.timerplugin.task.TimeType;
import me.qpneruy.timerplugin.task.archiver;
import me.qpneruy.timerplugin.task.TimeCalibarate;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.ClickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class ShowSchedCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        if (!player.hasPermission("TimerPro.Xem_Lenh")) {
            player.sendMessage("§6[TimerPro]: §cBạn không có quyền sử dụng lệnh này!");
        }
        if (strings.length == 0) {
            player.sendMessage("§6[TimerPro]: §cTruyền tham số vào đê!");
            return false;
        }
        String DayOfWeek = strings[0];
        TimeCalibarate Timer = new TimeCalibarate();
        if (Objects.equals(strings[0], "Hom_Nay")) { DayOfWeek = Timer.getDayOfWeek(); }
        if (strings[0].equals("Tat_Ca")) {

        }
        Component Commandlore = Component.text("Nhấn vào để chỉnh sửa lệnh")
                .color(net.kyori.adventure.text.format.NamedTextColor.YELLOW);
        archiver JsonReader = new archiver();
        List<TimeType> Command_data = JsonReader.getDayTimeList(DayOfWeek);
        int Count = 0;
        if (!Command_data.isEmpty()) {
            player.sendMessage("------------------§b[Danh Sách Lệnh]§f--------------------");
            for (TimeType commandp : Command_data) {
                Count++;
                Component TempMessage = Component.text("§e" + Count + ". §6Thời Gian: §f" + commandp.getTime())
                        .append(Component.text("\n§e└->§f /" + commandp.getCommand())
                                .clickEvent(ClickEvent.suggestCommand("/doi_dulieu " + DayOfWeek + " " + Count + " "))
                                .hoverEvent(HoverEvent.showText(Commandlore)));
                player.sendMessage(TempMessage);
            }
            player.sendMessage("------------------------§b[Hết]§f-------------------------");
        } else player.sendMessage("§6[TimerPro]: §fKhông có lệnh nào được thêm!");





        return true;
    }
}
