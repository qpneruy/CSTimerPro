package me.qpneruy.timerplugin.Gui;

import me.qpneruy.timerplugin.Types.ExecutionCmd;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.kyori.examination.Examinable;
import static me.qpneruy.timerplugin.Gui.Utils.createItem;
import static me.qpneruy.timerplugin.Gui.Utils.setPane;

public class Editor {
    public static Inventory Editor(ExecutionCmd command) {
        Inventory gui = Bukkit.createInventory(null, 54, Component.text("Chỉnh Sửa"));

        // Hiển thị thông tin lệnh
        List<String> lore = Arrays.asList(
                "&e/" + command.getCommand(),
                "&7Chạy: &a" + command.getStartTime(),
                "&7Dừng: &c" + command.getEndTime(),
                "Mỗi giờ: " + (command.isEachHour() ? "&aTrue" : "&cFalse"),
                "&7Mỗi Phút: " + (command.isEachMinute() ? "&aTrue" : "&cFalse"),
                "Ngày Chạy: &a" + ((command.getExecDateTime())),
                "&7Đang Bật: " + (command.isEnabled() ? "&aTrue" : "&cFalse")
        );
        gui.setItem(4, createItem(Material.PAPER, command.getName(), lore));

        setPane(gui, new int[]{0, 2, 6, 8, 18, 26, 36, 44}, Material.YELLOW_STAINED_GLASS_PANE);
        setPane(gui, new int[]{1, 3, 5, 7, 9, 17, 35, 27, 45, 53}, Material.ORANGE_STAINED_GLASS_PANE);
        setPane(gui, new int[]{10, 19, 28, 37, 46, 16, 25, 34, 43, 52}, Material.CHAIN);

        List<String> startTimeLore = Arrays.asList(
                ">&fThực Thi: &a" + command.getStartTime(),
                "&6&l-------------------", " ",
                "&7•(Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Phải) (Chuột Trái)"
        );
        List<String> endTimeLore = Arrays.asList(
                ">&7Thực Thi: &c" + command.getEndTime(),
                "&6&l--------------------------",
                "&7Không Cần Đặt giá trị, nếu không lăp", " ",
                "&7•(Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Phải) (Chuột Trái)"
        );
        List<String> nameLore = Arrays.asList(
                ">&7" + command.getName(),
                "&6&l-------------------", " ",
                "&7•(Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Phải) (Chuột Trái)"
        );
        List<String> commandLore = Arrays.asList(
                ">&7/" + command.getCommand(),
                "&6&l-------------------", " ",
                "&7•(Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Phải) (Chuột Trái)"
        );
        List<String> execDateLore = Arrays.asList(
                (command.getExecDateTime()),
                "&6&l-------------------",
                "&7<Thu_Hai, Thu_Ba,.....>", "&7<dd/MM/yyyy>", " ",
                "&7>(Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Phải) (Chuột Trái)"
        );

        gui.setItem(47, createItem(Material.RED_STAINED_GLASS_PANE, "&cQuay Lại"));
        gui.setItem(51, createItem(Material.GREEN_STAINED_GLASS_PANE, "&aĐóng"));
        gui.setItem(20, createItem(Material.CAMPFIRE, "&aBắt Đầu", startTimeLore));
        gui.setItem(24, createItem(Material.SOUL_CAMPFIRE, "&cKết Thúc", endTimeLore));
        gui.setItem(22, createItem(Material.OAK_SIGN, "&bTên Lệnh", nameLore));
        gui.setItem(31, createItem(Material.LECTERN, "&eLệnh", commandLore));
        gui.setItem(40, createItem(Material.CLOCK, "&6Ngày Chạy", execDateLore));
        gui.setItem(49, createItem(Material.HOPPER, "NDTQ Đẹp Trai", Collections.singletonList("Admin server TaoSinhTon đẹp trai")));
        gui.setItem(38, createItem(
                command.isEachHour() ? Material.STICKY_PISTON : Material.PISTON,
                "&6Mỗi Giờ",
                Arrays.asList(
                        "&fGiá Trị: " + (command.isEachHour() ? "&aTrue" : "&cFalse"),
                        "&6&l-----------------------------",
                        "&7Lặp Lệnh Mỗi Giờ từ Bắt Đầu -> Kết Thúc", " ",
                        "&7> (Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Phải) (Chuột Trái)"
                )
        ));
        gui.setItem(42, createItem(
                command.isEachMinute() ? Material.STICKY_PISTON : Material.PISTON,
                "&6Mỗi Phút",
                Arrays.asList(
                        "&fGiá Trị: " + (command.isEachMinute() ? "&aTrue" : "&cFalse"),
                        "&6&l------------------------------",
                        "&7Lặp Lệnh Mỗi Phút từ Bắt Đầu -> Kết Thúc", " ",
                        "&7>(Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Trái) (Chuột Phải)"
                )
        ));

        return gui;
    }

}
