package life.eclosia.commandapi.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CommandManager implements TabExecutor {

    /**
     * List des commands
     */
    private final ArrayList<SubCommand> subcommands = new ArrayList<>();

    /**
     * Ajout de nouvelle commande
     *
     * @param newSub SubCommand
     */
    public void addSubCommand(SubCommand newSub) {
        subcommands.add(newSub);
    }

    /**
     * Action a une commande effectuer
     *
     * @param sender  L'éméteur de la command
     * @param command La command
     * @param label   Not used
     * @param args    List des arguement
     * @return Si la command est valid
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //Création d'une list de command
        Stream<SubCommand> commands;

        //Filtre des command disponible en fonction des argumeent
        if (args != null && args.length > 0 && args[0] != null) {
            commands = getSubCommands().stream().filter(
                    subCommand -> subCommand.getCommand().equalsIgnoreCase(args[0])
                            && subCommand.getArgsNumber().equals(args.length)
            );
        } else {
            commands = getSubCommands().stream().filter(
                    subCommand -> subCommand.getArgsNumber() == 0
            );
        }

        //Définition de la méthode a call
        if (sender instanceof Player) {
            Player player = (Player) sender;
            commands.forEach(subCommand -> subCommand.performPlayer(player, args));
        } else {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;
            commands.forEach(subCommand -> subCommand.performConsole(console, args));
        }

        return true;
    }

    /**
     * Retourne la liste des command
     *
     * @return SubCommands
     */
    public ArrayList<SubCommand> getSubCommands() {
        return subcommands;
    }

    /**
     * Auto complétation avec un tab dans le chat
     *
     * @param sender  L'éméteur de la command
     * @param command La command
     * @param alias   Not used
     * @param args    List des arguement
     * @return List des proposition
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (!(sender instanceof Player)) return null;

        ArrayList<String> subcommandsArguments = new ArrayList<>();

        if (args.length == 1) {
            //Si il n'a que le nom de la command, retour de toute les commandes
            getSubCommands().forEach(subCommand -> subcommandsArguments.add(subCommand.getCommand()));
            return subcommandsArguments;
        } else if (args.length >= 2) {
            //Si il y a 2 ou plus d'arguement call de la méthode de toute les commandes
            getSubCommands().forEach(subCommand -> {
                if (args[0].equalsIgnoreCase(subCommand.getCommand()) && subCommand.getArgsNumber() != 0) {
                    subcommandsArguments.addAll(subCommand.getSubcommandArguments((Player) sender, args));
                }
            });
        }

        if (!subcommandsArguments.isEmpty()) {
            return subcommandsArguments;
        }

        return null;
    }
}
