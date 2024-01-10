package me.qpneruy.timerplugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getServer;

public class reload_omp implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        getServer().dispatchCommand(getServer().getConsoleSender(), "reload");
        getServer().dispatchCommand(getServer().getConsoleSender(), "reload confirm");
        return false;
    }
}
