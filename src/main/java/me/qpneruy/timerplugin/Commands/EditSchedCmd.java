package me.qpneruy.timerplugin.Commands;

import me.qpneruy.timerplugin.Events.Menu;
import me.qpneruy.timerplugin.Task.ExecutionCmd;
import me.qpneruy.timerplugin.Task.archiver;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

public class EditSchedCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage("Lệnh này chỉ có thể thực hiện trong game!");
            return false;
        }
        Player player = (Player)commandSender;
        if (!player.hasPermission("TImerPro.Edit")) {
            player.sendMessage("§6[TimerPro]: §cBạn không có quyền sử dụng lệnh này!");
            return false;
        }
        if (strings.length < 1) {
            player.sendMessage("§6[TimerPro]: §cTruyền tham số vào đê!");
            return false;
        }
        archiver EditCmd = new archiver();
        Map<String, ExecutionCmd> cmds = EditCmd.getDateTime(strings[0]);
        for (Map.Entry<String, ExecutionCmd> entry : cmds.entrySet()) {
            ExecutionCmd cmd = entry.getValue();
            if (Objects.equals(cmd.getName(), strings[1])) {
                Gui EditorMenu = new Gui();
                Inventory inventory = EditorMenu.Editor(cmd);
                Menu.addPlayer(player, inventory, cmd);
                player.openInventory(inventory);
            }
        }
        return true;
    }
}
