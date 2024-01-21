package me.qpneruy.timerplugin;

import me.qpneruy.timerplugin.command.*;
import me.qpneruy.timerplugin.placeholder.Expansion;
import me.qpneruy.timerplugin.task.archive;
import me.qpneruy.timerplugin.task.Timer_Task;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Objects;
import java.util.TimerTask;
import java.util.logging.Logger;


public final class TimerPro extends JavaPlugin {
    private static TimerPro plugin;
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
//        Objects.requireNonNull(getCommand("Sync_Time")).setExecutor(new Sync_Time(this));
//        Objects.requireNonNull(getCommand("Xem_Lenh")).setExecutor(new fecth_command());
        Objects.requireNonNull(getCommand("Them_Lenh")).setExecutor(new Add_Command());
        Objects.requireNonNull(getCommand("Doi_DuLieu")).setExecutor(new TimeDateChange());
//        Objects.requireNonNull(getCommand("freload")).setExecutor(new reload_omp());
//        Objects.requireNonNull(getCommand("Xoa_Lenh")).setExecutor(new Del_Command());
    }
    private void loadTabcompleter() {
        Objects.requireNonNull(getCommand("Them_lenh")).setTabCompleter(new Add_command_cmp());
//        Objects.requireNonNull(getCommand("Doi_DuLieu")).setTabCompleter(new change_data_cmp());
    }
    private void registerTask() {
        Timer_Task task = new Timer_Task(this);
        task.Run_Task();
    }

}
