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
import java.util.List;

public class archiver {
    private static final Logger logger = Logger.getLogger(archiver.class.getName());
    private Map<String, List<TimeType>> timerData = new HashMap<>();
    public final String jsonPath;

    public archiver() {
        this.jsonPath = TimerPro.getPlugin().getDataFolder().getAbsolutePath() + "/command_data.json";
        LoadData();
    }



    public void AddCommand(String DayOfWeek, String time, String Command) {
        TimeType TimeData = new TimeType(time, Command);
        List<TimeType> DayTimeData = this.timerData.get(DayOfWeek);
        DayTimeData.add(TimeData);
        SaveData();
    }
    public void RemoveCommand(String DayOfWeek, int index) {
        List<TimeType> DayTimeData = this.timerData.get(DayOfWeek);
        DayTimeData.remove(index);
        SaveData();
    }

    public List<TimeType> getDayTimeList(String DayOfWeek) {
        return this.timerData.get(DayOfWeek);
    }
    public void setDayTimeData(String DayOfWeek, int index, TimeType TimeData) {
        List<TimeType> DayTimeData = this.timerData.get(DayOfWeek);
        DayTimeData.set(index, TimeData);
        SaveData();
    }

    public void LoadData() {
        try (FileReader reader = new FileReader(jsonPath)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, List<TimeType>>>() {}.getType();
            this.timerData = gson.fromJson(reader, type);
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Không tìm thấy tệp", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Không thể đọc tệp", e);
        } catch (com.google.gson.JsonSyntaxException e) {
            logger.log(Level.SEVERE, "Cú pháp tệp Json không hợp lệ", e);
        }
    }

    public void SaveData() {
        try (FileWriter writer = new FileWriter(jsonPath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(timerData);
            writer.write(json);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Lỗi: ", e);
        }
    }
}