package me.qpneruy.timerplugin.Events;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.qpneruy.timerplugin.Task.ExecutionCmd;
import me.qpneruy.timerplugin.Task.archiver;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static me.qpneruy.timerplugin.Commands.Gui.createItem;

public class Menu implements Listener {
    private static final Map<Player, CmdInventory> PLAYER_INVENTORIES = new HashMap<>();
    private static final Map<Player, Map<String, Boolean>> PLAYER_INPUT_STATE = new HashMap<>();
    private static final Pattern CHAT_MESSAGE_PATTERN = Pattern.compile("\"text\":\"(.*?)\"");

    public static void addPlayer(Player player, Inventory inventory, ExecutionCmd schedCmd, archiver editCmd) {
        PLAYER_INVENTORIES.put(player, new CmdInventory(inventory, schedCmd, editCmd));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        if (!PLAYER_INVENTORIES.containsKey(player)) return;

        CmdInventory cmdInventory = PLAYER_INVENTORIES.get(player);
        Inventory inventory = cmdInventory.getInventory();

        if (!event.getInventory().equals(inventory)) return;

        event.setCancelled(true);

        Material clickedItemType = event.getCurrentItem() != null ? event.getCurrentItem().getType() : null;

        switch (Objects.requireNonNull(clickedItemType)) {
            case PISTON, STICKY_PISTON -> {
                handlePistonClick(event, player, clickedItemType);
                player.openInventory(inventory);
            }
            case LECTERN -> handleLecternClick(player);
            case OAK_SIGN -> handleSignClick(player);
            case CAMPFIRE -> handleCampfireClick(player);
            case SOUL_CAMPFIRE -> handleSoulCampfireClick(player);
            case CLOCK -> handleClockClick(player);
            case RED_STAINED_GLASS_PANE -> player.closeInventory();
            case GREEN_STAINED_GLASS_PANE -> {
                cmdInventory.getArchiver().save();
                player.closeInventory();
            }
        }
    }

    private void handleLecternClick(Player player) {
        setInputState(player, "Command", false);
        showInputMessage(player, "§6Nhập Lệnh", "§e----------------§6[Đặt lệnh]§e-----------------", "§e•Nhâp Lênh Vào Khung Chat");
    }

    private void handleSignClick(Player player) {
        setInputState(player, "Name", false);
        showInputMessage(player, "§6Nhập Tên", "§e----------------§6[Đặt Tên]§e-----------------", "§e•Nhâp Tên Vào Khung Chat");
    }

    private void handleSoulCampfireClick(Player player) {
        setInputState(player, "EndTime", false);
        showInputMessage(player, "§6Nhập Thời Gian Dừng", "§e----------------§6[Đặt Thời gian Dừng]§e-----------------", "§e•Nhâp Thời Gian vào khung chat theo định dạng:\n->§8<HH:mm>.");
    }

    private void handleCampfireClick(Player player) {
        setInputState(player, "StartTime", false);
        showInputMessage(player, "§6Nhập Thời Gian Bắt Đầu", "§e----------------§6[Đặt Thời gian Bắt Đầu]§e-----------------", "§e•Nhâp Thời Gian vào khung chat theo định dạng:\n->§8<HH:mm>.");
    }

    private void handleClockClick(Player player) {
        setInputState(player, "ExecDateTime", false);
        showInputMessage(player, "§6Nhập Ngày", "§e--------------------§6[Đặt Ngày]§e---------------------", "§e•Nhâp Ngày vào khung chat theo định dạng:\n->§8<Thu_hai, Thu_ba,....> §ehoặc §8<dd/MM/yyyy>.");
    }

    private void handlePistonClick(InventoryClickEvent event, Player player, Material currentItemType) {
        int slot = event.getSlot();
        boolean isSticky = currentItemType == Material.STICKY_PISTON;
        Material newItemType = isSticky ? Material.PISTON : Material.STICKY_PISTON;
        String status = isSticky ? "&cFalse" : "&aTrue";

        if (slot == 38 || slot == 42) {
            List<String> lore = Arrays.asList(
                    "&fGiá Trị: " + status,
                    "&6&l-----------------------------",
                    slot == 38 ? "&fLặp Lệnh Mỗi Giờ từ Bắt Đầu -> Kết Thúc" : "&fLặp Lệnh Mỗi Phút từ Bắt Đầu -> Kết Thúc",
                    " ", "&7•(Chuột-Phải) Chỉnh Sửa", "&7•(Chuột-Trái) (Chuột Phải)"
            );
            PLAYER_INVENTORIES.get(player).getInventory().setItem(slot, createItem(newItemType, "&6Mỗi " + (slot == 38 ? "Giờ" : "Phút"), lore));

            if (slot == 38) {
                PLAYER_INVENTORIES.get(player).getCmd().toggleEachHour();
            } else {
                PLAYER_INVENTORIES.get(player).getCmd().toggleEachMinute();
            }
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        Map<String, Boolean> inputState = PLAYER_INPUT_STATE.get(player);

        if (inputState == null) {
            return;
        }

        event.setCancelled(true);

        String message = GsonComponentSerializer.gson().serialize(event.message());
        Matcher matcher = CHAT_MESSAGE_PATTERN.matcher(message);

        if (!matcher.find()) {
            return;
        }

        String textContent = matcher.group(1);

        if ("huy".equals(textContent)) {
            player.sendMessage("§6[TimerPro]: §cĐã hủy Đặt ngày!");
            inputState.put("ExecDateTime", true);
            return;
        }

        CmdInventory cmdInventory = PLAYER_INVENTORIES.get(player);
        ExecutionCmd cmd = cmdInventory.getCmd();
        archiver archiver = cmdInventory.getArchiver();

        if (handleInputState(inputState, "StartTime", textContent, player, cmd, archiver)) return;
        if (handleInputState(inputState, "Name", textContent, player, cmd, archiver)) return;
        if (handleInputState(inputState, "EndTime", textContent, player, cmd, archiver)) return;
        if (handleInputState(inputState, "Command", textContent, player, cmd, archiver)) return;
        if (handleInputState(inputState, "ExecDateTime", textContent, player, cmd, archiver)) return;
    }

    private boolean handleInputState(Map<String, Boolean> inputState, String stateName, String textContent,
                                     Player player, ExecutionCmd cmd, archiver archiver) {
        if (inputState.getOrDefault(stateName, true)) {
            return false;
        }

        switch (stateName) {
            case "StartTime" -> cmd.setStartTime(textContent);
            case "Name" -> {
                archiver.removeCommand(cmd.getExecDateTime(), cmd.getName());
                cmd.setName(textContent);
                archiver.addCommand(cmd.getName(), cmd.getCommand(), cmd.getExecDateTime(), cmd.getStartTime());
            }
            case "EndTime" -> cmd.setEndTime(textContent);
            case "Command" -> cmd.setCommand(textContent);
            case "ExecDateTime" -> {
                archiver.removeCommand(cmd.getExecDateTime(), cmd.getName());
                cmd.setExecDateTime(textContent);
                archiver.addCommand(cmd.getName(), cmd.getCommand(), cmd.getExecDateTime(), cmd.getStartTime());
            }
        }

        archiver.save();
        inputState.put(stateName, true);
        return true;
    }

    private void setInputState(Player player, String stateName, boolean state) {
        PLAYER_INPUT_STATE.computeIfAbsent(player, k -> new HashMap<>()).put(stateName, state);
    }

    private void showInputMessage(Player player, String title, String... messages) {
        player.closeInventory();
        Component titleComponent = Component.text(title);
        Component subtitleComponent = Component.text("§eVào Khung Chat");
        Title titleObject = Title.title(titleComponent, subtitleComponent);
        player.showTitle(titleObject);
        player.sendMessage("§e•Để hủy nhập §c\"huy\"");
        Arrays.stream(messages).forEach(player::sendMessage);
    }
}

/*
 * Những thay đổi đã được thực hiện:
 *
 * - Sử dụng switch thay cho nhiều câu if: Tăng khả năng đọc và hiệu suất khi kiểm tra currentItemType.
 * - Rút gọn các đoạn code lặp lại: Gom code xử lý input chung vào hàm handleInputState.
 * - Sử dụng Map.computeIfAbsent: Thay thế logic if(containsKey)... khi đặt giá trị vào PLAYER_INPUT_STATE.
 * - Đặt tên biến rõ ràng hơn: Ví dụ: Case -> inputState.
 * - Loại bỏ code không cần thiết: Ví dụ: Bukkit.getConsoleSender().sendMessage(...) trong onPlayerChat.
 * - Gom các dòng message gửi cho player: Sử dụng Arrays.stream().forEach() thay vì lặp lại sendMessage.
 *
 * Lưu ý:
 * - Đoạn code trên sử dụng một số Java feature mới hơn (ví dụ: switch expression, lambda),
 *   bạn cần đảm bảo server/plugin của bạn hỗ trợ phiên bản Java tương thích.
 * - Hãy kiểm tra kỹ lại logic sau khi tối ưu code để tránh sai sót.
 */