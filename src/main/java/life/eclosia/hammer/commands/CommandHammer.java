package life.eclosia.hammer.commands;

import life.eclosia.hammer.Main;
import life.eclosia.hammer.object.Hammer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class CommandHammer implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String labels, String[] args) {

        try {
            if (sender instanceof Player) {


                Player p = (Player) sender;

                if (cmd.getName().equalsIgnoreCase("hammer")) {
                    if (args.length == 0) {
                        p.sendMessage(Main.PLUGIN_CHAT_PREFIX + "§7Commande invalide, faites §c/hammer give <joueur>");
                    }

                    if (args.length == 1) {
                        p.sendMessage(Main.PLUGIN_CHAT_PREFIX + "§7Commande invalide, faites §c/hammer give <joueur>");
                    }

                    if (args.length == 2) {
                        if (args[0].equalsIgnoreCase("give")) {
                            String retour = GiveHammer(args[1]);

                            if (!retour.isEmpty()) {
                                p.sendMessage(retour);
                            }
                        } else {
                            p.sendMessage("§7Commande invalide, faites §c/hammer give <joueur>");
                        }
                    }

                }
            }

            if (sender instanceof ConsoleCommandSender) {
                if (args.length == 2 && !args[1].isEmpty()) {
                    if (args[0].equalsIgnoreCase("give")) {

                        String retour = GiveHammer(args[1]);

                        if (!retour.isEmpty()) {
                            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Reload Complete");
                            getLogger().info(ChatColor.stripColor(retour));
                        }
                    } else {
                        getLogger().info(ChatColor.stripColor("Commande invalide, faites /hammer give <joueur>"));
                        return false;
                    }
                } else {
                    getLogger().info(ChatColor.stripColor(ChatColor.RED + "Commande invalide, faites /hammer give <joueur>"));
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            getLogger().log(Level.INFO, ChatColor.RED + e.getMessage());
            getLogger().log(Level.INFO, ChatColor.RED + e.getCause().toString());
            return false;
        }
    }

    private String GiveHammer(String targetName) {
        Player target = Bukkit.getPlayer(targetName);

        if (target != null && target.isOnline()) {
            if (target.getInventory().firstEmpty() != -1) {
                ItemStack hammerItem = Hammer.getItemStack();

                target.getInventory().addItem(hammerItem);
                target.updateInventory();

                return Main.PLUGIN_CHAT_PREFIX + ChatColor.GRAY +
                        " Vous avez reçu " + ChatColor.GOLD + "x1 Hammer " + ChatColor.GRAY + "!";
            } else {
                return Main.PLUGIN_CHAT_PREFIX + ChatColor.RED + " L'inventaire du joueur est plein !";
            }

        } else {
            return Main.PLUGIN_CHAT_PREFIX + ChatColor.RED + " Le joueur n'est pas en ligne";
        }
    }
}
