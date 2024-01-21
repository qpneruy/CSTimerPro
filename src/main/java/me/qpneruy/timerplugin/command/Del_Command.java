//package me.qpneruy.timerplugin.command;
//
//import me.qpneruy.timerplugin.task.archive;
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandExecutor;
//import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Player;
//import org.jetbrains.annotations.NotNull;
//
//public class Del_Command implements CommandExecutor {
//    @Override
//    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
//        Player player = (Player) commandSender;
//        if (!player.hasPermission("TimerPro.Xoa_Lenh")) {
//            player.sendMessage("§6[TimerPro]: §cBạn không có quyền sử dụng lệnh này!");
//        }
//        archive JsonDel = new archive();
//        int index = Integer.parseInt(strings[0]) - 1;
//        if (index < 0 || index > JsonDel.getTimeDataList().size()) {
//            player.sendMessage("§6[TimerPro]: §fVị trí không hợp lệ!");
//            return false;
//        }
//        JsonDel.removeTimeData(index);
//        player.sendMessage("§6[TimerPro]: §fĐã xóa Lệnh thành công!");
//        return true;
//    }
//}
