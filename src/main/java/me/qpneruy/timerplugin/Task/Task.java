package me.qpneruy.timerplugin.Task;

import me.qpneruy.timerplugin.TimerPro;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

import static me.qpneruy.timerplugin.Task.TimeCalibarate.*;

/**
 * Lớp đại diện cho một tác vụ chạy định kỳ để thực thi các lệnh hẹn giờ.
 */
public class Task extends BukkitRunnable {
    private final TimerPro plugin;
    private final TaskManager taskManager;
    private final archiver jsonReader = new archiver();

    /**
     * Khởi tạo một tác vụ mới.
     *
     * @param plugin Plugin chính.
     */
    public Task(TimerPro plugin) {
        this.plugin = plugin;
        this.taskManager = new TaskManager(this.plugin);
    }

    /**
     * Phương thức được gọi định kỳ để kiểm tra và thực thi các lệnh hẹn giờ.
     */
    @Override
    public void run() {
        // Kiểm tra sự chênh lệch thời gian và hiệu chỉnh nếu cần thiết.
        if (!isDisparity(getTime("ss"))) {
            Bukkit.getConsoleSender().sendMessage("§6[TimerPro]: Đã Hiệu Chỉnh Thời Gian!");
            Bukkit.getScheduler().cancelTasks(this.plugin);
            taskManager.start();
            return; // Thoát sau khi hiệu chỉnh thời gian
        }

        // Lấy thời gian hiện tại và ngày/thứ trong tuần.
        String currentTime = getTime("HH:mm");
        String currentDayOfWeek = getDayOfWeek();
        String currentDate = getTime("dd/MM/yyyy");

        // Thực thi các lệnh theo thời gian và ngày/thứ.
        executeCommands(currentTime, currentDayOfWeek);
        executeCommands(currentTime, currentDate);
    }

    /**
     * Thực thi các lệnh hẹn giờ dựa vào thời gian và ngày/ngày tháng.
     *
     * @param time      Thời gian hiện tại (HH:mm).
     * @param dayOrDate Ngày hoặc ngày tháng (ví dụ: "Thứ Hai" hoặc "dd/MM/yyyy").
     */
    private void executeCommands(String time, String dayOrDate) {
        Map<String, ExecutionCmd> commands = jsonReader.getCommands(dayOrDate);
        if (commands == null) {
            return; // Không có lệnh nào cho ngày/ngày tháng này.
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

    /**
     * Gửi tin nhắn đến tất cả người chơi có quyền OP.
     *
     * @param message Tin nhắn cần gửi.
     */
    private void sendToOps(String message) {
        Bukkit.getOnlinePlayers().stream()
                .filter(Player::isOp)
                .forEach(ops -> ops.sendMessage(message));
    }
}