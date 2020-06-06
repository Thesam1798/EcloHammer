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

    private final ArrayList<SubCommand> subcommands = new ArrayList<>();

    public void addSubCommand(SubCommand newSub) {
        subcommands.add(newSub);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Stream<SubCommand> commands;

        if (args != null && args.length > 0 && args[0] != null) {
            commands = subcommands.stream().filter(
                    subCommand -> subCommand.getCommand().equalsIgnoreCase(args[0])
                            && subCommand.getArgsNumber().equals(args.length)
            );
        } else {
            commands = subcommands.stream().filter(
                    subCommand -> subCommand.getArgsNumber() == 0
            );
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            commands.forEach(subCommand -> subCommand.perform(player, args));
        } else {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;
            commands.forEach(subCommand -> subCommand.perform(console, args));
        }

        return true;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subcommands;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length == 1) { //prank <subcommand> <args>
            ArrayList<String> subcommandsArguments = new ArrayList<>();

            for (int i = 0; i < getSubCommands().size(); i++) {
                subcommandsArguments.add(getSubCommands().get(i).getCommand());
            }

            return subcommandsArguments;
        } else if (args.length >= 2) {
            for (int i = 0; i < getSubCommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getCommand())) {
                    return getSubCommands().get(i).getSubcommandArguments((Player) sender, args);
                }
            }
        }

        return null;
    }
}
