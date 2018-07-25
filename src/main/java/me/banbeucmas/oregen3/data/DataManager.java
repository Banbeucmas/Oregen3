package me.banbeucmas.oregen3.data;

import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class DataManager {
    private static Map<String, MaterialChooser> choosers = new HashMap<>();

    public static Map<String, MaterialChooser> getChoosers() {
        return choosers;
    }

    public static void unregisterAll(){
        choosers = null;
    }

    public static void loadData(){
        choosers = new HashMap<>();
        FileConfiguration config = Oregen3.getPlugin().getConfig();
        for(String id : config.getConfigurationSection("generators").getKeys(false)){
            getChoosers().put(id, new MaterialChooser(id));
        }
    }
}
