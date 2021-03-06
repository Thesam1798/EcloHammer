package life.eclosia.hammer.command;

import life.eclosia.commandapi.CommandHelper;
import life.eclosia.commandapi.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HammerHelpCommand extends SubCommand {

    public static void action(CommandSender sender) {

        sender.sendMessage(ChatColor.RESET + "");
        sender.sendMessage(ChatColor.DARK_GREEN + "------------------ " + ChatColor.LIGHT_PURPLE + "Hammer Helper" + ChatColor.DARK_GREEN + " ------------------");
        sender.sendMessage(ChatColor.RESET + "");

        CommandHelper.GetAllCommand().forEach(subCommand -> {
            if (subCommand.getSyntax().contains("/hammer")) {
                sender.sendMessage(ChatColor.DARK_GREEN + "Command : " + ChatColor.GREEN + ChatColor.ITALIC + subCommand.getName());
                sender.sendMessage(ChatColor.GREEN + subCommand.getDescription());
                sender.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + subCommand.getSyntax());
                sender.sendMessage(ChatColor.DARK_GREEN + "");
            }
        });

        sender.sendMessage(ChatColor.DARK_GREEN + "--------------------------------------------------");
        sender.sendMessage(ChatColor.RESET + "");
    }

    @Override
    public String getName() {
        return "Hammer Help";
    }

    @Override
    public String getCommand() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Infromation sur l'outils hammer";
    }

    @Override
    public String getSyntax() {
        return "/hammer help";
    }

    @Override
    public Integer getArgsNumber() {
        return 1;
    }

    @Override
    public void performPlayer(Player player, String[] strings) {
        action(player);
    }

    @Override
    public void performConsole(ConsoleCommandSender player, String[] args) {
        action(player);
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
