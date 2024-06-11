package me.qpneruy.timerplugin.Events;

import me.qpneruy.timerplugin.Task.ExecutionCmd;
import me.qpneruy.timerplugin.Task.archiver;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import javax.xml.stream.events.EndDocument;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static me.qpneruy.timerplugin.Commands.Gui.createItem;

public class Menu implements Listener {
    private static final Map<Player, Inventory> playerInventories = new HashMap<>();
    private final archiver Editor = new archiver();
    public static void addPlayer(Player player, Inventory inventory, ExecutionCmd SchedCmd) {
        playerInventories.put(player, inventory);
    }

    public static void removePlayer(Player player) {
        playerInventories.remove(player);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = playerInventories.get(player);
        if (event.getInventory().equals(inventory)) {
            event.setCancelled(true);
            inventory = TogglePiston(event, inventory);
            player.openInventory(inventory);
            player.updateInventory();
        }
    }
    private Inventory TogglePiston(InventoryClickEvent event, Inventory inventory) {
        archiver Toggle = new archiver();
        if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.PISTON) {
            switch (event.getSlot()) {
                case 38: inventory.setItem(38, createItem(Material.STICKY_PISTON, "&6Mỗi Giờ", Collections.singletonList("&fGiá Trị: &aTrue")));
                    Editor.getDateTime()
                    break;
                case 42: inventory.setItem(42, createItem(Material.STICKY_PISTON, "&6Mỗi Phút", Collections.singletonList("&fGiá Trị: &aTrue")));
                    break;
            }
        } else
        if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.STICKY_PISTON) {
            switch (event.getSlot()) {
                case 38: inventory.setItem(38, createItem(Material.PISTON, "&6Mỗi Giờ", Collections.singletonList("&fGiá Trị: &cFalse")));
                    break;
                case 42: inventory.setItem(42, createItem(Material.PISTON, "&6Mỗi Phút", Collections.singletonList("&fGiá Trị: &cFalse")));
                    break;
            }
        }
        return  inventory;
    }
}