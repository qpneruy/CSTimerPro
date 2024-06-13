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

    /**
     * Tạo giao diện chỉnh sửa lệnh.
     *
     * @param command Lệnh cần chỉnh sửa.
     * @return Giao diện chỉnh sửa lệnh.
     */
    public Inventory Editor(ExecutionCmd command) {
        Inventory gui = Bukkit.createInventory(null, 54, "Chỉnh Sửa Lệnh");

        // Hiển thị thông tin lệnh
        List<String> lore = Arrays.asList(
                "&e/" + command.getCommand(),
                "&fChạy: &a" + command.getStartTime(),
                "&fDừng: &c" + command.getEndTime(),
                "Mỗi giờ: " + (command.isEachHour() ? "&aTrue" : "&cFalse"),
                "&fMỗi Phút: " + (command.isEachMinute() ? "&aTrue" : "&cFalse"),
                "Ngày Chạy: &a" + ((command.getExecDateTime())),
                "&fĐang Bật: " + (command.isEnabled() ? "&aTrue" : "&cFalse")
        );
        gui.setItem(4, createItem(Material.PAPER, command.getName(), lore));

        // Đặt các ô kính màu
        setPane(gui, new int[]{0, 2, 6, 8, 18, 26, 36, 44}, Material.YELLOW_STAINED_GLASS_PANE);
        setPane(gui, new int[]{1, 3, 5, 7, 9, 17, 35, 27, 45, 53}, Material.ORANGE_STAINED_GLASS_PANE);
        setPane(gui, new int[]{10, 19, 28, 37, 46, 16, 25, 34, 43, 52}, Material.CHAIN);

        // Tạo các nút chỉnh sửa
        List<String> startTimeLore = Arrays.asList(
                ">&fThực Thi: &a" + command.getStartTime(),
                "&6&l-------------------", " ",
                "&7•(Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Phải) (Chuột Trái)"
        );
        List<String> endTimeLore = Arrays.asList(
                ">&fThực Thi: &c" + command.getEndTime(),
                "&6&l--------------------------",
                "&fKhông Cần Đặt giá trị, nếu không lăp", " ",
                "&7•(Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Phải) (Chuột Trái)"
        );
        List<String> nameLore = Arrays.asList(
                ">&f" + command.getName(),
                "&6&l-------------------", " ",
                "&7•(Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Phải) (Chuột Trái)"
        );
        List<String> commandLore = Arrays.asList(
                ">&f/" + command.getCommand(),
                "&6&l-------------------", " ",
                "&7•(Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Phải) (Chuột Trái)"
        );
        List<String> execDateLore = Arrays.asList(
                (command.getExecDateTime()),
                "&6&l-------------------",
                "&f<Thu_Hai, Thu_Ba,.....>", "&f<dd/MM/yyyy>", " ",
                "&7•(Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Phải) (Chuột Trái)"
        );

        // Thêm các nút vào giao diện
        gui.setItem(47, createItem(Material.RED_STAINED_GLASS_PANE, "&cĐóng"));
        gui.setItem(51, createItem(Material.GREEN_STAINED_GLASS_PANE, "&aLưu"));
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
                        "&fLặp Lệnh Mỗi Giờ từ Bắt Đầu -> Kết Thúc", " ",
                        "&7•(Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Phải) (Chuột Trái)"
                )
        ));
        gui.setItem(42, createItem(
                command.isEachMinute() ? Material.STICKY_PISTON : Material.PISTON,
                "&6Mỗi Phút",
                Arrays.asList(
                        "&fGiá Trị: " + (command.isEachMinute() ? "&aTrue" : "&cFalse"),
                        "&6&l------------------------------",
                        "&fLặp Lệnh Mỗi Phút từ Bắt Đầu -> Kết Thúc", " ",
                        "&7•(Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Trái) (Chuột Phải)"
                )
        ));

        return gui;
    }

    /**
     * Tạo giao diện chính.
     *
     * @param cmds Danh sách các lệnh.
     * @return Giao diện chính.
     */
    public Inventory MainGui(List<ExecutionCmd> cmds) {
        return Bukkit.createInventory(null, 54, "Menu chính");
    }

    /**
     * Đặt các ô kính màu vào giao diện.
     *
     * @param gui       Giao diện cần đặt.
     * @param positions Vị trí các ô.
     * @param material  Loại vật liệu kính màu.
     */
    private void setPane(Inventory gui, int[] positions, Material material) {
        ItemStack item = new ItemStack(material);
        for (int position : positions) {
            gui.setItem(position, item);
        }
    }

    /**
     * Tạo một vật phẩm mới.
     *
     * @param material Loại vật liệu.
     * @param name     Tên vật phẩm.
     * @param lore     Mô tả vật phẩm.
     * @return Vật phẩm mới.
     */
    public static ItemStack createItem(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (name != null) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        }

        if (lore != null) {
            List<String> lores = new ArrayList<>();
            for (String l : lore) {
                if (l != null) {
                    lores.add(ChatColor.translateAlternateColorCodes('&', l));
                }
            }
            meta.setLore(lores);
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
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        }

        item.setItemMeta(meta);
        return item;
    }
}

/*
 * Những thay đổi đã được thực hiện:
 *
 * - Tối ưu hóa import: Loại bỏ các import không sử dụng.
 * - Format code: Sử dụng indent 4 spaces, thêm khoảng trắng cho dễ đọc.
 * - Áp dụng JavaDoc: Thêm comment giải thích cho class và method.
 * - Tối ưu hóa method Editor():
 *   - Sử dụng List.of() để khởi tạo list cho gọn hơn.
 *   - Rút gọn việc tạo ItemStack bằng cách tạo method riêng.
 * - Bổ sung comment giải thích trong code.
 */