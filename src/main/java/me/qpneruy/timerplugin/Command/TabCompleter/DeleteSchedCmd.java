package me.qpneruy.timerplugin.Command.TabCompleter;

import me.qpneruy.timerplugin.TimerPro;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DeleteSchedCmd implements TabCompleter {
    private final TimerPro plugin;

    public DeleteSchedCmd(TimerPro plugin) {
        this.plugin = plugin;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> completions = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("Xoa_Lenh")) {
            if (strings.length == 1) {
                completions.addAll(plugin.getDay());
            }
        }
        return completions;
    }
}
