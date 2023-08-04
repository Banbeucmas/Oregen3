package me.banbeucmas.oregen3.data;

import lombok.Getter;
import me.banbeucmas.oregen3.Oregen3;

import java.util.HashMap;
import java.util.Map;

public class DataManager {
    private Oregen3 plugin;

    @Getter
    private final Map<String, Generator> generators = new HashMap<>();

    public DataManager(Oregen3 plugin) {
        this.plugin = plugin;
    }

    public Generator getGenerator(String name) {
        return generators.get(name);
    }

    public void unregisterAll(){
        generators.clear();
    }

    public void loadData() {
        unregisterAll();
        plugin.getConfig()
                .getConfigurationSection("generators")
                .getKeys(false)
                .forEach(id -> generators.put(id, new Generator(plugin, id)));
    }
}
