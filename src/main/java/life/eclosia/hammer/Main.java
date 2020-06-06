package life.eclosia.hammer;

import life.eclosia.commandapi.CommandHelper;
import life.eclosia.hammer.command.HammerCommand;
import life.eclosia.hammer.command.HammerGiveCommand;
import life.eclosia.hammer.command.HammerHelpCommand;
import life.eclosia.hammer.config.HammerConfig;
import life.eclosia.hammer.events.HammerEvent;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {

    public static final String PLUGIN_CHAT_PREFIX = ChatColor.GRAY + "[" + ChatColor.GOLD + "EcloHammer" + ChatColor.GRAY + "] ";
    public static Plugin PLUGIN;

    @Override
    public void onEnable() {
        System.out.println("EcloHammer demarre...");
        PLUGIN = this;

        HammerConfig.load();

        CommandHelper.AddCommand((JavaPlugin) PLUGIN, "hammer", new HammerCommand());
        CommandHelper.AddCommand((JavaPlugin) PLUGIN, "hammer", new HammerHelpCommand());
        CommandHelper.AddCommand((JavaPlugin) PLUGIN, "hammer", new HammerGiveCommand());

        getServer().getPluginManager().registerEvents(new HammerEvent(this), this);
    }

    @Override
    public void onDisable() {
        System.out.println("EcloHammer s'arrete...");
        PLUGIN = null;
    }
}
