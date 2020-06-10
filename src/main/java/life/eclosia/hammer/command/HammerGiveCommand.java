package life.eclosia.hammer.command;

import life.eclosia.commandapi.commands.SubCommand;
import life.eclosia.hammer.Main;
import life.eclosia.hammer.object.hammer.Hammer;
import life.eclosia.hammer.object.hammer.ListHammer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
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
    public void performPlayer(Player player, String[] args) {
        try {
            action(player, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void performConsole(ConsoleCommandSender player, String[] args) {
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

        if (ListHammer.list.isEmpty()) ListHammer.loadFromConfig();
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

        List<String> retour = new ArrayList<>();

        //Si c'est bien la command GIVE
        if (args[0].equals(getCommand())) {

            if (args[1] == null || args[1].isEmpty()) {
                //Si il n'a pas de jouruer entrer
                Bukkit.getServer().getOnlinePlayers().forEach(playerOnline -> retour.add(playerOnline.getName()));
            } else if ((args[2] == null || args[2].isEmpty()) && !ListHammer.list.isEmpty()) {
                //Si il n'a pas de hammer entrer
                retour.addAll(ListHammer.list.keySet());
            } else if ((args[2] != null && (!args[2].isEmpty())) && !ListHammer.list.isEmpty()) {
                //Si un debut de mot pour le hammer est entrer
                ListHammer.list.keySet().stream().filter(s -> s.contains(args[2])).forEach(retour::add);
            } else if (args[2] == null || args[2].isEmpty()) {
                //Si un debut de player est entrer
                Bukkit.getServer().getOnlinePlayers().forEach(playerOnline -> {
                    if (playerOnline.getName().contains(args[1])) {
                        retour.add(playerOnline.getName());
                    }
                });
            }
        }

        if (!retour.isEmpty()) {
            return retour;
        } else {
            return null;
        }
    }
}
