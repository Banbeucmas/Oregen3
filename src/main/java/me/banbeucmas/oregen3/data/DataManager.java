package me.banbeucmas.oregen3.data;

import me.banbeucmas.oregen3.Oregen3;

import java.util.HashMap;
import java.util.Map;

public class DataManager {
    private static final Map<String, Generator> generators = new HashMap<>();

    public static Map<String, Generator> getGenerators() {
        return generators;
    }

    //TODO: Make this not static
    public static Generator getGenerator(String name) {
        return generators.get(name);
    }

    public static void unregisterAll(){
        generators.clear();
    }

    public static void loadData() {
        unregisterAll();
        Oregen3.getPlugin().getConfig()
                .getConfigurationSection("generators")
                .getKeys(false)
                .forEach(id -> generators.put(id, new Generator(id)));
    }
}
