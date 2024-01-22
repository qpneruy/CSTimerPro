package me.qpneruy.timerplugin.command;

import me.qpneruy.timerplugin.TimerPro;
import me.qpneruy.timerplugin.task.timerTask;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class syncTime implements CommandExecutor {
    private final TimerPro plugin;

    public syncTime(TimerPro plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender Sender, @NotNull Command command, @NotNull String label, String[] Args) {
        Player player = (Player) Sender;
        if (!player.hasPermission("TimerPro.Sync_Time")) {
            player.sendMessage("§6[TimerPro]: §cBạn không có quyền sử dụng lệnh này!");
        }
        timerTask task = new timerTask(this.plugin);
        Bukkit.getScheduler().cancelTasks(this.plugin);
        task.Run_Task();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.now();
        String currentTime = dtf.format(localTime);
        player.sendMessage("§6[TimerPro]: §fĐã hiệu chỉnh thời gian: " + currentTime);
        return false;
    }
}
