package life.eclosia.commandapi.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class SubCommand {

    /**
     * @return Le nom de la commande
     */
    public abstract String getName();

    /**
     * @return La command
     */
    public abstract String getCommand();

    /**
     * @return La description de la command
     */
    public abstract String getDescription();

    /**
     * @return La Syntax de la command
     */
    public abstract String getSyntax();

    /**
     * @return Le nompbres d'agrument
     */
    public abstract Integer getArgsNumber();

    /**
     * Call si un joueur a potentiellement excuter la command
     *
     * @param player Le joueur
     * @param args Les args
     */
    public abstract void performPlayer(Player player, String[] args);


    /**
     * Call si la console a potentiellement excuter la command
     *
     * @param console La console
     * @param args Les args
     */
    public abstract void performConsole(ConsoleCommandSender console, String[] args);

    /**
     * La liste pour l'auto complémetation
     *
     * @param player Le joueur
     * @param args Les arguement
     * @return La Liste des posibiliter
     */
    public abstract List<String> getSubcommandArguments(Player player, String[] args);

}
