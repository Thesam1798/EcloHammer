package life.eclosia.commandapi;

import life.eclosia.commandapi.commands.CommandManager;
import life.eclosia.commandapi.commands.SubCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class CommandHelper {

    /**
     * Le Commande manager
     */
    private static CommandManager commandManager;

    /**
     * Méthode pour l'ajout d'une commande
     *
     * @param plugin        Le plugin
     * @param commandName   Le nom de la commande
     * @param newSubCommand Le SubCommand
     */
    public static void AddCommand(JavaPlugin plugin, String commandName, SubCommand newSubCommand) {
        try {
            //Si cela est null on quit
            if (plugin == null || commandName == null || newSubCommand == null) return;

            //Si le commandManager est null, création d'un default
            if (commandManager == null) commandManager = new CommandManager();

            //Ajout de la commande
            commandManager.addSubCommand(newSubCommand);
            plugin.getCommand(commandName).setExecutor(commandManager);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @return Retour de toute les subCommand
     */
    public static List<SubCommand> GetAllCommand() {
        return commandManager.getSubCommands();
    }

    /**
     * @return Retour le command manager
     */
    public static CommandManager getCommandManager() {
        return commandManager;
    }

    /**
     * Définition du command Manager
     *
     * @param commandManager Le commandManager a set
     */
    public static void setCommandManager(CommandManager commandManager) {
        CommandHelper.commandManager = commandManager;
    }
}
