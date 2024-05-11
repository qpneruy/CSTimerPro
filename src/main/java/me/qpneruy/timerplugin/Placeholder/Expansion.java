package me.qpneruy.timerplugin.Placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Expansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "timer";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Tinhde061";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }
    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (identifier.equals("time")) {
            return getCurrentTime();
        } else if (identifier.equals("date")) {
            return getCurrentDate();
        }
        return null;
    }
    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(new Date());
    }
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date());
    }
}
