package me.qpneruy.timerplugin.Commands.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<String> getTime() {
        List<String> times = new ArrayList<>();
        times.add("06:00");
        times.add("09:00");
        times.add("12:00");
        times.add("18:00");
        return times;
    }

    public static List<String> getDay() {
        List<String> dayList = new ArrayList<>();
        dayList.add("Moi_Ngay");
        dayList.add("Hom_Nay");
        dayList.add("Thu_Hai");
        dayList.add("Thu_Ba");
        dayList.add("Thu_Tu");
        dayList.add("Thu_Nam");
        dayList.add("Thu_Sau");
        dayList.add("Thu_Bay");
        dayList.add("Chu_Nhat");
        return dayList;
    }

    public static List<String> suggestions(String input, List<String> List) {
        List<String> completions = new ArrayList<>();
        for (String str : List) if (str.toLowerCase().startsWith(input.toLowerCase())) completions.add(str);
        return completions;
    }
}