package life.eclosia.commandapi;

import life.eclosia.commandapi.commands.CommandManager;
import life.eclosia.commandapi.commands.SubCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import static org.junit.Assert.assertEquals;

public class CommandHelperTest {

    JavaPlugin javaPlugin;
    SubCommand subCommand;
    CommandManager commandManager;

    @Before
    public void setUp() {
        javaPlugin = PowerMockito.mock(JavaPlugin.class);
        subCommand = PowerMockito.mock(SubCommand.class);

        CommandManager commandManager = Mockito.mock(CommandManager.class);
        CommandHelper.setCommandManager(commandManager);
    }

    @Test
    public void addCommandIsAllNull() {
        CommandHelper.AddCommand(null, null, null);
        commandManager = CommandHelper.getCommandManager();

        Mockito.verify(commandManager, Mockito.times(0)).addSubCommand(subCommand);
    }

    @Test
    public void addCommandPluginIsNull() {
        CommandHelper.AddCommand(null, "Test", subCommand);
        commandManager = CommandHelper.getCommandManager();

        Mockito.verify(commandManager, Mockito.times(0)).addSubCommand(subCommand);
    }

    @Test
    public void addCommandNameIsNull() {
        CommandHelper.AddCommand(javaPlugin, null, subCommand);
        commandManager = CommandHelper.getCommandManager();

        Mockito.verify(commandManager, Mockito.times(0)).addSubCommand(subCommand);
    }

    @Test
    public void addCommandCommandIsNull() {
        CommandHelper.AddCommand(javaPlugin, "TEST", null);
        commandManager = CommandHelper.getCommandManager();

        Mockito.verify(commandManager, Mockito.times(0)).addSubCommand(subCommand);
    }

    @Test
    public void addCommand() {
        CommandHelper.AddCommand(javaPlugin, "TEST", subCommand);
        commandManager = CommandHelper.getCommandManager();

        Mockito.verify(commandManager, Mockito.times(1)).addSubCommand(subCommand);
    }

    @Test
    public void getAllCommand() {
        commandManager = new CommandManager();
        CommandHelper.setCommandManager(commandManager);

        int size = CommandHelper.GetAllCommand().size();
        assertEquals(0, size);

        commandManager.addSubCommand(subCommand);

        size = CommandHelper.GetAllCommand().size();
        assertEquals(1, size);
    }
}
