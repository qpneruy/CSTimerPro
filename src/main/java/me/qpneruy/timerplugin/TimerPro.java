package me.qpneruy.timerplugin;
import me.qpneruy.timerplugin.Task.TaskManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import me.qpneruy.timerplugin.Events.Menu;
import java.util.*;


public final class TimerPro extends JavaPlugin {
    public static TimerPro plugin;
    public static TimerPro getPlugin() {
        return plugin;
    }
    //public static MenuInventory menuInventory = new MenuInventory();
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
            Bukkit.getServer().getConsoleSender().sendMessage("§cTinhDepTrai is false!!!.");
            Bukkit.getServer().getConsoleSender().sendMessage("§cPlease set 'TinhDepTrai' to true for continue using this plugin :)).");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        this.loadCommand();
        this.loadTabcompleter();
        //new Expansion().register();
        this.registerTask();

    }

    @Override
    public void onDisable() {
        getLogger().info("-------------------------------");
        getLogger().info("||       Đã dừng PLugin      ||");
        getLogger().info("-------------------------------");
    }
    private void loadCommand() {
        Objects.requireNonNull(getCommand("Xem_Lenh")).setExecutor(new me.qpneruy.timerplugin.Commands.ShowSchedCmd());
        Objects.requireNonNull(getCommand("Them_Lenh")).setExecutor(new me.qpneruy.timerplugin.Commands.AddSchedCmd());
        Objects.requireNonNull(getCommand("Xoa_Lenh")).setExecutor(new me.qpneruy.timerplugin.Commands.DeleteSchedCmd());
        Objects.requireNonNull(getCommand("Sua_lenh")).setExecutor(new me.qpneruy.timerplugin.Commands.EditSchedCmd());
    }
    private void loadTabcompleter() {
        Objects.requireNonNull(getCommand("Them_lenh")).setTabCompleter(new me.qpneruy.timerplugin.Commands.TabCompleter.AddSchedCmd());
        Objects.requireNonNull(getCommand("sua_lenh")).setTabCompleter(new me.qpneruy.timerplugin.Commands.TabCompleter.DeleteSchedCmd());
        Objects.requireNonNull(getCommand("Xem_Lenh")).setTabCompleter(new me.qpneruy.timerplugin.Commands.TabCompleter.ShowSchedCmd());
        Objects.requireNonNull(getCommand("Xoa_Lenh")).setTabCompleter(new me.qpneruy.timerplugin.Commands.TabCompleter.DeleteSchedCmd());
    }
    private void registerTask() {
        TaskManager tm = new TaskManager(this);
        tm.start();
        Menu menu = new Menu();
        getServer().getPluginManager().registerEvents(menu, this);
    }

}

