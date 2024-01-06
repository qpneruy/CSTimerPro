package me.qpneruy.timerplugin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    private final TimerPro plugin;
    public JoinListener(TimerPro plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        String joinmes = this.plugin.getConfig().getString("join-mes");
        Player player = event.getPlayer();
        String Player_n = player.getName();
        if (joinmes != null) {
            joinmes = joinmes.replace("%player%", Player_n);
            event.getPlayer().sendMessage("alo" + joinmes);
        }
    }
}
