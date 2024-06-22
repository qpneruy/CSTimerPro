package me.qpneruy.timerplugin.Task;

import me.qpneruy.timerplugin.TimerPro;
import me.qpneruy.timerplugin.Types.ExecutionCmd;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

import static me.qpneruy.timerplugin.Task.TimeCalibarate.*;
import static org.bukkit.Bukkit.getServer;

public class Task extends BukkitRunnable {
    private final TimerPro plugin;
    private final TaskManager taskManager;
    private final archiver jsonReader = new archiver();

    public Task(TimerPro plugin) {
        this.plugin = plugin;
        this.taskManager = new TaskManager(this.plugin);
    }

    @Override
    public void run() {
        if (!isDisparity(getTime("ss"))) {
            Bukkit.getConsoleSender().sendMessage("§6[TimerPro]: Đã Hiệu Chỉnh Thời Gian!");
            Bukkit.getScheduler().cancelTasks(this.plugin);
            getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, taskManager::start, 1L);
            return;
        }

        String currentTime = getTime("HH:mm");
        String currentDayOfWeek = getDayOfWeek();
        String currentDate = getTime("dd/MM/yyyy");

        executeCommands(currentTime, currentDayOfWeek);
        executeCommands(currentTime, "Moi_Ngay");
        executeCommands(currentTime, currentDate);
    }


    private void executeCommands(String time, String dayOrDate) {
        Map<String, ExecutionCmd> commands = jsonReader.getCommands(dayOrDate);
        if (commands == null) {
            return;
        }

        commands.forEach((name, command) -> {
            if (command.isEnabled() && command.getStartTime().equals(time)) {
                boolean success = Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.getCommand());
                if (success) {
                    sendToOps("§6[TimerPro]: §fĐã thực thi lệnh: §e" + command.getCommand());
                } else {
                    sendToOps("§6[TimerPro]: §cLệnh: §e" + command.getCommand() + " §cKhông thể thực thi!");
                }
            }
        });
    }


    private void sendToOps(String message) {
        Bukkit.getOnlinePlayers().stream()
                .filter(Player::isOp)
                .forEach(ops -> ops.sendMessage(message));
    }
}