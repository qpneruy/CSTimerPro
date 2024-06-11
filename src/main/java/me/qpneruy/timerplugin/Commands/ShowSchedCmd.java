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
      public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings){
          if (!(commandSender instanceof Player)) {
              log.debug("Lệnh này chỉ có thể sử dụng trong game!");
              return false;
          }
          Player player = (Player) commandSender;
          if (!player.hasPermission("TimerPro.Xem_Lenh")) {
              player.sendMessage("§6[TimerPro]: §cBạn không có quyền sử dụng lệnh này!");
              return false;
          }
          if (strings.length < 1) {
             player.sendMessage("§6[TimerPro]: §cTruyền tham số vào đê!");
             return false;
          }
          Component DelLore = Component.text("Nhấn vào Xóa lệnh này.");
          archiver JsonReader = new archiver();
        String[] daysOfWeek = {"Thu_Hai", "Thu_Ba", "Thu_Tu", "Thu_Nam", "Thu_Sau", "Thu_Bay", "Chu_Nhat"};
        if (strings[0].equals("Hom_Nay")) { strings[0] = getDayOfWeek(); }
        List<String> daysOfWeekList = Arrays.asList(daysOfWeek);
        if (daysOfWeekList.contains(strings[0])) {
            int count = 0;
            Map<String, ExecutionCmd> Command = JsonReader.getDateTime(strings[0]);
            player.sendMessage("§6--------------------§e[" + strings[0] + "]§6--------------------");
            if (Command.entrySet().isEmpty()) { player.sendMessage("Solo yasour ko?"); return false;}
            for (Map.Entry<String, ExecutionCmd> entry : Command.entrySet()) {
                ExecutionCmd commandp = entry.getValue();
                ++count;
                DelLore.append(Component.text("\n /" + commandp.getCommand()));
                Component DelElement = Component.text(" §c[-]").clickEvent(ClickEvent.suggestCommand("/xoa_lenh " + strings[0] + " " + commandp.getName())).hoverEvent(HoverEvent.showText(DelLore));
                Component Message = Component.text("§e" + count + ". §f" + commandp.getName()).append(DelElement);
                player.sendMessage(Message);
            }
            player.sendMessage("§6--------------------§e[ Hết ]§6--------------------");
        }


        if (strings[0].equals("Tat_Ca")) {
              for (String day : daysOfWeek) {
                  Map<String, ExecutionCmd> Command = JsonReader.getDateTime(day);
                  if (!Command.isEmpty()) {
                      int count = 0;
                        player.sendMessage("§6--------------------§e[" + day + "]§6--------------------");
                        for (Map.Entry<String, ExecutionCmd> entry : Command.entrySet()) {
                            ExecutionCmd commandp = entry.getValue();
                            ++count;
                            DelLore.append(Component.text("\n /" + commandp.getCommand()));
                            Component DelElement = Component.text(" §c[-]").clickEvent(ClickEvent.suggestCommand("/xoa_lenh " + day + " " + commandp.getName())).hoverEvent(HoverEvent.showText(DelLore));
                            Component Message = Component.text("§e" + count + ". §f" + commandp.getName()).append(DelElement);
                            player.sendMessage(Message);
                        }

                  }
              }
          }
          return true;
      }
}