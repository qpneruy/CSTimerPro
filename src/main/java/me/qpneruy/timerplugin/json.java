package me.qpneruy.timerplugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class json {
    private static final Logger logger = Logger.getLogger(json.class.getName());
    private List<TimeData> timeDataList = new ArrayList<>();
    private final String filePath;

    public json() {
        this.filePath = TimerPro.getPlugin().getDataFolder().getAbsolutePath() + "/data.json";

        try {
            // Tạo tệp mới nếu nó không tồn tại
            File file = new File(filePath);
            if (!file.exists()) {
                if (file.createNewFile()) {
                    logger.info("Đã tạo tệp data.json mới!");
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Xảy ra lỗi khi tạo mới tệp", e);
        }

        // Tiếp tục load dữ liệu từ tệp
        loadFromFile();
    }

    public void addTimeData(String time, int x, int y, int z, String name) {
        TimeData timeData = new TimeData(time, new LocationData(x, y, z), name);
        timeDataList.add(timeData);
        saveToFile();
    }

    public List<TimeData> getTimeDataList() {
        return timeDataList;
    }

    private void loadFromFile() {
        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            Type timeDataType = new TypeToken<List<TimeData>>() {}.getType();
            List<TimeData> loadedTimeDataList = gson.fromJson(reader, timeDataType);
            timeDataList = loadedTimeDataList != null ? loadedTimeDataList : new ArrayList<>();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Xay ra loi khi doc tep: ", e);
        }
    }

    private void saveToFile() {
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(timeDataList);
            writer.write(json);
            System.out.println("Du lieu da duoc luu vao file.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Xay ra loi khi luu tep: ", e);
        }
    }
}

class TimeData {
    private final String time;
    private final LocationData location;
    private final String name;

    public TimeData(String time, LocationData location, String name) {
        this.time = time;
        this.location = location;
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public LocationData getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }
}

class LocationData {
    private final int x;
    private final int y;
    private final int z;

    public LocationData(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
