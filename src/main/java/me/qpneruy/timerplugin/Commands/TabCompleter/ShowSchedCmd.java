package me.qpneruy.timerplugin.Commands.TabCompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowSchedCmd implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> completions = new ArrayList<>();
        if (strings.length == 1) {
            List<String> daysOfWeek = Arrays.asList("Thu_Hai", "Thu_Ba", "Thu_Tu", "Thu_Nam", "Thu_Sau", "Thu_Bay", "Chu_Nhat", "Hom_Nay", "Tat_Ca");
            completions.addAll(daysOfWeek);
        }
        return completions;
    }
}
