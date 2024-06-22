package me.qpneruy.timerplugin.Gui;

import me.qpneruy.timerplugin.Task.archiver;
import me.qpneruy.timerplugin.Types.ExecutionCmd;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.*;

import static me.qpneruy.timerplugin.Gui.Menu.COMMANDS_OF_SLOT;
import static me.qpneruy.timerplugin.Gui.Utils.createItem;
import static me.qpneruy.timerplugin.Gui.Utils.setPane;

public class Main {
    private static Inventory create(int pageCount) {
        Inventory gui = Bukkit.createInventory(null, 54, Component.text("Danh Sách Lệnh - Trang " + pageCount));
        setPane(gui, new int[]{0, 8, 9, 17, 18, 27, 36, 44, 45, 53}, Material.CHAIN); //26 35
        gui.setItem(46, createItem(Material.RED_STAINED_GLASS_PANE, "&cĐóng"));
        gui.setItem(48, createItem(Material.HOPPER, "Lọc Trạng Thái", Collections.singletonList("Kiểu Lọc: Tất cả")));
        gui.setItem(49, createItem(Material.CLOCK, "Lọc Ngày", Collections.singletonList("Kiểu Lọc: Tất cả")));
        gui.setItem(51, createItem(Material.GREEN_WOOL, "&aThêm Lệnh"));
        gui.setItem(35, createItem(Material.SOUL_LANTERN, "Trang Trước"));
        gui.setItem(26, createItem(Material.LANTERN, "Trang Sau"));
        return gui;
    }
    public static Map<String, Inventory> mainGui(archiver Reader) {
        Map<String, Inventory> pages = new HashMap<>();
        List<Inventory> guis = new ArrayList<>();
        guis.add(create(1));
        int page_count = 1;
        int[] commandsSlot = {1, 2, 3, 4, 5, 6, 7, 10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};
        int Count = 0;
        Menu.CMDSLOT_ARCHIVER = Reader;
        String[] daysOfWeek = {"Moi_Ngay", "Thu_Hai", "Thu_Ba", "Thu_Tu", "Thu_Nam", "Thu_Sau", "Thu_Bay", "Chu_Nhat"};
        for (String days : daysOfWeek) {
            Map<String, ExecutionCmd> Cmd = Reader.getCommands(days);
            if (!Cmd.isEmpty()) {
                for (Map.Entry<String, ExecutionCmd> entry : Cmd.entrySet()) {
                    guis.get(page_count-1).setItem(commandsSlot[Count], createItem(Material.PAPER, "§b" + entry.getValue().getName(), Arrays.asList(
                            "&7Thời Gian: §2" + entry.getValue().getExecDateTime() + " " + entry.getValue().getStartTime(),
                            "&7Đang bật: " + (entry.getValue().isEnabled() ? "&aTrue" : "&cFalse"),
                            "&6&l-----------------------------",
                            "&7• (Chuột-Trái) Chỉnh Sửa", "&7• (Shift-Chuột Phải) &cXóa Lệnh"
                    )));
                    COMMANDS_OF_SLOT.put(commandsSlot[Count], entry.getValue());
                    ++Count;
                    if (Count == 35) {
                        Count = 0;
                      pages.put("page_" + page_count, guis.get(page_count-1));
                        page_count++;
                        guis.add(create(page_count));
                      //khởi tạo gui ở đây
                    }
                }
            }
        }
        if (page_count > 0) { pages.put("page_" + page_count, guis.get(page_count-1));}
        System.out.println(">>>>>>>" + page_count);
        return pages;
    }
}
