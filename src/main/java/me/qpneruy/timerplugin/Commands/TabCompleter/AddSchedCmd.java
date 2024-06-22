package me.qpneruy.timerplugin.Commands.TabCompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static me.qpneruy.timerplugin.Commands.TabCompleter.Utils.*;

public class AddSchedCmd implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> completions = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("themlenh")) {
            if (strings.length == 1) {
                completions.add("<Tên Lệnh>");
            }
            if (strings.length == 2) {
                completions.addAll(suggestions(strings[1], getDay()));
            }
            if (strings.length == 3) {
                completions.addAll(suggestions(strings[2], getTime()));
            }
            if (strings.length == 4) {
                completions.add("<Lệnh>");
            }
         }
        return completions;
    }
}
