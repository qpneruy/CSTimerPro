package me.qpneruy.timerplugin.Gui;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    /**
     * Đặt các ô kính màu vào giao diện.
     *
     * @param gui       Giao diện cần đặt.
     * @param positions Vị trí các ô.
     * @param material  Loại vật liệu kính màu.
     */
    public static void setPane(Inventory gui, int[] positions, Material material) {
        ItemStack item = new ItemStack(material);
        for (int position : positions) {
            gui.setItem(position, item);
        }
    }

    public static ItemStack createItem(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (name != null) {
            meta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', name)));
        }

        if (lore != null) {
            List<Component> lores = new ArrayList<>();
            for (String l : lore) {
                if (l != null) {
                    lores.add(Component.text(ChatColor.translateAlternateColorCodes('&', l)));
                }
            }
            meta.lore(lores);
        }

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Tạo một vật phẩm mới (không có mô tả).
     *
     * @param material Loại vật liệu.
     * @param name     Tên vật phẩm.
     * @return Vật phẩm mới.
     */
    public static ItemStack createItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (name != null) {
            meta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', name)));
        }

        item.setItemMeta(meta);
        return item;
    }
}