package me.qpneruy.timerplugin.Gui;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.qpneruy.timerplugin.Task.archiver;
import me.qpneruy.timerplugin.Types.ExecutionCmd;
import me.qpneruy.timerplugin.Types.SchedArchiver;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static me.qpneruy.timerplugin.Gui.Utils.createItem;
import static me.qpneruy.timerplugin.Task.TimeCalibarate.getDayOfWeek;
import static me.qpneruy.timerplugin.Task.TimeCalibarate.getTime;
import static me.qpneruy.timerplugin.TimerPro.plugin;

public class Menu implements Listener {
    private static final Map<Player, Map<String, Inventory>> GUI_OF_PLAYERS = new HashMap<>();
    private static final Map<Player, SchedArchiver> COMMANDS_OF_PLAYERS = new HashMap<>();
    private static final Map<Player, String> PLAYER_INPUT_STATE = new HashMap<>();
    private static final Pattern CHAT_MESSAGE_PATTERN = Pattern.compile("\"text\":\"(.*?)\"");
    public static Map<Integer, ExecutionCmd> COMMANDS_OF_SLOT = new HashMap<>();
    public static archiver CMDSLOT_ARCHIVER;
    public static Map<Player, Integer> playerCurrentPage = new HashMap<>();

    public static void addPlayerCommand(Player player, ExecutionCmd schedCmd, archiver archiver) {
        COMMANDS_OF_PLAYERS.put(player, new SchedArchiver(schedCmd, archiver));
    }

    public static void addPlayerGui(Player player, Map<String, Inventory> gui) {
        GUI_OF_PLAYERS.put(player, gui);
    }

    @EventHandler
    public static void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        boolean found = false;
        for (Map.Entry<String, Inventory> entry : GUI_OF_PLAYERS.get(player).entrySet()) {if (entry.getValue().equals(event.getInventory())) found = true;}
        if (!found) return;
        Inventory gui = event.getInventory();
        event.setCancelled(true);

        Material clickedItemType = event.getCurrentItem() != null ? event.getCurrentItem().getType() : null;
        if (Objects.requireNonNull(gui.getItem(0)).getType() == Material.CHAIN) {
            handleMainGui(player, clickedItemType, event);
        } else if (Objects.requireNonNull(gui.getItem(0)).getType() == Material.YELLOW_STAINED_GLASS_PANE) {
            handleEditorGui(player, clickedItemType, event);
        }

    }

    private static void handleMainGui(Player player, Material clickedItemType, InventoryClickEvent e) {
        if (clickedItemType == Material.RED_STAINED_GLASS_PANE) {
            player.closeInventory();
            return;
        }
        if (e.getClick().equals(ClickType.LEFT) && clickedItemType == Material.LANTERN) {
            int tinh = playerCurrentPage.get(player);
            tinh+=1;
            if (GUI_OF_PLAYERS.get(player).containsKey("page_"+tinh)) {
                playerCurrentPage.put(player, tinh);
                player.openInventory(GUI_OF_PLAYERS.get(player).get("page_" + tinh));
                player.updateInventory();
            }
        }
        if (e.getClick().equals(ClickType.LEFT) && clickedItemType == Material.SOUL_LANTERN) {
            int tinh = playerCurrentPage.get(player);
            tinh-=1;
            if (GUI_OF_PLAYERS.get(player).containsKey("page_"+tinh)) {
                playerCurrentPage.put(player, tinh);
                player.openInventory(GUI_OF_PLAYERS.get(player).get("page_" + tinh));
                player.updateInventory();
            }
        }
        if (e.getClick().equals(ClickType.SHIFT_RIGHT)) {
            ExecutionCmd Cmd = COMMANDS_OF_SLOT.get(e.getSlot());
            CMDSLOT_ARCHIVER.removeCommand(Cmd);
            player.performCommand("timerpro");
            player.updateInventory();
        }
        if (e.getClick().equals(ClickType.LEFT) && clickedItemType == Material.PAPER) {
            ExecutionCmd Cmd = COMMANDS_OF_SLOT.get(e.getSlot());
            addPlayerCommand(player, Cmd, CMDSLOT_ARCHIVER);
            player.performCommand("suaLenh " + Cmd.getExecDateTime() + " " + Cmd.getName());
            player.updateInventory();
        }
        if (clickedItemType == Material.GREEN_WOOL) {
            ExecutionCmd Cmd = new ExecutionCmd();
            int count = 1;
            Map<String, ExecutionCmd> cmds =  CMDSLOT_ARCHIVER.getCommands(getDayOfWeek());
            for (Map.Entry<String, ExecutionCmd> entry : cmds.entrySet()) {
                if (entry.getValue().getName().contains("LệnhMới")) ++count;
            }
            Cmd.init("LệnhMới(" + count + ")", "TinhDepTrai", getDayOfWeek(), getTime("hh:mm"));
            CMDSLOT_ARCHIVER.addCommand(Cmd);
            player.performCommand("suaLenh " + Cmd.getExecDateTime() + " " + Cmd.getName());
        }
    }
  //chỉnh sửa cách lặp kphai eachoures eachminutes đẻ user chỉnh sau hơn kiểu mỗi 2 giờ 3 phút 1 giây
    private static void handleEditorGui(Player player, Material clickedItemType, InventoryClickEvent e) {
        SchedArchiver SchedCmd = COMMANDS_OF_PLAYERS.get(player);
        switch (Objects.requireNonNull(clickedItemType)) {
            case PISTON, STICKY_PISTON -> {
                handlePistonClick(e, clickedItemType, SchedCmd);
                player.updateInventory();
            }
            case LECTERN -> handleLecternClick(player);
            case OAK_SIGN -> handleSignClick(player);
            case CAMPFIRE -> handleCampfireClick(player);
            case SOUL_CAMPFIRE -> handleSoulCampfireClick(player);
            case CLOCK -> handleClockClick(player);
            case RED_STAINED_GLASS_PANE -> {
                player.performCommand("timerpro");
                player.updateInventory();
            }
            case GREEN_STAINED_GLASS_PANE -> player.closeInventory();

        }
        SchedCmd.getParent().save();
    }

    private static void handleLecternClick(Player player) {
        PLAYER_INPUT_STATE.put(player, "Command");
        showInputMessage(player, "§6Nhập Lệnh", "§e----------------§6[Đặt lệnh]§e-----------------", "§e•Nhâp Lênh Vào Khung Chat");
    }

    private static void handleSignClick(Player player) {
        PLAYER_INPUT_STATE.put(player, "Name");
        showInputMessage(player, "§6Nhập Tên", "§e----------------§6[Đặt Tên]§e-----------------", "§e•Nhâp Tên Vào Khung Chat");
    }

    private static void handleSoulCampfireClick(Player player) {
        PLAYER_INPUT_STATE.put(player, "EndTime");
        showInputMessage(player, "§6Nhập Thời Gian Dừng", "§e----------------§6[Đặt Thời gian Dừng]§e-----------------", "§e•Nhâp Thời Gian vào khung chat theo định dạng:\n->§8<HH:mm>.");
    }

    private static void handleCampfireClick(Player player) {
        PLAYER_INPUT_STATE.put(player, "StartTime");
        showInputMessage(player, "§6Nhập Thời Gian Bắt Đầu", "§e----------------§6[Đặt Thời gian Bắt Đầu]§e-----------------", "§e•Nhâp Thời Gian vào khung chat theo định dạng:\n->§8<HH:mm>.");
    }

    private static void handleClockClick(Player player) {
        PLAYER_INPUT_STATE.put(player, "ExecDateTime");
        showInputMessage(player, "§6Nhập Ngày", "§e--------------------§6[Đặt Ngày]§e---------------------", "§e•Nhâp Ngày vào khung chat theo định dạng:\n->§8<Thu_hai, Thu_ba,....> §ehoặc §8<dd/MM/yyyy>.");
    }

    private static void handlePistonClick(InventoryClickEvent event, Material currentItemType, SchedArchiver schedArchiver) {
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
            event.getInventory().setItem(slot, createItem(newItemType, "&6Mỗi " + (slot == 38 ? "Giờ" : "Phút"), lore));

            if (slot == 38) {
                schedArchiver.getChild().toggleEachHour();
            } else {
                schedArchiver.getChild().toggleEachMinute();
            }
        }
    }

    private static void showInputMessage(Player player, String title, String... messages) {
        player.closeInventory();
        Component titleComponent = Component.text(title);
        Component subtitleComponent = Component.text("§eVào Khung Chat");
        Title titleObject = Title.title(titleComponent, subtitleComponent);
        player.showTitle(titleObject);
        player.sendMessage("§e•Để hủy nhập §c\"huy\"");
        Arrays.stream(messages).forEach(player::sendMessage);
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        String inputState = PLAYER_INPUT_STATE.get(player);

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
            PLAYER_INPUT_STATE.remove(player);
            return;
        }
        SchedArchiver CMD = COMMANDS_OF_PLAYERS.get(player);
        ExecutionCmd cmd = CMD.getChild();
        archiver archiver = CMD.getParent();

        if (handleInputState(inputState, textContent, player, cmd, archiver)) ;
    }

    private boolean handleInputState(String stateName, String textContent,
                                     Player player, ExecutionCmd cmd, archiver archiver) {

        switch (stateName) {
            case "StartTime" -> cmd.setStartTime(textContent);
            case "Name" -> {
                archiver.removeCommand(cmd);
                cmd.setName(textContent);
                archiver.addCommand(cmd);
            }
            case "EndTime" -> cmd.setEndTime(textContent);
            case "Command" -> cmd.setCommand(textContent);
            case "ExecDateTime" -> {
                archiver.removeCommand(cmd);
                cmd.setExecDateTime(textContent);
                archiver.addCommand(cmd);
            }
        }
        archiver.save();
        Bukkit.getScheduler().runTask(plugin, () -> player.performCommand("suaLenh " + cmd.getExecDateTime() + " " + cmd.getName()));
        PLAYER_INPUT_STATE.remove(player);
        return true;
    }
}