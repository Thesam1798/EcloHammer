package life.eclosia.commandapi.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class SubCommand {

    public abstract String getName();

    public abstract String getCommand();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract Integer getArgsNumber();

    public abstract void perform(Player player, String[] args);

    public abstract void perform(ConsoleCommandSender player, String[] args);

    public abstract List<String> getSubcommandArguments(Player player, String[] args);

}
