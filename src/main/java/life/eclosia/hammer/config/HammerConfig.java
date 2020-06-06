package life.eclosia.hammer.config;

import life.eclosia.hammer.Main;
import org.bukkit.configuration.file.FileConfiguration;

public class HammerConfig {

    private static FileConfiguration config;

    public static void load() {
        Main.PLUGIN.saveDefaultConfig();
        Main.PLUGIN.getConfig();
        save();
        config = Main.PLUGIN.getConfig();
    }

    public static FileConfiguration getConfig() throws Exception {
        if (config == null) load();
        if (config == null) throw new Exception("Impossible de charger la configuration");
        return config;
    }

    public static void save() {
        Main.PLUGIN.saveConfig();
    }

}
