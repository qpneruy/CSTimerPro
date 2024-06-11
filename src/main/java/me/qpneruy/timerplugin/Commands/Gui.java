package me.qpneruy.timerplugin.Commands;

import me.qpneruy.timerplugin.Task.ExecutionCmd;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;



public class Gui {
    public Inventory Editor(ExecutionCmd Command) {
        Inventory gui = Bukkit.createInventory(null, 54, "Chỉnh Sửa Lệnh");
        List<String> Lore = Arrays.asList("&e/" + Command.getCommand(), "&fChạy: &a" + Command.getStartTime(), "&fDừng: &c" + Command.getEndTime(), "&fMỗi giờ: " + (Command.isEachHour() ? "&aTrue" : "&cFalse"), "&fMỗi Phút: " + (Command.isEachMinutes() ? "&aTrue" : "&cFalse"), "&fNgày Chạy: " + Command.getExecDayOfWeek());
        gui.setItem(4, createItem(Material.PAPER, Command.getName(), Lore));
        //Trang Tri
        int[] YELLOW_STAINED_GLASS_PANE_P = {0, 2, 6, 8, 18, 26, 36, 44};
        ItemStack item1 = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        for (int position : YELLOW_STAINED_GLASS_PANE_P) {
            gui.setItem(position, item1);
        }
        int[] ORANGE_STAINED_GLASS_PANE_P = {1, 3, 5, 7, 9, 17, 35, 27, 45, 53};
        ItemStack item2 = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        for (int position : ORANGE_STAINED_GLASS_PANE_P) {
            gui.setItem(position, item2);
        }
        int[] CHAIN_P = {10, 19, 28, 37, 46, 16, 25, 34, 43, 52};
        ItemStack item3 = new ItemStack(Material.CHAIN);
        for (int position : CHAIN_P) {
            gui.setItem(position, item3);
        }
        //ChucNang
        gui.setItem(47, createItem(Material.RED_STAINED_GLASS_PANE, "&cĐóng")); //Dong
        gui.setItem(51, createItem(Material.GREEN_STAINED_GLASS_PANE, "&aLưu")); //Mo
        gui.setItem(20, createItem(Material.CAMPFIRE, "&aBắt Đầu", Collections.singletonList("&fThực Thi: &a" + Command.getStartTime()))); //Bat Dau
        gui.setItem(24, createItem(Material.SOUL_CAMPFIRE, "&cKết Thúc", Collections.singletonList("&fThực Thi: &c" + Command.getEndTime()))); //Ket Thuc
        gui.setItem(22, createItem(Material.OAK_SIGN, "&bTên", Collections.singletonList("&f" + Command.getName())));// Ten
        gui.setItem(31, createItem(Material.LECTERN, "&eLệnh", Collections.singletonList("&f/" + Command.getCommand()))); // Lenh
        gui.setItem(40, createItem(Material.CLOCK, "&6Ngày Chạy", Collections.singletonList("&f" + Command.getExecDate())));
        gui.setItem(49, createItem(Material.HOPPER, "NDTQ Đẹp Trai", Collections.singletonList("Admin server TaoSinhTon đẹp trai")));
        if (Command.isEachHour()) {
            gui.setItem(38, createItem(Material.STICKY_PISTON, "&6Mỗi Giờ", Collections.singletonList("&fGiá Trị: &aTrue")));
        } else {
            gui.setItem(38, createItem(Material.PISTON, "&6Mỗi Giờ", Collections.singletonList("&fGiá Trị: &cFalse")));
        }

        if (Command.isEachMinutes()) {
            gui.setItem(42, createItem(Material.STICKY_PISTON, "&6Mỗi Phút", Collections.singletonList("&fGiá Trị: &aTrue")));
        } else {
            gui.setItem(42, createItem(Material.PISTON, "&6Mỗi Phút", Collections.singletonList("&fGiá Trị: &cFalse")));
        }
        return gui;
    }
    public Inventory MainGui(List<ExecutionCmd> Cmds) {
        Inventory gui = Bukkit.createInventory(null, 54, "Menu chính");

        return gui;
    }
    public static ItemStack createItem(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (name != null) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        }
        List<String> lores = new ArrayList<>();
        for (String l : lore) {
            if (l != null) {
                lores.add(ChatColor.translateAlternateColorCodes('&', l));
            }
        }
        meta.setLore(lores);
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack createItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(meta);
        return item;
    }
}