package me.qpneruy.timerplugin.Task;

import com.google.gson.*;
import me.qpneruy.timerplugin.TimerPro;
import me.qpneruy.timerplugin.serializer.ExecutionCmdDeserializer;
import me.qpneruy.timerplugin.serializer.ExecutionCmdSerializer;
import org.bukkit.Bukkit;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static me.qpneruy.timerplugin.Task.ExecutionCmd.isValidDate;
import static me.qpneruy.timerplugin.Task.JsonValidator.isValidJson;

/**
 * Lớp chịu trách nhiệm lưu trữ và quản lý các lệnh hẹn giờ (ExecutionCmd) vào file JSON.
 */
public class archiver {
    private static final Logger logger = Logger.getLogger(archiver.class.getName());

    private final Map<String, Map<String, ExecutionCmd>> dayCommands = new HashMap<>();
    private final Map<String, Map<String, ExecutionCmd>> dateCommands = new HashMap<>();
    private final String jsonPath;

    /**
     * Khởi tạo đối tượng archiver và tải dữ liệu từ file JSON.
     */
    public archiver() {
        this.jsonPath = TimerPro.getPlugin().getDataFolder().getAbsolutePath() + "/Commands.json";
        File file = new File(jsonPath);
        if (!file.exists()) {
            initializeEmptyJson();
        }
        load();
    }

    /**
     * Lấy danh sách lệnh theo ngày hoặc theo ngày tháng cụ thể.
     *
     * @param dayOrDate Chuỗi đại diện cho ngày hoặc ngày tháng.
     * @return Map chứa các lệnh ứng với ngày/ngày tháng.
     */
    public Map<String, ExecutionCmd> getCommands(String dayOrDate) {
        if (isValidDate(dayOrDate)) {
            return dateCommands.getOrDefault(dayOrDate, Collections.emptyMap());
        }
        return dayCommands.getOrDefault(dayOrDate, Collections.emptyMap());
    }

    /**
     * Thêm một lệnh hẹn giờ mới.
     *
     * @param name         Tên của lệnh.
     * @param command     Nội dung lệnh.
     * @param dayOrDate    Ngày hoặc ngày tháng thực hiện lệnh.
     * @param startTime    Thời gian bắt đầu thực hiện lệnh.
     * @return 0 nếu thêm thành công, -1 nếu lỗi định dạng thời gian, -3 nếu lỗi lưu dữ liệu.
     */
    public int addCommand(String name, String command, String dayOrDate, String startTime) {
        ExecutionCmd cmd = new ExecutionCmd();
        if (!cmd.init(name, command, dayOrDate, startTime)) {
            return -1;
        }

        if (isValidDate(dayOrDate)) {
            dateCommands.computeIfAbsent(dayOrDate, k -> new HashMap<>()).put(name, cmd);
        } else {
            dayCommands.computeIfAbsent(dayOrDate, k -> new HashMap<>()).put(name, cmd);
        }

        return save() ? 0 : -3;
    }

    /**
     * Xóa một lệnh hẹn giờ.
     *
     * @param dateTime Ngày giờ của lệnh cần xóa.
     * @param name     Tên của lệnh cần xóa.
     * @return true nếu xóa thành công, false nếu không tìm thấy lệnh.
     */
    public boolean removeCommand(String dateTime, String name) {
        boolean removed = false;
        if (isValidDate(dateTime)) {
            Map<String, ExecutionCmd> commands = dateCommands.get(dateTime);
            if (commands != null) {
                commands.remove(name);
                removed = true;
            }
        } else {
            Map<String, ExecutionCmd> commands = dayCommands.get(dateTime);
            if (commands != null) {
                commands.remove(name);
                removed = true;
            }
        }
        return removed && save();
    }

    /**
     * Khởi tạo file JSON với dữ liệu trống.
     */
    private void initializeEmptyJson() {
        try (FileWriter writer = new FileWriter(jsonPath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject jsonObject = new JsonObject();

            JsonObject dayOfWeekJson = new JsonObject();
            Arrays.asList("Moi_Ngay", "Thu_Hai", "Thu_Ba", "Thu_Tu", "Thu_Nam", "Thu_Sau", "Thu_Bay", "Chu_Nhat")
                    .forEach(day -> dayOfWeekJson.add(day, gson.toJsonTree(new HashMap<>())));
            jsonObject.add("DayOfWeek", dayOfWeekJson);

            jsonObject.add("Dates", new JsonObject());

            writer.write(gson.toJson(jsonObject));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Lỗi khi lưu dữ liệu vào file JSON", e);
        }
    }

    /**
     * Tải dữ liệu từ file JSON.
     *
     * @return true nếu tải thành công, false nếu gặp lỗi.
     */
    public boolean load() {
        if (!isValidJson(jsonPath)) {
            return false;
        }

        try (Reader reader = new FileReader(jsonPath)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(ExecutionCmd.class, new ExecutionCmdDeserializer())
                    .create();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

            loadCommandsFromJson(jsonObject, "DayOfWeek", dayCommands);
            loadCommandsFromJson(jsonObject, "Dates", dateCommands);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Lỗi khi tải dữ liệu từ file JSON", e);
            return false;
        }
        return true;
    }

    /**
     * Hàm phụ trợ để tải dữ liệu lệnh từ JSON object.
     */
    private void loadCommandsFromJson(JsonObject jsonObject, String type, Map<String, Map<String, ExecutionCmd>> commandsMap) {
        if (jsonObject.has(type)) {
            JsonObject commandsJson = jsonObject.getAsJsonObject(type);
            for (Map.Entry<String, JsonElement> dateEntry : commandsJson.entrySet()) {
                Map<String, ExecutionCmd> commandsForDay = new HashMap<>();
                if (dateEntry.getValue().isJsonObject()) {
                    JsonObject commandsForDateJson = dateEntry.getValue().getAsJsonObject();
                    for (Map.Entry<String, JsonElement> cmdEntry : commandsForDateJson.entrySet()) {
                        ExecutionCmd cmd = new GsonBuilder()
                                .registerTypeAdapter(ExecutionCmd.class, new ExecutionCmdDeserializer())
                                .create()
                                .fromJson(cmdEntry.getValue(), ExecutionCmd.class);
                        cmd.setName(cmdEntry.getKey());
                        cmd.setExecDateTime(dateEntry.getKey());
                        commandsForDay.put(cmdEntry.getKey(), cmd);
                    }
                }
                commandsMap.put(dateEntry.getKey(), commandsForDay);
            }
        }
    }
    /**
     * Lưu dữ liệu vào file JSON.
     *
     * @return true nếu lưu thành công, false nếu gặp lỗi.
     */
    public boolean save() {
        try (FileWriter writer = new FileWriter(jsonPath)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(ExecutionCmd.class, new ExecutionCmdSerializer())
                    .setPrettyPrinting()
                    .create();
            JsonObject jsonObject = new JsonObject();

            jsonObject.add("DayOfWeek", serializeCommandsToJson(dayCommands, gson));
            jsonObject.add("Dates", serializeCommandsToJson(dateCommands, gson));

            writer.write(gson.toJson(jsonObject));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Lỗi khi lưu dữ liệu vào file JSON", e);
            return false;
        }
        return true;
    }

    /**
     * Hàm phụ trợ để chuyển đổi dữ liệu lệnh sang JSON object.
     */
    private JsonObject serializeCommandsToJson(Map<String, Map<String, ExecutionCmd>> commandsMap, Gson gson) {
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry<String, Map<String, ExecutionCmd>> entry : commandsMap.entrySet()) {
            JsonObject commandsForDay = new JsonObject();
            for (Map.Entry<String, ExecutionCmd> cmdEntry : entry.getValue().entrySet()) {
                commandsForDay.add(cmdEntry.getKey(), gson.toJsonTree(cmdEntry.getValue()));
            }
            jsonObject.add(entry.getKey(), commandsForDay);
        }
        return jsonObject;
    }
}

/*
 * Những thay đổi đã được thực hiện:
 *
 * - Tối ưu hóa getCommands: Sử dụng getOrDefault để tránh NullPointerException khi không tìm thấy key.
 * - Chia nhỏ method: Tách load và save thành các method nhỏ hơn để dễ đọc và bảo trì hơn.
 * - Sử dụng forEach: Thay thế vòng lặp for bằng forEach khi có thể để code ngắn gọn hơn.
 * - Thêm comment: Bổ sung comment giải thích chi tiết cho từng method và các phần logic quan trọng.
 * - Chuẩn hóa tên biến: Đổi tên biến thành camelCase để tuân thủ chuẩn Java.
 * - Sử dụng try-with-resources: Đảm bảo file luôn được đóng đúng cách sau khi sử dụng.
 * - Rút gọn code: Loại bỏ code dư thừa và lặp lại.
 */