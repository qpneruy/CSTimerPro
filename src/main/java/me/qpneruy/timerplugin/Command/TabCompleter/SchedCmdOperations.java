package me.qpneruy.timerplugin.Command.TabCompleter;

import me.qpneruy.timerplugin.TimerPro;
import me.qpneruy.timerplugin.task.TimeType;
import me.qpneruy.timerplugin.task.archiver;
import me.qpneruy.timerplugin.task.TimeCalibarate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SchedCmdOperations implements TabCompleter {
    private final TimerPro plugin;

    public SchedCmdOperations(TimerPro plugin) {
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
                List<String> id;
                String DayOfWeek = strings[0];
                TimeCalibarate Timer = new TimeCalibarate();
                if (Objects.equals(strings[0], "Hom_Nay")) { DayOfWeek = Timer.getDayOfWeek(); }
                id = getId(DayOfWeek);
                if (id == null) {
                    return null;
                } else completions.addAll(id);
            } else if (strings.length == 3) {
                completions.addAll(getSuggest());
            }
        }
        return completions;
    }

    private List<String> getId(String DayOfWeek) {
        List<String> id = new ArrayList<>();
        archiver getCommand = new archiver();
        List<TimeType> commandp = getCommand.getDayTimeList(DayOfWeek);
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


