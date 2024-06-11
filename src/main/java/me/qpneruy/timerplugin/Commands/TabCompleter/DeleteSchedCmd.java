package me.qpneruy.timerplugin.Commands.TabCompleter;

import me.qpneruy.timerplugin.Task.ExecutionCmd;
import me.qpneruy.timerplugin.Task.archiver;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static me.qpneruy.timerplugin.Commands.TabCompleter.Utils.getDay;

public class DeleteSchedCmd implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> completions = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("xoa_lenh")) {
            if (strings.length == 1) {
                completions.addAll(getDay());
            }
            if (strings.length == 2) {
                    archiver GetSuggest = new archiver();
                    if (GetSuggest.getDateTime(strings[0]).isEmpty()) {
                        completions.add("Null");
                    } else {
                        Map<String, ExecutionCmd> cmds = GetSuggest.getDateTime(strings[0]);
                        for (Map.Entry<String, ExecutionCmd> name : cmds.entrySet()) {
                            completions.add(name.getValue().getName());
                        }
                    }
            }
            return completions;
        }
        return completions;
    }
}