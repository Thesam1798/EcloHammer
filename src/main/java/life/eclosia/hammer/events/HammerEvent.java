package life.eclosia.hammer.events;

import de.tr7zw.changeme.nbtapi.NBTItem;
import life.eclosia.hammer.Main;
import life.eclosia.hammer.object.hammer.Hammer;
import life.eclosia.hammer.object.hammer.ListHammer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HammerEvent implements Listener {

    public HammerEvent(Main main) {
    }

    @EventHandler
    public void onMine(BlockBreakEvent event) {
        Player player = event.getPlayer();
        final boolean[] valid = {false};
        final Hammer[] hammers = {null};

        ItemStack itemInHand = player.getItemInHand();
        if (!ListHammer.list.isEmpty() && player.getGameMode().equals(GameMode.SURVIVAL) && itemInHand != null) {
            ItemStack finalItemInHand = itemInHand;
            ListHammer.list.forEach((s, hammer) -> {
                if (!valid[0]) {
                    valid[0] = hammer.similar((finalItemInHand));
                    hammers[0] = hammer;
                }
            });
        }

        if (!valid[0] || hammers[0] == null) return;

        assert itemInHand != null;

        // Récupération de la durabiliter moins 1
        int dura = (new NBTItem(itemInHand)).getInteger("DURA") - 1;

        //Suppresion de l'item
        player.getInventory().remove(itemInHand);

        //Création d'une copy avec les NBT
        NBTItem nbtItem = (new NBTItem((new Hammer(hammers[0])).getItemStack()));

        //Récupération du Lore de base
        ArrayList<String> lore = (new Hammer(hammers[0])).getDefaultLore();

        //Verif
        if (lore == null) return;

        //Remove if exist
        lore.removeIf(s -> s.contains(ChatColor.DARK_GREEN + "Durability : "));

        //Remove last return line
        if (lore.get(lore.size() - 1).equalsIgnoreCase("") || ChatColor.stripColor(lore.get(lore.size() - 1)).equalsIgnoreCase("")) {
            lore.remove(lore.get(lore.size() - 1));
        }

        //Si la sura est inférieur ou equals a 0
        if (dura <= 0) {
            lore.add(ChatColor.DARK_GREEN + "Durability : " + ChatColor.RED + 0 + ChatColor.DARK_GREEN + "/" + ChatColor.RED + hammers[0].getDurability() + ChatColor.RESET);
            event.setCancelled(true);
            player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1, 1);
            return;
        } else {
            lore.add(ChatColor.DARK_GREEN + "Durability : " + ChatColor.RED + dura + ChatColor.DARK_GREEN + "/" + ChatColor.RED + hammers[0].getDurability() + ChatColor.RESET);
            nbtItem.setInteger("DURA", dura);
        }

        itemInHand = nbtItem.getItem();
        ItemMeta itemMeta = itemInHand.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName((new Hammer(hammers[0])).getName() + " " + ChatColor.RESET + ChatColor.RED + dura);
        itemInHand.setItemMeta(itemMeta);

        World world = player.getWorld();
        Block block = event.getBlock();

        List<Material> blockNotAllowed = Arrays.asList(Material.BEDROCK, Material.WATER, Material.LAVA, Material.AIR);

        for (int x = -1; x < 2; ++x) {
            for (int y = -1; y < 2; ++y) {
                for (int z = -1; z < 2; ++z) {
                    Block blockAtLoc = world.getBlockAt(block.getLocation().getBlockX() + x, block.getLocation().getBlockY() + y, block.getLocation().getBlockZ() + z);
                    if (!blockNotAllowed.contains(blockAtLoc.getType())) {
                        blockAtLoc.breakNaturally(new ItemStack(Material.DIAMOND_PICKAXE));
                    }
                }
            }
        }

        player.setItemInHand(itemInHand);
        player.updateInventory();
    }
}
