package me.banbeucmas.oregen3.data;

import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DataManager {
    private static Map<String, MaterialChooser> choosers = new HashMap<>();

    public static Map<String, MaterialChooser> getChoosers() {
        return choosers;
    }

    public static void unregisterAll(){
        choosers = null;
    }

    public static void loadData() {
        choosers = new HashMap<>();
        final FileConfiguration config = Oregen3.getPlugin().getConfig();
        for (final String id : Objects.requireNonNull(config.getConfigurationSection("generators")).getKeys(false)) {
            choosers.put(id, new MaterialChooser(id));
        }
    }
}
