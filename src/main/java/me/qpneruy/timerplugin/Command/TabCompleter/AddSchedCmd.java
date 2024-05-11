package me.qpneruy.timerplugin.Command.TabCompleter;

import me.qpneruy.timerplugin.TimerPro;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AddSchedCmd implements TabCompleter {
    private final TimerPro plugin;

    public AddSchedCmd(TimerPro plugin) {
        this.plugin = plugin;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> completions = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("Them_lenh")) {
            if (strings.length == 1) {
                completions.addAll(plugin.getDay());
            } if (strings.length == 2) {
                completions.addAll(getTime());
            }
        }
        return completions;
    }

    private List<String> getTime() {
        List<String> times = new ArrayList<>();
        times.add("06:00");
        times.add("09:00");
        times.add("12:00");
        times.add("18:00");
        return times;
    }

}
