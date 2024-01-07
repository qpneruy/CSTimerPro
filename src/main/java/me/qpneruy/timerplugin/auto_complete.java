package me.qpneruy.timerplugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class auto_complete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("Add_Time")) {
            // Nếu có ít nhất một tham số
            if (args.length == 2) {
                // Auto-complete cho tham số đầu tiên (ví dụ: id của mob)
                completions.addAll(getMobIds());
            }
        }

        return completions;
    }

    private List<String> getMobIds() {
        List<String> mobIds = new ArrayList<>();
        mobIds.add("bat");
        mobIds.add("blaze");
        mobIds.add("cave_spider");
        mobIds.add("chicken");
        mobIds.add("cod");
        mobIds.add("cow");
        mobIds.add("creeper");
        mobIds.add("dolphin");
        mobIds.add("donkey");
        mobIds.add("drowned");
        mobIds.add("elder_guardian");
        mobIds.add("enderman");
        mobIds.add("endermite");
        mobIds.add("evoker");
        mobIds.add("ghast");
        mobIds.add("guardian");
        mobIds.add("horse");
        mobIds.add("husk");
        mobIds.add("llama");
        mobIds.add("magma_cube");
        mobIds.add("mooshroom");
        mobIds.add("mule");
        mobIds.add("ocelot");
        mobIds.add("parrot");
        mobIds.add("phantom");
        mobIds.add("pig");
        mobIds.add("polar_bear");
        mobIds.add("pufferfish");
        mobIds.add("rabbit");
        mobIds.add("salmon");
        mobIds.add("sheep");
        mobIds.add("shulker");
        mobIds.add("silverfish");
        mobIds.add("skeleton");
        mobIds.add("skeleton_horse");
        mobIds.add("slime");
        mobIds.add("spider");
        mobIds.add("squid");
        mobIds.add("stray");
        mobIds.add("tropical_fish");
        mobIds.add("turtle");
        mobIds.add("vex");
        mobIds.add("villager");
        mobIds.add("vindicator");
        mobIds.add("witch");
        mobIds.add("wither_skeleton");
        mobIds.add("wolf");
        mobIds.add("zombie");
        mobIds.add("zombie_horse");
        mobIds.add("zombie_pigman");
        mobIds.add("zombie_villager");
        mobIds.add("cat");
        mobIds.add("fox");
        mobIds.add("panda");
        mobIds.add("pillager");
        mobIds.add("ravager");
        mobIds.add("trader_llama");
        mobIds.add("wandering_trader");
        mobIds.add("bee");
        mobIds.add("hoglin");
        mobIds.add("piglin");
        mobIds.add("strider");
        mobIds.add("zoglin");
        mobIds.add("zombified_piglin");
        mobIds.add("axolotl");
        mobIds.add("glow_squid");
        mobIds.add("goat");
        mobIds.add("piglin_brute");

        return mobIds;
    }
}
