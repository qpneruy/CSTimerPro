package me.qpneruy.timerplugin.Task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.qpneruy.timerplugin.TimerPro;
import me.qpneruy.timerplugin.Types.ExecutionCmd;
import me.qpneruy.timerplugin.serializer.ExecutionCmdDeserializer;
import me.qpneruy.timerplugin.serializer.ExecutionCmdSerializer;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static me.qpneruy.timerplugin.Task.JsonValidator.isValidJson;
import static me.qpneruy.timerplugin.Types.ExecutionCmd.isValidDate;

public class archiver {
    private static final Logger logger = Logger.getLogger(archiver.class.getName());

    private static final Map<String, Map<String, ExecutionCmd>> dayCommands = new HashMap<>();
    private static final Map<String, Map<String, ExecutionCmd>> dateCommands = new HashMap<>();
    private final String jsonPath;

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

    public int addCommand(ExecutionCmd cmd) {
        String dayOrDate = cmd.getExecDateTime();

        if (isValidDate(dayOrDate)) {
            dateCommands.computeIfAbsent(dayOrDate, k -> new HashMap<>()).put(cmd.getName(), cmd);
        } else {
            dayCommands.computeIfAbsent(dayOrDate, k -> new HashMap<>()).put(cmd.getName(), cmd);
        }

        return save() ? 0 : -3;
    }

    public boolean removeCommand(String dateTime, String Name) {
        ExecutionCmd tempCmd = new ExecutionCmd();
        tempCmd.setName(Name);
        tempCmd.setExecDateTime(dateTime);
        return removeCommand(tempCmd);
    }

    public boolean removeCommand(ExecutionCmd cmd) {
        String dateTime = cmd.getExecDateTime();
        String name = cmd.getName();
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