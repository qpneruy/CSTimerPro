//package me.qpneruy.timerplugin.command;
//
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandExecutor;
//import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Player;
//import org.jetbrains.annotations.NotNull;
//
//import static org.bukkit.Bukkit.getServer;
//
//public class reload_omp implements CommandExecutor {
//
//    @Override
//    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
//        Player player = (Player) commandSender;
//        if (player.hasPermission("TimerPro.freload")) {
//            getServer().dispatchCommand(getServer().getConsoleSender(), "reload");
//            getServer().dispatchCommand(getServer().getConsoleSender(), "reload confirm");
//        } else {
//            player.sendMessage("Bạn không có quyền sử dụng lệnh này!");
//        }
//        return false;
//    }
//}
