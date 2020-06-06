package life.eclosia.hammer.command;

import life.eclosia.commandapi.commands.SubCommand;
import life.eclosia.hammer.Main;
import life.eclosia.hammer.object.hammer.Hammer;
import life.eclosia.hammer.object.hammer.ListHammer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HammerGiveCommand extends SubCommand {

    @Override
    public String getName() {
        return "Give Hammer";
    }

    @Override
    public String getCommand() {
        return "give";
    }

    @Override
    public String getDescription() {
        return "Permet de vous give un hammer";
    }

    @Override
    public String getSyntax() {
        return "/hammer give [player] [hammer name]";
    }

    @Override
    public Integer getArgsNumber() {
        return 3;
    }

    @Override
    public void perform(Player player, String[] args) {
        try {
            action(player, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void perform(ConsoleCommandSender player, String[] args) {
        try {
            action(player, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void action(CommandSender sender, String[] args) throws Exception {

        for (String arg : args) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.MAGIC + "DEBUG" + ChatColor.RESET + " : " + arg);
        }

        if (ListHammer.list == null || ListHammer.list.isEmpty()) ListHammer.loadFromConfig();
        if (ListHammer.list.isEmpty())
            sender.sendMessage(Main.PLUGIN_CHAT_PREFIX + ChatColor.RED + "ERROR : Imposible de charger la config");

        Object[] hammers = ListHammer.list.values().stream().filter(hammer -> hammer.getSimpleName().equalsIgnoreCase(args[2])).toArray();

        if (hammers.length == 1) {
            sender.sendMessage(Hammer.GiveHammer(args[1], args[2]));
        } else {
            sender.sendMessage(Main.PLUGIN_CHAT_PREFIX + ChatColor.RED + "ERROR : Imposible de trouve le hammer : " + args[2]);
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
