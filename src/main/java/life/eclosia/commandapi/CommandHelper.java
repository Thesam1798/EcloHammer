package life.eclosia.commandapi;

import life.eclosia.commandapi.commands.CommandManager;
import life.eclosia.commandapi.commands.SubCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class CommandHelper {

    private static CommandManager commandManager;

    public static void AddCommand(JavaPlugin plugin, String commandName, SubCommand newSubCommand) {
        try {
            if (plugin == null || commandName == null || newSubCommand == null) return;
            if (commandManager == null) commandManager = new CommandManager();

            commandManager.addSubCommand(newSubCommand);
            plugin.getCommand(commandName).setExecutor(commandManager);
        } catch (Exception ignored) {
        }
    }

    public static List<SubCommand> GetAllCommand() {
        return commandManager.getSubCommands();
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }

    public static void setCommandManager(CommandManager commandManager) {
        CommandHelper.commandManager = commandManager;
    }
}
