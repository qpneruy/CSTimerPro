package me.qpneruy.timerplugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.qpneruy.timerplugin.command.*;
import me.qpneruy.timerplugin.placeholder.Expansion;
import me.qpneruy.timerplugin.task.TimeData;
import me.qpneruy.timerplugin.task.archive;
import me.qpneruy.timerplugin.task.timerTask;
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
    private static final Logger logger = Logger.getLogger(archive.class.getName());
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
        Objects.requireNonNull(getCommand("Sync_Time")).setExecutor(new syncTime(this));
        Objects.requireNonNull(getCommand("Xem_Lenh")).setExecutor(new showCommand());
        Objects.requireNonNull(getCommand("Them_Lenh")).setExecutor(new addCommand());
        Objects.requireNonNull(getCommand("Doi_DuLieu")).setExecutor(new timeDateChange());
//        Objects.requireNonNull(getCommand("freload")).setExecutor(new reload_omp());
//        Objects.requireNonNull(getCommand("Xoa_Lenh")).setExecutor(new Del_Command());
    }
    private void loadTabcompleter() {
        Objects.requireNonNull(getCommand("Them_lenh")).setTabCompleter(new addCommand_cmp(this));
        Objects.requireNonNull(getCommand("Doi_DuLieu")).setTabCompleter(new timeDataChange_cmp(this));
        Objects.requireNonNull(getCommand("Xem_Lenh")).setTabCompleter(new showCommand_cmp(this));
    }
    private void registerTask() {
        timerTask task = new timerTask(this);
        task.Run_Task();
    }
    public List<String> getDay() {
        List<String> dayList = new ArrayList<>();
        dayList.add("Tat_Ca");
        dayList.add("Thu_Hai");
        dayList.add("Thu_Ba");
        dayList.add("Thu_Tu");
        dayList.add("Thu_Nam");
        dayList.add("Thu_Sau");
        dayList.add("Thu_Bay");
        dayList.add("Chu_Nhat");
        return dayList;
    }
    private static Map<String, List<TimeData>> defaultData() {
        Map<String, List<TimeData>> scheduleMap = new LinkedHashMap<>();
        scheduleMap.put("Tat_Ca", new ArrayList<>());
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
}
