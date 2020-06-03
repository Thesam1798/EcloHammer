package life.eclosia.hammer;

import life.eclosia.hammer.commands.CommandHammer;
import life.eclosia.hammer.events.HammerEvent;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static final String PLUGIN_CHAT_PREFIX = ChatColor.GRAY + "[" + ChatColor.GOLD + "EcloHammer" + ChatColor.GRAY + "] ";

    @Override
    public void onEnable() {
        System.out.println("EcloHammer demarre...");
        getCommand("hammer").setExecutor(new CommandHammer());
        getServer().getPluginManager().registerEvents(new HammerEvent(this), this);
    }

    @Override
    public void onDisable() {
        System.out.println("EcloHammer s'arrete...");
    }
}
