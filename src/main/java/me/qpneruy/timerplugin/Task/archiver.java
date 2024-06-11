package me.qpneruy.timerplugin.Task;

import com.google.gson.*;
import me.qpneruy.timerplugin.TimerPro;
import me.qpneruy.timerplugin.serializer.ExecutionCmdDeserializer;
import me.qpneruy.timerplugin.serializer.ExecutionCmdSerializer;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static me.qpneruy.timerplugin.Task.ExecutionCmd.isValidDate;
import static me.qpneruy.timerplugin.Task.ExecutionCmd.isValidDayOfWeek;
import static me.qpneruy.timerplugin.Task.JsonValidator.isValidJson;

public class archiver {
    private static final Logger logger = Logger.getLogger(archiver.class.getName());
    private final Map<String, Map<String, ExecutionCmd>> DayCommands = new HashMap<>();
    private final Map<String, Map<String, ExecutionCmd>> DateCommands = new HashMap<>();
    public final String jsonPath;

    public archiver() {
        this.jsonPath = TimerPro.getPlugin().getDataFolder().getAbsolutePath() + "/Commands.json";
//        this.jsonPath = "D:\\Project\\Java\\TimerPluginX\\src\\main\\resources\\Commands.json";
        File file = new File(jsonPath);
        if (!file.exists()) { initializeEmptyJson(); }
        Load();
    }
    public Map<String, ExecutionCmd> getDateTime(String DayOrDate) {
        if (isValidDate(DayOrDate))
            return DateCommands.get(DayOrDate);
        return DayCommands.get(DayOrDate);
    }
    public byte AddCommand(String Name, String Command, String DayOrDate, String StartTime) {
        // -1: Loi Dinh Dang Thoi Gian
        // -2: Chua Dat Thoi Gian Ket Thuc
        // -3: Loi Khi Luu Du Lieu
        ExecutionCmd CmdType = new ExecutionCmd();
        if (!CmdType.init(Name, Command, DayOrDate, StartTime)) {
            return -1;
        }
        if (!isValidDayOfWeek(DayOrDate)) {
            this.DateCommands.computeIfAbsent(DayOrDate, k -> new HashMap<>());
            this.DateCommands.get(DayOrDate).put(Name, CmdType);
        } else {
            this.DayCommands.computeIfAbsent(DayOrDate, k -> new HashMap<>());
            this.DayCommands.get(DayOrDate).put(Name, CmdType);
        }
        if(Save()) return 0; else return -3;
    }

    public boolean RemoveCommand(String DateTime, String Name) {
        boolean found = false;
        if (isValidDate(DateTime)) {
            if (DateCommands.containsKey(DateTime)) {
                DateCommands.get(DateTime).remove(Name);
                found = true;
            }
        } else {
            if (DayCommands.containsKey(DateTime)) {
                DayCommands.get(DateTime).remove(Name);
                found = true;
            }
        }
        return found && Save();
    }

    private void initializeEmptyJson() {
        try (FileWriter writer = new FileWriter(jsonPath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject jsonObject = new JsonObject();
            JsonObject dayOfWeekJson = new JsonObject();
            dayOfWeekJson.add("Moi_Ngay", gson.toJsonTree(DayCommands.getOrDefault("Moi_Ngay", new HashMap<>())));
            dayOfWeekJson.add("Thu_Hai", gson.toJsonTree(DayCommands.getOrDefault("Thu_Hai", new HashMap<>())));
            dayOfWeekJson.add("Thu_Ba", gson.toJsonTree(DayCommands.getOrDefault("Thu_Ba", new HashMap<>())));
            dayOfWeekJson.add("Thu_Tu", gson.toJsonTree(DayCommands.getOrDefault("Thu_Tu", new HashMap<>())));
            dayOfWeekJson.add("Thu_Nam", gson.toJsonTree(DayCommands.getOrDefault("Thu_Nam", new HashMap<>())));
            dayOfWeekJson.add("Thu_Sau", gson.toJsonTree(DayCommands.getOrDefault("Thu_Sau", new HashMap<>())));
            dayOfWeekJson.add("Thu_Bay", gson.toJsonTree(DayCommands.getOrDefault("Thu_Bay", new HashMap<>())));
            dayOfWeekJson.add("Chu_Nhat", gson.toJsonTree(DayCommands.getOrDefault("Chu_Nhat", new HashMap<>())));
            jsonObject.add("DayOfWeek", dayOfWeekJson);
            JsonObject dateCommandsJson = new JsonObject();
            jsonObject.add("Dates", dateCommandsJson);
            String json = gson.toJson(jsonObject);
            writer.write(json);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save data to JSON file", e);
        }
    }
    public boolean Load() {
        if (!isValidJson(jsonPath)) return false;
        try (Reader reader = new FileReader(jsonPath)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(ExecutionCmd.class, new ExecutionCmdDeserializer())
                    .create();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            if (jsonObject.has("DayOfWeek")) {
                JsonObject dayOfWeekJson = jsonObject.getAsJsonObject("DayOfWeek");
                for (Map.Entry<String, JsonElement> dayEntry : dayOfWeekJson.entrySet()) {
                    Map<String, ExecutionCmd> dayCmds = new HashMap<>();
                    if (dayEntry.getValue().isJsonObject()) {
                        JsonObject commandsForDay = dayEntry.getValue().getAsJsonObject();
                        for (Map.Entry<String, JsonElement> cmdEntry : commandsForDay.entrySet()) {
                            ExecutionCmd cmd = gson.fromJson(cmdEntry.getValue(), ExecutionCmd.class);
                            cmd.setName(cmdEntry.getKey());
                            dayCmds.put(cmdEntry.getKey(), cmd);
                        }
                    }
                    DayCommands.put(dayEntry.getKey(), dayCmds);
                }
            }
            if (jsonObject.has("Dates")) {
                JsonObject dateCommandsJson = jsonObject.getAsJsonObject("Dates");
                for (Map.Entry<String, JsonElement> DateEntry : dateCommandsJson.entrySet()) {
                    Map<String, ExecutionCmd> DateCmds = new HashMap<>();
                    if (DateEntry.getValue().isJsonObject()) {
                        JsonObject commandsForDay = DateEntry.getValue().getAsJsonObject();
                        for (Map.Entry<String, JsonElement> cmdEntry : commandsForDay.entrySet()) {
                            ExecutionCmd cmd = gson.fromJson(cmdEntry.getValue(), ExecutionCmd.class);
                            DateCmds.put(cmdEntry.getKey(), cmd);
                        }
                    }
                    DateCommands.put(DateEntry.getKey(), DateCmds);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load data from JSON file", e);
            return false;
        }
        return true;
    }

    public boolean Save() {
        try (FileWriter writer = new FileWriter(jsonPath)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(ExecutionCmd.class, new ExecutionCmdSerializer())
                    .setPrettyPrinting()
                    .create();
            JsonObject jsonObject = new JsonObject();
            JsonObject dayOfWeekJson = new JsonObject();
            for (Map.Entry<String, Map<String, ExecutionCmd>> entry : DayCommands.entrySet()) {
                JsonObject commandsForDay = new JsonObject();
                for (Map.Entry<String, ExecutionCmd> cmdEntry : entry.getValue().entrySet()) {
                    commandsForDay.add(cmdEntry.getKey(), gson.toJsonTree(cmdEntry.getValue()));
                }
                dayOfWeekJson.add(entry.getKey(), commandsForDay);
            }
            jsonObject.add("DayOfWeek", dayOfWeekJson);
            JsonObject dateCommandsJson = new JsonObject();
            for (Map.Entry<String, Map<String, ExecutionCmd>> entry : DateCommands.entrySet()) {
                JsonObject commandsForDay = new JsonObject();
                for (Map.Entry<String, ExecutionCmd> cmdEntry : entry.getValue().entrySet()) {
                    commandsForDay.add(cmdEntry.getKey(), gson.toJsonTree(cmdEntry.getValue()));
                }
                dateCommandsJson.add(entry.getKey(), commandsForDay);
            }
            jsonObject.add("Dates", dateCommandsJson);
            String json = gson.toJson(jsonObject);
            writer.write(json);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save data to JSON file", e);
            return false;
        }
        return true;
    }
}
