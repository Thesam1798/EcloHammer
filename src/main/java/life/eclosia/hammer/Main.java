package life.eclosia.hammer;

import life.eclosia.commandapi.CommandHelper;
import life.eclosia.hammer.command.HammerCommand;
import life.eclosia.hammer.command.HammerGiveCommand;
import life.eclosia.hammer.command.HammerHelpCommand;
import life.eclosia.hammer.command.HammerRepairCommand;
import life.eclosia.hammer.config.HammerConfig;
import life.eclosia.hammer.events.HammerEvent;
import life.eclosia.hammer.object.hammer.ListHammer;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;


public class Main extends JavaPlugin {

    public static final String PLUGIN_CHAT_PREFIX = ChatColor.GRAY + "[" + ChatColor.GOLD + "EcloHammer" + ChatColor.GRAY + "] " + ChatColor.RESET + "";
    public static Plugin PLUGIN;

    @Override
    public void onEnable() {
        System.out.println("EcloHammer demarre...");

        try {
            PLUGIN = this;

            HammerConfig.load();
            ListHammer.loadFromConfig();

            CommandHelper.AddCommand((JavaPlugin) PLUGIN, "hammer", new HammerCommand());
            CommandHelper.AddCommand((JavaPlugin) PLUGIN, "hammer", new HammerHelpCommand());
            CommandHelper.AddCommand((JavaPlugin) PLUGIN, "hammer", new HammerGiveCommand());
            CommandHelper.AddCommand((JavaPlugin) PLUGIN, "hammer", new HammerRepairCommand());

            getServer().getPluginManager().registerEvents(new HammerEvent(this), this);

        } catch (Exception e) {
            this.getLogger().log(Level.WARNING, PLUGIN_CHAT_PREFIX + ChatColor.RED + "--------- --------- --------- [ERROR] --------- --------- ---------");
            this.getLogger().log(Level.WARNING, e.getMessage());
            this.getLogger().log(Level.WARNING, PLUGIN_CHAT_PREFIX + ChatColor.RED + "--------- --------- --------- [ERROR] --------- --------- ---------");
        }
    }

    @Override
    public void onDisable() {
        System.out.println("EcloHammer s'arrete...");
        PLUGIN = null;
    }
}
