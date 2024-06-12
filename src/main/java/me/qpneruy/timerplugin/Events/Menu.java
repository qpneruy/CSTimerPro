package me.qpneruy.timerplugin.Events;

import me.qpneruy.timerplugin.Task.ExecutionCmd;
import me.qpneruy.timerplugin.Task.archiver;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static me.qpneruy.timerplugin.Commands.Gui.createItem;

public class Menu implements Listener {
    private static final Map<Player, Inventory> playerInventories = new HashMap<>();
    private static final Map<Player, ExecutionCmd> playerCommands = new HashMap<>();
    private static final Map<Player, archiver> playerArchiver = new HashMap<>();
    public static void addPlayer(Player player, Inventory inventory, ExecutionCmd SchedCmd, archiver EditCmd) {
        playerInventories.put(player, inventory);
        playerCommands.put(player, SchedCmd);
        playerArchiver.put(player, EditCmd);
    }
    public static ExecutionCmd getPlayerCommand(Player player) {
        return playerCommands.get(player);
    }
    public static archiver getplayerArchiver(Player player) {
        return playerArchiver.get(player);
    }
    public static void removePlayer(Player player) {
        playerInventories.remove(player);
        playerCommands.remove(player);
        playerArchiver.remove(player);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = playerInventories.get(player);
        if (event.getInventory().equals(inventory)) {
            event.setCancelled(true);
            inventory = TogglePiston(event, inventory, player);
            getplayerArchiver(player).Save();
            player.openInventory(inventory);
            player.updateInventory();
        }
    }
    private Inventory TogglePiston(InventoryClickEvent event, Inventory inventory, Player player) {
        if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.PISTON) {
            switch (event.getSlot()) {
                case 38: inventory.setItem(38, createItem(Material.STICKY_PISTON, "&6Mỗi Giờ", Arrays.asList("&fGiá Trị: &aTrue", "&6&l-----------------------------","&fLặp Lệnh Mỗi Giờ từ Bắt Đầu -> Kết Thúc", " ", "&7•(Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Trái) (Chuột Phải)")));
                    getPlayerCommand(player).ToggleEachHour();
                    break;
                case 42: inventory.setItem(42, createItem(Material.STICKY_PISTON, "&6Mỗi Phút", Arrays.asList("&fGiá Trị: &aTrue", "&6&l------------------------------","&fLặp Lệnh Mỗi Phút từ Bắt Đầu -> Kết Thúc", " ", "&7•(Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Trái) (Chuột Phải)")));
                    getPlayerCommand(player).ToggleEachMinutes();
                    break;
            }
        } else
        if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.STICKY_PISTON) {
            switch (event.getSlot()) {
                case 38: inventory.setItem(38, createItem(Material.PISTON, "&6Mỗi Giờ", Arrays.asList("&fGiá Trị: &cFalse", "&6&l-----------------------------","&fLặp Lệnh Mỗi Giờ từ Bắt Đầu -> Kết Thúc", " ", "&7•(Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Trái) (Chuột Phải)")));
                    getPlayerCommand(player).ToggleEachHour();
                    break;
                case 42: inventory.setItem(42, createItem(Material.PISTON, "&6Mỗi Phút", Arrays.asList("&fGiá Trị: &cFalse", "&6&l------------------------------","&fLặp Lệnh Mỗi Phút từ Bắt Đầu -> Kết Thúc"," ", "&7•(Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Trái) (Chuột Phải)")));
                    getPlayerCommand(player).ToggleEachMinutes();
                    break;
            }
        }
        return  inventory;
    }
}