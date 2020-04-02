package me.banbeucmas.oregen3.data;

import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class MaterialChooser {
    private final int priority;
    private final int level;
    private final String permission;
    private final Map<Material, Double> chances = new EnumMap<>(Material.class);
    private final Material fallback;

    MaterialChooser(final String id) {
        final String path = "generators." + id;

        final Oregen3 plugin = Oregen3.getPlugin();
        final FileConfiguration config = plugin.getConfig();
        fallback   = config.isSet(path + ".fallback")
                ? Material.matchMaterial(Objects.requireNonNull(config.getString(path + ".fallback"))) : Material.COBBLESTONE;
        permission = config.isSet(path + ".permission")
                ? config.getString(path + ".permission") : "oregen3.generator." + id;
        priority   = config.isSet(path + ".priority")
                ? config.getInt(path + ".priority") : 0;
        level      = config.isSet(path + ".level")
                ? config.getInt(path + ".level") : 0;

        for (final String mat : Objects.requireNonNull(config.getConfigurationSection(path + ".random")).getKeys(false)) {
            chances.put(Material.matchMaterial(mat), config.getDouble(path + ".random." + mat));
        }
    }


// --Commented out by Inspection START (25/03/2020 8:27 SA):
//    public String getId() {
//        return id;
//    }
// --Commented out by Inspection STOP (25/03/2020 8:27 SA)

    public String getPermission() {
        return permission;
    }

    public int getPriority() {
        return priority;
    }

    public int getLevel() {
        return level;
    }

    public Material getFallback() {
        return fallback;
    }

    public Map<Material, Double> getChances() {
        return chances;
    }

}
