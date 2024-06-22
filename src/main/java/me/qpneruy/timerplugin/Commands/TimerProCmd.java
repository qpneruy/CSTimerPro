package me.qpneruy.timerplugin.Commands;

import me.qpneruy.timerplugin.Gui.Menu;
import me.qpneruy.timerplugin.Task.archiver;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static me.qpneruy.timerplugin.Gui.Main.mainGui;

public class TimerProCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            Bukkit.getConsoleSender().sendMessage("Lệnh này chỉ có thể sử dụng trong game!");
            return false;
        }
        if (!player.hasPermission("TimerPro.timerpro")) {
            player.sendMessage("§6[TimerPro]: §cBạn không có quyền sử dụng lệnh này!");
            return false;
        }
        if (strings.length < 1){
            player.sendMessage("§6[TimerPro]: §cTruyền tham số vào đê!");
            return false;
        }
        Menu.playerCurrentPage.put(player, Integer.valueOf(strings[0]));
        archiver Reader = new archiver();
        Map<String, Inventory> gui = mainGui(Reader);
        Menu.addPlayerGui(player, gui);
        if (gui.get("page_" + Menu.playerCurrentPage.get(player)) == null) {
            
        }
        player.openInventory(gui.get("page_" + Menu.playerCurrentPage.get(player)));
        return true;
    }
}
