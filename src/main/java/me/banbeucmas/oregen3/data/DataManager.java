package me.banbeucmas.oregen3.data;

import me.banbeucmas.oregen3.Oregen3;

import java.util.HashMap;
import java.util.Map;

import static me.banbeucmas.oregen3.utils.PluginUtils.debug;
import static me.banbeucmas.oregen3.utils.PluginUtils.debugln;

public class DataManager {
    private static final Map<String, MaterialChooser> choosers = new HashMap<>();

    public static Map<String, MaterialChooser> getChoosers() {
        return choosers;
    }

    public static void unregisterAll(){
        choosers.clear();
    }

    public static void loadData() {
        debug("Loaded generators: ");
        for (final String id : Oregen3.getPlugin().getConfig().getConfigurationSection("generators").getKeys(false)) {
            debug(id + ", ");
            if (Oregen3.DEBUG) {
                System.out.print(id + ", ");
            }
            choosers.put(id, new MaterialChooser(id));
        }
        debugln("");
    }
}
