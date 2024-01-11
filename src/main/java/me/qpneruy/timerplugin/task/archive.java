package me.qpneruy.timerplugin.task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.qpneruy.timerplugin.TimerPro;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.apache.logging.log4j.LogManager.getLogger;

public class archive {
    private static final Logger logger = Logger.getLogger(archive.class.getName());
    private List<TimeData> timeDataList = new ArrayList<>();
    private final String filePath_Command;

    public archive() {
        this.filePath_Command = TimerPro.getPlugin().getDataFolder().getAbsolutePath() + "/command_data.json";
        File fileA = new File(filePath_Command);
        File_Check(fileA);
        loadFromFile(filePath_Command);
    }

    private void File_Check(File file) {
        try {
          if (!file.exists()) {
              if (file.createNewFile()) {
              getLogger().info("Đấm nhau không?");
              }
          }
        } catch (IOException e) {
        logger.log(Level.SEVERE, "Xảy ra lỗi khi tạo mới tệp", e);
        }
    }

    public void addTimeData(String time, String name) {
        TimeData timeData = new TimeData(time, name);
        timeDataList.add(timeData);
        saveToFile();
    }
    public void removeTimeData(int index) {
        this.timeDataList.remove(index);
        saveToFile();
    }

    public List<TimeData> getTimeDataList() {
        return timeDataList;
    }
    public void setTimdataList(int index, TimeData data) {
        timeDataList.set(index ,data);
        saveToFile();
    }

    private void loadFromFile(String file_path) {
        try (FileReader reader = new FileReader(file_path)) {
            Gson gson = new Gson();
            Type timeDataType = TypeToken.getParameterized(List.class, TimeData.class).getType();
            List<TimeData> loadedTimeDataList = gson.fromJson(reader, timeDataType);
            timeDataList = loadedTimeDataList != null ? loadedTimeDataList : new ArrayList<>();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Xảy ra lỗi khi đọc tệp: ", e);
        }
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter(filePath_Command)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(timeDataList);
            writer.write(json);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Xảy ra Lỗi khi Luư tệp: ", e);
        }
    }
}


