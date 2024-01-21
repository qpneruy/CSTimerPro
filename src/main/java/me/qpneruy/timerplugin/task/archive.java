package me.qpneruy.timerplugin.task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.qpneruy.timerplugin.TimerPro;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class archive {
    private static final Logger logger = Logger.getLogger(archive.class.getName());
    private Map<String, List<TimeData>> timerData = new HashMap<>();
    public final String jsonPath;

    public archive() {
        this.jsonPath = TimerPro.getPlugin().getDataFolder().getAbsolutePath() + "/command_data.json";
        File fileA = new File(jsonPath);
        fileCheck(fileA);
        LoadData();
    }
    private static Map<String, List<TimeData>> defaultData() {
        Map<String, List<TimeData>> scheduleMap = new HashMap<>();
        scheduleMap.put("Everyday", new ArrayList<>());
        scheduleMap.put("Sunday", new ArrayList<>());
        scheduleMap.put("Monday", new ArrayList<>());
        scheduleMap.put("Tuesday", new ArrayList<>());
        scheduleMap.put("Wednesday", new ArrayList<>());
        scheduleMap.put("Thursday", new ArrayList<>());
        scheduleMap.put("Friday", new ArrayList<>());
        scheduleMap.put("Saturday", new ArrayList<>());
        return scheduleMap;
    }

    private void fileCheck(File file) {
        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String json = gson.toJson(defaultData());
                    try (FileWriter writer = new FileWriter(jsonPath)) {
                        writer.write(json);
                        logger.log(Level.WARNING, "No command_data file exists, created a new one");
                    } catch (IOException e) {
                        logger.log(Level.SEVERE, "Error writing to command_data file", e);
                    }
                    logger.info("Đấm nhau không?");
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error creating new file", e);
        }
    }
    public void AddCommand(String DayOfWeek, String time, String Command) {
        TimeData TimeData = new TimeData(time, Command);
        List<TimeData> DayTimeData = this.timerData.get(DayOfWeek);
        DayTimeData.add(TimeData);
        SaveData();
    }
    public void RemoveCommand(String DayOfWeek, int index) {
        List<TimeData> DayTimeData = this.timerData.get(DayOfWeek);
        DayTimeData.remove(index);
        SaveData();
    }

    public List<TimeData> getDayTimeList(String DayOfWeek) {
        return this.timerData.get(DayOfWeek);
    }
    public void setDayTimeData(String DayOfWeek, int index, TimeData TimeData) {
        List<TimeData> DayTimeData = this.timerData.get(DayOfWeek);
        DayTimeData.set(index, TimeData);
        SaveData();
    }

    public void LoadData() {
        try (FileReader reader = new FileReader(jsonPath)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, List<TimeData>>>() {}.getType();
            this.timerData = gson.fromJson(reader, type);
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "File not found", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file", e);
        } catch (com.google.gson.JsonSyntaxException e) {
            logger.log(Level.SEVERE, "Error parsing JSON syntax", e);
        }
    }

    public void SaveData() {
        try (FileWriter writer = new FileWriter(jsonPath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(timerData);
            writer.write(json);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while save data: ", e);
        }
    }
}