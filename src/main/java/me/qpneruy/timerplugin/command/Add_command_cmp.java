package me.qpneruy.timerplugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Add_command_cmp implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> completions = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("Them_lenh")) {
            if (strings.length == 1) {
                completions.addAll(getDay());
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
    private List<String> getDay() {
        List<String> dayList = new ArrayList<>();
        dayList.add("Sunday");
        dayList.add("Monday");
        dayList.add("Tuesday");
        dayList.add("Wednesday");
        dayList.add("Thursday");
        dayList.add("Friday");
        dayList.add("Saturday");
        return dayList;
    }
}
