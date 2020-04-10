package me.banbeucmas.oregen3.data;

import com.cryptomorin.xseries.XSound;
import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class MaterialChooser {
    private final long priority;
    private final long level;
    private final String permission;
    private final Map<Material, Double> chances = new EnumMap<>(Material.class);
    private final Material fallback;
    private boolean soundEnabled;
    private Sound sound;
    private float soundVolume;
    private float soundPitch;
    private boolean worldEnabled;
    private boolean worldBlacklist;
    private List<String> worldList = new ArrayList<>();

    MaterialChooser(final String id) {
        final String path = "generators." + id;

        final Oregen3 plugin = Oregen3.getPlugin();
        final FileConfiguration config = plugin.getConfig();
        fallback   = Material.matchMaterial(config.getString(path + ".fallback", "COBBLESTONE"));
        permission = config.getString(path + ".permission", "oregen3.generator." + id);
        priority   = config.getLong(path + ".priority", 0);
        level      = config.getLong(path + ".level", 0);
        if (config.isSet(path + ".sound")) {
            soundEnabled = true;
            sound        = XSound.matchXSound(Oregen3.getPlugin().getConfig().getString(path + ".sound.name", "BLOCK_FIRE_EXTINGUISH")).map(XSound::parseSound).orElse(XSound.BLOCK_FIRE_EXTINGUISH.parseSound());
            soundVolume  = (float) config.getDouble(path + ".sound.volume", 1);
            soundPitch   = (float) config.getDouble(path + ".sound.pitch", 1);
        }
        if (config.isSet(path + ".world")) {
            worldEnabled   = true;
            worldBlacklist = config.getBoolean(path + ".world.blacklist", false);
            worldList      = config.getStringList(path + ".world.list");
        }
        for (final String mat : config.getConfigurationSection(path + ".random").getKeys(false)) {
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

    public long getPriority() {
        return priority;
    }

    public long getLevel() {
        return level;
    }

    public Material getFallback() {
        return fallback;
    }

    public Map<Material, Double> getChances() {
        return chances;
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    public Sound getSound() {
        return sound;
    }

    public float getSoundVolume() {
        return soundVolume;
    }

    public float getSoundPitch() {
        return soundPitch;
    }

    public boolean isWorldEnabled() {
        return worldEnabled;
    }

    public boolean isWorldBlacklist() {
        return worldBlacklist;
    }

    public List<String> getWorldList() {
        return worldList;
    }
}
