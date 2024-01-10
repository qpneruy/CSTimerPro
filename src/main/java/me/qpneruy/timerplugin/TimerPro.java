package me.qpneruy.timerplugin;
import me.qpneruy.timerplugin.command.*;
import me.qpneruy.timerplugin.placeholder.Expansion;
import me.qpneruy.timerplugin.task.command_task;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public final class TimerPro extends JavaPlugin {
    private static TimerPro plugin;
    public static TimerPro getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        getLogger().info("-------------------------------");
        getLogger().info("||     Đã khởi động Plugin   ||___________________________");
        getLogger().info("|| Make by: Supercalifragilisticexpialidocious configer ||");
        getLogger().info("----------------------------------------------------------");
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
        Objects.requireNonNull(getCommand("Sync_Time")).setExecutor(new Sync_Time(this));
        Objects.requireNonNull(getCommand("Xem_Lenh")).setExecutor(new fecth_command());
        Objects.requireNonNull(getCommand("Them_Lenh")).setExecutor(new Add_Command(this));
        Objects.requireNonNull(getCommand("Doi_DuLieu")).setExecutor(new change_data());
        Objects.requireNonNull(getCommand("freload")).setExecutor(new reload_omp());
    }
    private void loadTabcompleter() {
        Objects.requireNonNull(getCommand("Them_lenh")).setTabCompleter(new Add_command_cmp());
        Objects.requireNonNull(getCommand("Doi_DuLieu")).setTabCompleter(new change_data_cmp());
    }
    private void registerTask() {
       command_task task = new command_task(this);
       task.Run_Task();
    }
}
