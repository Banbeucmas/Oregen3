package me.banbeucmas.oregen3.data;

import com.cryptomorin.xseries.XSound;
import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.handler.block.placer.BlockPlacer;
import me.banbeucmas.oregen3.handler.block.placer.VanillaBlockPlacer;
import me.banbeucmas.oregen3.hook.blockplacer.OraxenBlockPlacer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {
    private final String id;
    private final long priority;
    private final double level;
    private final String permission;
    private final String name;
    private final BlockPlacer[] blockPlacers;
    private final Double[] chancesList;
    private double totalChance = 0;
    private boolean soundEnabled;
    private Sound sound;
    private float soundVolume;
    private float soundPitch;
    private boolean worldEnabled;
    private boolean worldBlacklist;
    private Set<String> worldList;

    Generator(final String id) {
        this.id = id;
        final ConfigurationSection path = Oregen3.getPlugin().getConfig().getConfigurationSection("generators." + id);

        name = Objects.requireNonNull(path).getString("name", id);
        permission = path.getString("permission", "oregen3.generator." + id);
        priority   = path.getLong("priority", 0);
        level      = path.getDouble("level", 0);
        if (path.isSet("sound")) {
            soundEnabled = true;
            sound        = XSound.matchXSound(path.getString("sound.name", "BLOCK_FIRE_EXTINGUISH")).map(XSound::parseSound).orElse(XSound.BLOCK_FIRE_EXTINGUISH.parseSound());
            soundVolume  = (float) path.getDouble("sound.volume", 1);
            soundPitch   = (float) path.getDouble("sound.pitch", 1);
        }
        if (path.isSet("world")) {
            worldEnabled   = true;
            worldBlacklist = path.getBoolean("world.blacklist", true);
            worldList      = new HashSet<>(path.getStringList("world.list"));
        }
        final Set<String> randomList = Objects.requireNonNull(path.getConfigurationSection("random")).getKeys(false);
        chancesList = new Double[randomList.size()];
        blockPlacers = new BlockPlacer[randomList.size()];
        int i = 0;
        for (final String mat : randomList) {
            final double chance = path.getDouble("random." + mat);
            if (mat.startsWith("oraxen-")) {
                blockPlacers[i] = new OraxenBlockPlacer(mat);
            }
            else {
                blockPlacers[i] = new VanillaBlockPlacer(mat);
            }
            totalChance += chance;
            chancesList[i] = totalChance;
            i++;
        }
    }

    public BlockPlacer randomChance() {
        final Double chance = ThreadLocalRandom.current().nextDouble(getTotalChance());
        int chosenBlock = Arrays.binarySearch(getChancesList(), chance);
        if (chosenBlock < 0) {
            chosenBlock = -(chosenBlock + 1);
        }
        return blockPlacers[chosenBlock];
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public long getPriority() {
        return priority;
    }

    public double getLevel() {
        return level;
    }

    public double getTotalChance() {
        return totalChance;
    }

    public Double[] getChancesList() {
        return chancesList;
    }

    public Map<Material, Double> getChances() {
        return null;
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

    public Set<String> getWorldList() {
        return worldList;
    }
}
