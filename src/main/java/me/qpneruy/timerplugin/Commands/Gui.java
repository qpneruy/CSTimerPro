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
        List<String> Lore = Arrays.asList("&e/" + Command.getCommand(), "&fChạy: &a" + Command.getStartTime(), "&fDừng: &c" + Command.getEndTime(), "&fMỗi giờ: " + (Command.isEachHour() ? "&aTrue" : "&cFalse"), "&fMỗi Phút: " + (Command.isEachMinutes() ? "&aTrue" : "&cFalse"), "&fNgày Chạy: &a" + Command.getExecDayOfWeek(), "&fĐang Bật: " + (Command.isEnable() ? "&aTrue" : "&cFalse"));
        gui.setItem(4, createItem(Material.PAPER, Command.getName(), Lore));
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
        List<String> lore1 = Arrays.asList("&fThực Thi: &a" + Command.getStartTime(), "&6&l-------------------"," ", "&7•(Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Phải) (Chuột Trái)");
        gui.setItem(47, createItem(Material.RED_STAINED_GLASS_PANE, "&cĐóng"));
        gui.setItem(51, createItem(Material.GREEN_STAINED_GLASS_PANE, "&aLưu"));
        gui.setItem(20, createItem(Material.CAMPFIRE, "&aBắt Đầu", lore1));
        lore1 = Arrays.asList("&fThực Thi: &c" + Command.getEndTime(), "&6&l--------------------------", "&fKhông Cần Đặt giá trị, nếu không lăp"," ", "&7•(Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Phải) (Chuột Trái)");
        gui.setItem(24, createItem(Material.SOUL_CAMPFIRE, "&cKết Thúc", lore1));
        gui.setItem(22, createItem(Material.OAK_SIGN, "&bTên", Collections.singletonList("&f" + Command.getName())));
        gui.setItem(31, createItem(Material.LECTERN, "&eLệnh", Collections.singletonList("&f/" + Command.getCommand())));
        lore1 = Arrays.asList("&6&l----------------------", "&f<Thu_Hai, Thu_Ba,.....", "&f<dd/MM/yyyy");
        gui.setItem(40, createItem(Material.CLOCK, "&6Ngày Chạy", Collections.singletonList("&f" + Command.getExecDayOfWeek())));
        gui.setItem(49, createItem(Material.HOPPER, "NDTQ Đẹp Trai", Collections.singletonList("Admin server TaoSinhTon đẹp trai")));
        gui.setItem(38, (Command.isEachHour()) ? createItem(Material.STICKY_PISTON, "&6Mỗi Giờ", Arrays.asList("&fGiá Trị: &aTrue", "&6&l-----------------------------","&fLặp Lệnh Mỗi giờ từ Bắt Đầu -> Kết Thúc", " ", "&7•(Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Phải) (Chuột Trái)")) : createItem(Material.PISTON, "&6Mỗi Giờ", Arrays.asList("&fGiá Trị: &cFalse", "&6&l-----------------------------","&fLặp Lệnh Mỗi Giờ từ Bắt Đầu -> Kết Thúc"," ", "&7•(Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Trái) (Chuột Phải)")));
        gui.setItem(42, (Command.isEachMinutes()) ? createItem(Material.STICKY_PISTON, "&6Mỗi Phút", Arrays.asList("&fGiá Trị: &aTrue", "&6&l------------------------------","&fLặp Lệnh Mỗi Phút từ Bắt Đầu -> Kết Thúc", " ", "&7•(Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Trái) (Chuột Phải)")) : createItem(Material.PISTON, "&6Mỗi Phút", Arrays.asList("&fGiá Trị: &cFalse", "&6&l------------------------------","&fLặp Lệnh Mỗi Phút từ Bắt Đầu -> Kết Thúc"," ", "&7•(Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Trái) (Chuột Phải)")));
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