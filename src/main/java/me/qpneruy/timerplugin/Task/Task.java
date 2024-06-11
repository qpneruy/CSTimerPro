package me.qpneruy.timerplugin.Task;

import me.qpneruy.timerplugin.TimerPro;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

import static me.qpneruy.timerplugin.Task.TimeCalibarate.*;

public class Task extends BukkitRunnable {
    private final TimerPro plugin;
    private final TaskManager task;
    private final archiver JsonReader = new archiver();

    public Task(TimerPro plugin) {
        this.plugin = plugin;
        this.task = new TaskManager(this.plugin);
    }

    public void run() {
        if (Isdisparity(getTime("ss"))) {
            Bukkit.getConsoleSender().sendMessage("§6[TimerPro]: Đã Hiệu Chỉnh Thời Gian!");
            Bukkit.getScheduler().cancelTasks(this.plugin);
            task.start();
        }
        this.ExecuteCommand(getTime("HH:mm"), getDayOfWeek());
        this.ExecuteCommand(getTime("HH:mm"), getTime("dd/MM/yyyy"));
    }

    private void ExecuteCommand(String Time, String DayOrDate) {
        Map<String, ExecutionCmd> Commands = JsonReader.getDateTime(DayOrDate);
        if (Commands == null) {return; }
        for (Map.Entry<String, ExecutionCmd> entry : Commands.entrySet()) {
            ExecutionCmd command = entry.getValue();
            if (command.isEnable() && command.getStartTime().equals(Time)) {
                boolean State = Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.getCommand());
                if (State) {
                    this.SendToOps("§6[TimerPro]: §fĐã thực thi lệnh: §e" + command.getCommand());
                } else {
                    this.SendToOps("§6[TimerPro]: §cLệnh: §e" + command.getCommand() + " §cKhông thể thực thi!");
                }
            }
        }
    }

    private void SendToOps(String message){
        for (Player ops : Bukkit.getOnlinePlayers()) {
            if (ops.isOp()) {
                ops.sendMessage(message);
            }
        }
    }
}


