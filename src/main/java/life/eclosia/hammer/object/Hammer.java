package life.eclosia.hammer.object;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Hammer {

    private static ItemStack hammerItem;

    public static ItemStack getItemStack() {
        if (hammerItem == null) {
            hammerItem = new ItemStack(Material.GOLD_PICKAXE, 1);

            ItemMeta hammerItemMeta = hammerItem.getItemMeta();
            hammerItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            hammerItemMeta.setDisplayName("§c§l✖ Hammer ✖");
            hammerItemMeta.setLore(Arrays.asList("§7", "§7Cet outil mine du §63x3x2§7,", "§7très utile pour miner plus rapidement", "§7", "§cNe le réparer qu'avec /repair", "§cou /repair all pour éviter les bug!"));
            hammerItemMeta.addEnchant(Enchantment.DIG_SPEED, 1, true);
            hammerItemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

            hammerItem.setItemMeta(hammerItemMeta);
            hammerItem.setDurability((short) 1);
        }
        return hammerItem;
    }

    public static boolean compare(ItemStack stack) {
        if (hammerItem == null) {
            getItemStack();
        }

        return hammerItem.hasItemMeta() == stack.hasItemMeta() && (!hammerItem.hasItemMeta() || Bukkit.getItemFactory().equals(hammerItem.getItemMeta(), stack.getItemMeta()));
    }

}
