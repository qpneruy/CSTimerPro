package me.qpneruy.timerplugin.command;

import me.qpneruy.timerplugin.TimerPro;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class showCommand_cmp implements TabCompleter {
    private final TimerPro plugin;

    public showCommand_cmp(TimerPro plugin) {
        this.plugin = plugin;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> completions = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("Xem_Lenh")) {
            if (strings.length == 1) {
                completions.addAll(plugin.getDay());
            }
        }
        return completions;
    }
}
