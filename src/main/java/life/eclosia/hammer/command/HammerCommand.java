package life.eclosia.hammer.command;

import life.eclosia.commandapi.commands.SubCommand;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HammerCommand extends SubCommand {

    @Override
    public String getName() {
        return "Hammer Help";
    }

    @Override
    public String getCommand() {
        return "hammer";
    }

    @Override
    public String getDescription() {
        return "Infromation sur l'outils hammer";
    }

    @Override
    public String getSyntax() {
        return "/hammer";
    }

    @Override
    public Integer getArgsNumber() {
        return 0;
    }

    @Override
    public void perform(Player player, String[] strings) {
        try {
            HammerHelpCommand.action(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void perform(ConsoleCommandSender player, String[] args) {
        try {
            HammerHelpCommand.action(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
