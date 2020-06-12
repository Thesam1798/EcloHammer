package life.eclosia.hammer.object.hammer;

import life.eclosia.hammer.config.HammerConfig;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.MemorySection;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ListHammer {

    public static final HashMap<String, Hammer> list = new HashMap<>();
    private static final HashMap<String, HashMap<String, Object>> listForSave = new HashMap<>();

    public static void save() throws Exception {
        //mock();

        for (String name : list.keySet()) {
            Hammer hammer = list.get(name);
            HashMap<String, Object> info = new HashMap<>();
            info.put("name", ChatColor.translateAlternateColorCodes('&', hammer.getName()));
            info.put("material", hammer.getTool().name());
            info.put("lore", hammer.getLore());
            info.put("durability", hammer.getDurability());
            info.put("enchantments", hammer.getEnchantments());
            info.put("repairTime", hammer.getRepairTime());

            listForSave.put(hammer.getSimpleName(), info);
        }

        HammerConfig.getConfig().set("hammers", listForSave);
        HammerConfig.save();
    }

    private static void mock() {
        ArrayList<String> lore = new ArrayList<>(Arrays.asList("§7", "§7Cet outil mine du §63x3x2§7,", "§7très utile pour miner plus rapidement", "§7", "§cNe le réparer qu'avec /repair", "§cou /repair all pour éviter les bug!"));
        HashMap<Enchantment, Integer> enchantments = new HashMap<>();
        enchantments.put(Enchantment.DIG_SPEED, 3);
        enchantments.put(Enchantment.DURABILITY, 6);
        Hammer test = new Hammer(Material.GOLD_PICKAXE, "§c§l✖ Hammer ✖", 300, lore, enchantments, 5);

        list.put(test.getSimpleName(), test);
    }

    public static void loadFromConfig() throws Exception {
        if (HammerConfig.getConfig().get("hammers") instanceof MemorySection) {
            ((MemorySection) HammerConfig.getConfig().get("hammers")).getValues(false).forEach((s, o) -> {
                MemorySection hammerInfo = ((MemorySection) o);
                if (hammerInfo.get("material") != null && hammerInfo.get("name") != null && hammerInfo.get("lore") != null) {
                    Hammer hammer = new Hammer(hammerInfo.get("material"), hammerInfo.get("name"), hammerInfo.get("durability"), hammerInfo.get("lore"), hammerInfo.get("enchantments"), hammerInfo.get("repairTime"));
                    list.put(hammer.getSimpleName(), hammer);
                }
            });
        }

        save();
    }

}
