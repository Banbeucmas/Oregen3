package me.banbeucmas.oregen3.data;

import me.banbeucmas.oregen3.Oregen3;

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

    public static void loadData() {
        choosers = new HashMap<>();
        if (Oregen3.DEBUG) {
            System.out.print("Loaded generators: ");
        }
        for (final String id : Oregen3.getPlugin().getConfig().getConfigurationSection("generators").getKeys(false)) {
            if (Oregen3.DEBUG) {
                System.out.print(id + ", ");
            }
            choosers.put(id, new MaterialChooser(id));
        }
        if (Oregen3.DEBUG) {
            System.out.println();
        }
    }
}
