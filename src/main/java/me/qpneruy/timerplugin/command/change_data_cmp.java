package me.qpneruy.timerplugin.command;

import me.qpneruy.timerplugin.task.TimeData;
import me.qpneruy.timerplugin.task.archive;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class change_data_cmp implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> completions = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("Doi_DuLieu")) {
            if (strings.length == 1) {
                completions.addAll(getId());
            } else if (strings.length == 2) {
                completions.addAll(getSuggest());
            }
        }
        return completions;
    }

    private List<String> getId() {
        List<String> id = new ArrayList<>();
        archive getCommand = new archive();
        List<TimeData> commandp = getCommand.getTimeDataList();
        int total_command = commandp.size();
        for (int i = 1; i <= total_command; i++) {
            id.add(Integer.toString(i));
        }
        return id;
    }

    private List<String> getSuggest() {
        List<String> suggests = new ArrayList<>();
        suggests.add("time");
        suggests.add("command");
        return suggests;
    }
}


