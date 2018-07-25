package me.banbeucmas.oregen3.data;

import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class MaterialChooser {
    private Oregen3 plugin = Oregen3.getPlugin();
    private FileConfiguration config = plugin.getConfig();
    private String id;
    private String permission;
    private Map<Material, Double> chances = new HashMap<>();
    private Material fallback;

    public MaterialChooser(String id) {
        this.id = id;
        this.fallback = config.isSet("generators." + id + ".fallback")
                ? Material.matchMaterial(config.getString("generators." + id + ".fallback")) : Material.COBBLESTONE;
        this.permission = config.isSet("generators." + id + ".permission")
                ? config.getString("generators." + id + ".permission") : "oregen3.generator." + id;
        for(String mat : config.getConfigurationSection("generators." + id + ".random").getKeys(false)){
            chances.put(Material.matchMaterial(mat), config.getDouble("generators." + id + ".random." + mat));
        }
    }


    public String getId() {
        return id;
    }

    public Material getFallback() {
        return fallback;
    }

    public String getPermission() {
        return permission;
    }

    public Map<Material, Double> getChances() {
        return chances;
    }
}
