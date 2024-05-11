package me.qpneruy.timerplugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.qpneruy.timerplugin.Command.*;
import me.qpneruy.timerplugin.Command.TabCompleter.AddSchedCmd;
import me.qpneruy.timerplugin.Command.TabCompleter.DeleteSchedCmd;
import me.qpneruy.timerplugin.Command.TabCompleter.ShowSchedCmd;
import me.qpneruy.timerplugin.Command.TabCompleter.SchedCmdOperations;
import me.qpneruy.timerplugin.Placeholder.Expansion;
import me.qpneruy.timerplugin.task.TimeType;
import me.qpneruy.timerplugin.task.archiver;
import me.qpneruy.timerplugin.task.TaskStarting;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class TimerPro extends JavaPlugin {
    private static TimerPro plugin;
    private static final Logger logger = Logger.getLogger(archiver.class.getName());
    public static TimerPro getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        boolean tinhDepTrai = getConfig().getBoolean("TinhDepTrai", true);
        getLogger().info("-------------------------------");
        getLogger().info("||     Đã khởi động Plugin   ||___________________________");
        getLogger().info("|| Make by: Supercalifragilisticexpialidocious configer ||");
        getLogger().info("----------------------------------------------------------");
        getLogger().info("    _____ ___ __  __ _____ ____  ____  ____   ___  ");
        getLogger().info("   |_   _|_ _|  \\/  | ____|  _ \\|  _ \\|  _ \\ / _ \\ ");
        getLogger().info("     | |  | || |\\/| |  _| | |_) | |_) | |_) | | | |");
        getLogger().info("     | |  | || |  | | |___|  _ <|  __/|  _ <| |_| |");
        getLogger().info("     |_| |___|_|  |_|_____|_| \\_\\_|   |_| \\_\\\\___/ ");
        getLogger().info("                                                    ");
        if (!tinhDepTrai) {
            Bukkit.getServer().getConsoleSender().sendMessage("§cTinhDepTrai is set to false!!!.");
            Bukkit.getServer().getConsoleSender().sendMessage("§cPlease set 'TinhDepTrai' to true to continue using this plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        File file = new File(TimerPro.getPlugin().getDataFolder().getAbsolutePath() + "/command_data.json");
        this.ReadyUp(file);
        this.loadCommand();
        this.loadTabcompleter();
        new Expansion().register();
        this.registerTask();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("-------------------------------");
        getLogger().info("||       Đã dừng PLugin      ||");
        getLogger().info("-------------------------------");
    }
    private void loadCommand() {
        Objects.requireNonNull(getCommand("Sync_Time")).setExecutor(new SyncPluginTime(this));
        Objects.requireNonNull(getCommand("Xem_Lenh")).setExecutor(new me.qpneruy.timerplugin.Command.ShowSchedCmd());
        Objects.requireNonNull(getCommand("Them_Lenh")).setExecutor(new me.qpneruy.timerplugin.Command.AddSchedCmd());
        Objects.requireNonNull(getCommand("Doi_DuLieu")).setExecutor(new me.qpneruy.timerplugin.Command.SchedCmdOperations());
        Objects.requireNonNull(getCommand("Xoa_Lenh")).setExecutor(new me.qpneruy.timerplugin.Command.DeleteSchedCmd());
    }
    private void loadTabcompleter() {
        Objects.requireNonNull(getCommand("Them_lenh")).setTabCompleter(new AddSchedCmd(this));
        Objects.requireNonNull(getCommand("Doi_DuLieu")).setTabCompleter(new SchedCmdOperations(this));
        Objects.requireNonNull(getCommand("Xem_Lenh")).setTabCompleter(new ShowSchedCmd(this));
        Objects.requireNonNull(getCommand("Xoa_Lenh")).setTabCompleter(new DeleteSchedCmd(this));
    }
    private void registerTask() {
        TaskStarting task = new TaskStarting(this);
        task.Run_Task();
    }
    public List<String> getDay() {
        List<String> dayList = new ArrayList<>();
        dayList.add("Moi_Ngay");
        dayList.add("Hom_Nay");
        dayList.add("Thu_Hai");
        dayList.add("Thu_Ba");
        dayList.add("Thu_Tu");
        dayList.add("Thu_Nam");
        dayList.add("Thu_Sau");
        dayList.add("Thu_Bay");
        dayList.add("Chu_Nhat");
        return dayList;
    }
    private static Map<String, List<TimeType>> defaultData() {
        Map<String, List<TimeType>> scheduleMap = new LinkedHashMap<>();
        scheduleMap.put("Moi_Ngay", new ArrayList<>());
        scheduleMap.put("Thu_Hai", new ArrayList<>());
        scheduleMap.put("Thu_Ba", new ArrayList<>());
        scheduleMap.put("Thu_Tu", new ArrayList<>());
        scheduleMap.put("Thu_Nam", new ArrayList<>());
        scheduleMap.put("Thu_Sau", new ArrayList<>());
        scheduleMap.put("Thu_Bay", new ArrayList<>());
        scheduleMap.put("Chu_Nhat", new ArrayList<>());
        return scheduleMap;
    }
    private void ReadyUp(File file) {
        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String json = gson.toJson(defaultData());
                    try (FileWriter writer = new FileWriter(TimerPro.getPlugin().getDataFolder().getAbsolutePath() + "/command_data.json")) {
                        writer.write(json);
                        logger.log(Level.WARNING, "Không tìm thấy file command_data.json, đã tạo file mới.");
                    } catch (IOException e) {
                        logger.log(Level.SEVERE, "Không thể ghi dữ liệu vào tệp command_data.json.", e);
                    }
                    logger.info("Đấm nhau không?");
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error creating new file", e);
        }
    }
}
