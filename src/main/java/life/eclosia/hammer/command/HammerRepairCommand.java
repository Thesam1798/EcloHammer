package life.eclosia.hammer.command;

import de.tr7zw.changeme.nbtapi.NBTItem;
import life.eclosia.commandapi.commands.SubCommand;
import life.eclosia.hammer.Main;
import life.eclosia.hammer.object.hammer.Hammer;
import life.eclosia.hammer.object.hammer.ListHammer;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class HammerRepairCommand extends SubCommand {

    private static final HashMap<UUID, HashMap<ItemStack, Calendar>> RepairList = new HashMap<>();

    @Override
    public String getName() {
        return "Repair Hammer";
    }

    @Override
    public String getCommand() {
        return "repair";
    }

    @Override
    public String getDescription() {
        return "Permet de réparer le hammer en main";
    }

    @Override
    public String getSyntax() {
        return "/hammer repair";
    }

    @Override
    public Integer getArgsNumber() {
        return 1;
    }

    @Override
    public void performPlayer(Player player, String[] args) {
        try {
            if (ListHammer.list.isEmpty()) ListHammer.loadFromConfig();
            if (ListHammer.list.isEmpty())
                player.sendMessage(Main.PLUGIN_CHAT_PREFIX + ChatColor.RED + "ERROR : Imposible de charger la config");

            ItemStack item = Hammer.RepairHammer(player.getItemInHand().clone());

            if (item != null) {
                if (RepairList.containsKey(player.getUniqueId()) && RepairList.get(player.getUniqueId()).containsKey(item)) {
                    Date last = RepairList.get(player.getUniqueId()).get(item).getTime();

                    int time = getTimeBetween(new Date(), last);

                    Integer repairTime = (new NBTItem(item)).getInteger("REPAIR");

                    if (repairTime != null) {
                        if (time >= repairTime) {
                            player.setItemInHand(item);
                            RepairList.get(player.getUniqueId()).replace(item, Calendar.getInstance());
                        } else {
                            player.sendMessage(Main.PLUGIN_CHAT_PREFIX + ChatColor.RED + "Imposible de réparer pour le moment il vous reste " + ChatColor.DARK_GREEN + (repairTime - time) + ChatColor.RED + " minutes");
                        }
                    } else {
                        player.sendMessage(Main.PLUGIN_CHAT_PREFIX + ChatColor.RED + "PLUGIN ERROR");
                    }

                } else {
                    HashMap<ItemStack, Calendar> times = new HashMap<>();
                    times.put(item, Calendar.getInstance());

                    RepairList.put(player.getUniqueId(), times);

                    player.setItemInHand(item);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getTimeBetween(Date laterDate, Date earlierDate) {
        return (int) ((laterDate.getTime() / 60000) - (earlierDate.getTime() / 60000));
    }

    @Override
    public void performConsole(ConsoleCommandSender player, String[] args) {
        player.sendMessage(Main.PLUGIN_CHAT_PREFIX + ChatColor.RED + "La Commande ne peut etre utilise que par un joueur !");
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
