package me.qpneruy.timerplugin.Commands;

import me.qpneruy.timerplugin.Gui.Menu;
import me.qpneruy.timerplugin.Task.archiver;
import me.qpneruy.timerplugin.Types.ExecutionCmd;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static me.qpneruy.timerplugin.Gui.Editor.Editor;
import static me.qpneruy.timerplugin.Task.TimeCalibarate.getDayOfWeek;

public class EditSchedCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage("Lệnh này chỉ có thể thực hiện trong game!");
            return false;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("TImerPro.sualenh")) {
            player.sendMessage("§6[TimerPro]: §cBạn không có quyền sử dụng lệnh này!");
            return false;
        }
        if (args.length < 2) {
            player.sendMessage("§6[TimerPro]: §cTruyền tham số vào đê!");
            return false;
        }
        archiver editCmd = new archiver();
        String targetDay = args[0].equals("Hom_Nay") ? getDayOfWeek() : args[0];
        String commandName = args[1];
        Map<String, ExecutionCmd> cmds = editCmd.getCommands(targetDay);

        for (Map.Entry<String, ExecutionCmd> entry : cmds.entrySet()) {
            ExecutionCmd cmd = entry.getValue();
            if (Objects.equals(cmd.getName(), commandName)) {
                Menu.addPlayerCommand(player, cmd, editCmd);
                Inventory inventory = Editor(cmd);
                Map<String, Inventory> gui = new HashMap<>();
                gui.put("page_1", inventory);
                Menu.addPlayerGui(player, gui);
                player.openInventory(inventory);
                return true;
            }
        }
        player.sendMessage("§6[TimerPro]: §cKhông tìm thấy lệnh cần chỉnh sửa!");
        return false;
    }
}