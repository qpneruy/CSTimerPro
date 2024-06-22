package me.qpneruy.timerplugin.Types;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.qpneruy.timerplugin.TimerPro;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class OpPlayer {
    private String uuid;
    private String name;
    private int level;
    private boolean bypassesPlayerLimit;
    private static TimerPro plugin = new TimerPro();

    public OpPlayer(TimerPro plugin) {
        OpPlayer.plugin = plugin;
    }

    public String getUuid() {
        return uuid;
    }
    public String getName() {
        return name;
    }
    public int getLevel() {
        return level;
    }
    public boolean isBypassesPlayerLimit() {
        return bypassesPlayerLimit;
    }
    public void setBypassesPlayerLimit(boolean bypassesPlayerLimit) {
        this.bypassesPlayerLimit = bypassesPlayerLimit;
    }

    public static List<OpPlayer> getOpPlayers() {
        Gson gson = new Gson();
        Type opPlayerListType = new TypeToken<List<OpPlayer>>(){}.getType();
        List<OpPlayer> opPlayers = null;

        Path parentPath = Paths.get(plugin.getDataFolder().getAbsolutePath()).getParent().getParent();
        Path opsJsonPath = parentPath.resolve("ops.json");
        try (FileReader reader = new FileReader(opsJsonPath.toString())) {
            opPlayers = gson.fromJson(reader, opPlayerListType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return opPlayers;
    }
}