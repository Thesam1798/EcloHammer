package life.eclosia.hammer.events;

import life.eclosia.hammer.Main;
import life.eclosia.hammer.object.Hammer;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class HammerEvent implements Listener {

    public HammerEvent(Main main) {
    }

    @EventHandler
    public void onMine(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        List<Material> blockNotAllowed = Arrays.asList(Material.BEDROCK, Material.WATER, Material.LAVA, Material.AIR);

        if (!player.getGameMode().equals(GameMode.SURVIVAL) || player.getItemInHand() == null) return;
        if (!Hammer.compare(player.getItemInHand())) return;

        World world = player.getWorld();

        for (int x = -1; x < 2; ++x) {
            for (int y = -1; y < 2; ++y) {
                for (int z = -1; z < 2; ++z) {
                    Block blockAtLoc = world.getBlockAt(block.getLocation().getBlockX() + x, block.getLocation().getBlockY() + y, block.getLocation().getBlockZ() + z);

                    if (!blockNotAllowed.contains(blockAtLoc.getType())) {
                        blockAtLoc.breakNaturally(new ItemStack(Material.DIAMOND_PICKAXE));
                    } else {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
