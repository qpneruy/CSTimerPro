package me.qpneruy.timerplugin.command;

import me.qpneruy.timerplugin.TimerPro;
import me.qpneruy.timerplugin.task.TimeData;
import me.qpneruy.timerplugin.task.archive;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class timeDataChange_cmp implements TabCompleter {
    private final TimerPro plugin;

    public timeDataChange_cmp(TimerPro plugin) {
        this.plugin = plugin;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> completions = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("Doi_DuLieu")) {
            if (strings.length == 1) {
                completions.addAll(plugin.getDay());
            }
            if (strings.length == 2) {
                System.out.println(strings[0]);
                List<String> id = new ArrayList<>();
                id = getId(strings[0]);
                if (id == null) {
                    completions.add("Khong co lenh");
                } else completions.addAll(id);
            } else if (strings.length == 3) {
                completions.addAll(getSuggest());
            }
        }
        return completions;
    }

    private List<String> getId(String DayOfWeek) {
        List<String> id = new ArrayList<>();
        archive getCommand = new archive();
        List<TimeData> commandp = getCommand.getDayTimeList(DayOfWeek);
        int total_command = commandp.size();
        if (commandp.isEmpty()) {
            return null;
        }
        for (int i = 1; i <= total_command; i++) {
            id.add(Integer.toString(i));
        }
        return id;
    }

    private List<String> getSuggest() {
        List<String> suggests = new ArrayList<>();
        suggests.add("Thoi_Gian");
        suggests.add("Lenh");
        return suggests;
    }
}


