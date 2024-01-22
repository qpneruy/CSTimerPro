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
        LoadData();
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